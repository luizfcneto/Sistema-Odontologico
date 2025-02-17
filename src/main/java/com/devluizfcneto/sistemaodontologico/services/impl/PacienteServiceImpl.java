package com.devluizfcneto.sistemaodontologico.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.PacienteAlreadyExistsException;
import com.devluizfcneto.sistemaodontologico.errors.PacienteNotFoundException;
import com.devluizfcneto.sistemaodontologico.repositories.PacienteRepository;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;
import com.devluizfcneto.sistemaodontologico.validations.CadastrarPacienteValidation;

@Service
public class PacienteServiceImpl implements PacienteService {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private CadastrarPacienteValidation cadastrarPacienteValidation;
	
	public PacienteServiceImpl() {}

	@Override
	public Paciente cadastrar(CadastrarPacienteDTO paciente) {
		paciente.calculaIdade();
		this.cadastrarPacienteValidation.validateCadastrarPaciente(paciente);
		Paciente pacienteExistente = pacienteRepository.findByCpf(paciente.getCpf());
		if(pacienteExistente != null) {
			throw new PacienteAlreadyExistsException("Erro ao tentar criar o paciente. CPF já utilizado");
		}
		Paciente pacienteNovo = new Paciente(paciente.getCpf(), paciente.getNome(), DateUtils.formatStringToLocalDate(paciente.getDataNascimento()));
		return pacienteRepository.save(pacienteNovo);
	}

	@Override
	public Paciente buscar(Long id) {
		Optional<Paciente> pacienteExiste = pacienteRepository.findById(id);
		if(pacienteExiste.isEmpty()) {
			throw new PacienteNotFoundException("Erro: Não foi possível encontrar o Paciente");
		}
		return pacienteExiste.get();
	}

	@Override
	public Paciente buscar(String nome) {
		return pacienteRepository.findByNome(nome);
	}

	@Override
	public void remover(Long id) {
		Optional<Paciente> pacienteExistente = this.pacienteRepository.findById(id);
		if(!pacienteExistente.isEmpty()) {
			this.pacienteRepository.delete(pacienteExistente.get());
		}else {
			throw new PacienteNotFoundException("Paciente com id fornecido não foi encontrado");
		}
	}
}
