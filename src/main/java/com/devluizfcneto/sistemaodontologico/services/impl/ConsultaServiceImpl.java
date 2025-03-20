package com.devluizfcneto.sistemaodontologico.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.devluizfcneto.sistemaodontologico.utils.DateUtils;
import com.devluizfcneto.sistemaodontologico.validations.ListarConsultaParamsValidation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.entities.Consulta;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.ConsultaAlreadyMadeException;
import com.devluizfcneto.sistemaodontologico.errors.ConsultaConflictedException;
import com.devluizfcneto.sistemaodontologico.repositories.ConsultaRepository;
import com.devluizfcneto.sistemaodontologico.services.ConsultaService;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;
import com.devluizfcneto.sistemaodontologico.utils.TimeUtils;
import com.devluizfcneto.sistemaodontologico.validations.CadastrarConsultaValidation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaServiceImpl implements ConsultaService{
	
	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private CadastrarConsultaValidation cadastrarConsultaValidation;
	
	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private ListarConsultaParamsValidation listarConsultaParamsValidation;
	
	@Override
	@Transactional
	public ConsultaResponseDTO cadastrar(CadastrarConsultaDTO consultaDTO) {
		this.cadastrarConsultaValidation.validateCadastrarConsulta(consultaDTO);
		
		Paciente pacienteExiste = this.pacienteService.buscar(consultaDTO.getPacienteId());

		LocalDate now = LocalDate.now();
		this.checaPacientePossuiConsultaFutura(pacienteExiste, now);
		
		this.checaColisaoConsulta(
				DateUtils.formatStringToLocalDate(consultaDTO.getData()),
				TimeUtils.formatStringToLocalTime(consultaDTO.getHoraInicial()), 
				TimeUtils.formatStringToLocalTime(consultaDTO.getHoraFinal()));
		
		Consulta novaConsulta = new Consulta(consultaDTO, pacienteExiste);
		return new ConsultaResponseDTO(this.consultaRepository.save(novaConsulta));

	}

	@Transactional(readOnly = true)
	public Boolean checaPacientePossuiConsultaFutura(Paciente paciente, LocalDate now) {
		Boolean pacientePossuiConsultaFutura = this.consultaRepository.existeConsultaFutura(paciente.getId(), now);
		if(pacientePossuiConsultaFutura) {
			throw new ConsultaAlreadyMadeException("Erro, paciente já possui consulta marcada");
		}
		return pacientePossuiConsultaFutura;
	}

	@Transactional(readOnly = true)
	public void checaColisaoConsulta(LocalDate dataConsulta, LocalTime horaInicial, LocalTime horaFinal){
		List<Consulta> consultasColididas = this.consultaRepository.findConflitosHorario(dataConsulta, horaInicial, horaFinal);
		if(!consultasColididas.isEmpty()) {
			throw new ConsultaConflictedException("Erro, já existe consulta marcada dentro deste horário");
		}
	}

	@Override
	public Boolean atualizaConsulta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void remover(Long id) {
		this.consultaRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConsultaResponseDTO> listarConsultas(String dataInicial, String dataFinal, String direction) {
		this.listarConsultaParamsValidation.validate(dataInicial, dataFinal);
		LocalDate dataInicio = dataInicial != null ? DateUtils.formatStringToLocalDate(dataInicial) : null;
		LocalDate dataFim = dataFinal != null ? DateUtils.formatStringToLocalDate(dataFinal) : null;

		Specification<Consulta> specification = (root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();
			if(dataInicio != null) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("data"), dataInicio));
			}
			if(dataFim != null) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("data"), dataFim));
			}
			return predicate;
		};

		Sort sortData = direction == null || direction.equalsIgnoreCase("asc") ?
				Sort.by("data").ascending() :
				Sort.by("data").descending();
		Sort sort = sortData.and(Sort.by("horaInicial").ascending());

		List<Consulta> consultas = this.consultaRepository.findAll(specification, sort);

		return consultas.stream()
				.map(consulta -> new ConsultaResponseDTO(consulta))
				.collect(Collectors.toList());
	}
}
