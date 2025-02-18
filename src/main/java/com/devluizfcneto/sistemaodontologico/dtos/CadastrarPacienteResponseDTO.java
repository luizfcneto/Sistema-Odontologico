package com.devluizfcneto.sistemaodontologico.dtos;

import java.time.LocalDate;
import java.time.Period;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;

public record CadastrarPacienteResponseDTO(Long id, String cpf, String nome, String dataNascimento, int idade) {
	
	public CadastrarPacienteResponseDTO(Paciente paciente) {
		this(
				paciente.getId(),
				paciente.getCpf(),
	            paciente.getNome(),
	            DateUtils.formatLocalDateToString(paciente.getDataNascimento()),
	            Period.between(paciente.getDataNascimento(), LocalDate.now()).getYears()
	        );
	}
	
}
