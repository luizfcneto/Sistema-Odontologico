package com.devluizfcneto.sistemaodontologico.services;

import org.springframework.stereotype.Service;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;

@Service
public interface PacienteService {
	public Paciente cadastrar(CadastrarPacienteDTO paciente);
	public Paciente buscar(Long id);
	public Paciente buscar(String nome);
	public Boolean remover(Long id);
}
