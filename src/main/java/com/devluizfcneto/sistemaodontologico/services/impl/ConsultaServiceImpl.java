package com.devluizfcneto.sistemaodontologico.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class ConsultaServiceImpl implements ConsultaService{
	
	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private CadastrarConsultaValidation cadastrarConsultaValidation;
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Override
	public ConsultaResponseDTO cadastrar(CadastrarConsultaDTO consultaDTO) {
		this.cadastrarConsultaValidation.validateCadastrarConsulta(consultaDTO);
		
		Paciente pacienteExiste = this.pacienteService.buscar(consultaDTO.getPacienteId());
		
		LocalDate now = LocalDate.now();
		this.checaPacientePossuiConsultaFutura(pacienteExiste, now);
		
		this.checaColisaoConsulta(
				now, 
				TimeUtils.formatStringToLocalTime(consultaDTO.getHoraInicial()), 
				TimeUtils.formatStringToLocalTime(consultaDTO.getHoraFinal()));
		
		Consulta novaConsulta = new Consulta(consultaDTO, pacienteExiste);
		ConsultaResponseDTO response = new ConsultaResponseDTO(this.consultaRepository.save(novaConsulta));
		return response;
	}
	
	public Boolean checaPacientePossuiConsultaFutura(Paciente paciente, LocalDate now) {
		Boolean pacientePossuiConsultaFutura = this.consultaRepository.existeConsultaFutura(paciente.getId(), now);
		if(pacientePossuiConsultaFutura) {
			throw new ConsultaAlreadyMadeException("Erro, paciente já possui consulta marcada");
		}
		return pacientePossuiConsultaFutura;
	}
	
	public void checaColisaoConsulta(LocalDate now, LocalTime horaInicial, LocalTime horaFinal){
		List<Consulta> consultasColididas = this.consultaRepository.findConflitosHorario(now, horaInicial, horaFinal);
		if(!consultasColididas.isEmpty()) {
			throw new ConsultaConflictedException("Erro, já existe consulta marcada dentro deste horário");
		}
	}

	@Override
	public List<Consulta> listaConsulta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean atualizaConsulta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removerConsulta() {
		// TODO Auto-generated method stub
		
	}
	
}
