package com.devluizfcneto.sistemaodontologico.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.repositories.PacienteRepository;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;

@Service
public class PacienteServiceImpl implements PacienteService {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	public PacienteServiceImpl() {}

	@Override
	public Paciente cadastrar(CadastrarPacienteDTO paciente) {
		Paciente pacienteNovo = new Paciente(paciente.getCpf(), paciente.getNome(), paciente.getDataNascimento());
		return pacienteRepository.save(pacienteNovo);
	}

	@Override
	public Paciente buscar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paciente buscar(String nome) {
		return pacienteRepository.findByNome(nome);
	}

	@Override
	public Boolean remover(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
