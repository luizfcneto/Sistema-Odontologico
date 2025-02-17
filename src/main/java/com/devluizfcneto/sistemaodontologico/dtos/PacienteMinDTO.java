package com.devluizfcneto.sistemaodontologico.dtos;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;

public class PacienteMinDTO {
	
	private String nome;
	private String dataNascimento;
	
	public PacienteMinDTO(String nome, String dataNascimento) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}
	
	public PacienteMinDTO(Paciente paciente) {
		this.nome = paciente.getNome();
		this.dataNascimento = DateUtils.formatLocalDateToString(paciente.getDataNascimento());
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	
}
