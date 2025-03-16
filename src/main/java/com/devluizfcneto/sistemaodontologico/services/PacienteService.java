package com.devluizfcneto.sistemaodontologico.services;

import org.springframework.stereotype.Service;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;

import java.util.List;

@Service
public interface PacienteService {
	public Paciente cadastrar(CadastrarPacienteDTO paciente);
	public Paciente buscar(Long id);
	public Paciente buscar(String nome);
	public void remover(Long id);
	public List<Paciente> listarPacientes(String orderBy, String direction);
}
