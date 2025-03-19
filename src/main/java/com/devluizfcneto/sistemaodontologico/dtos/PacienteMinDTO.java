package com.devluizfcneto.sistemaodontologico.dtos;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;

import java.time.LocalDate;
import java.time.Period;

public class PacienteMinDTO {
	
	private String nome;
	private String dataNascimento;
	private Integer idade;
	
	public PacienteMinDTO(String nome, String dataNascimento) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.idade = calcularIdade(dataNascimento);
	}
	
	public PacienteMinDTO(Paciente paciente) {
		this.nome = paciente.getNome();
		this.dataNascimento = DateUtils.formatLocalDateToString(paciente.getDataNascimento());
		this.idade = calcularIdade(dataNascimento);
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
	public Integer getIdade() { return idade; }

	private Integer calcularIdade(String dataNascimento) {
		return calcularIdade(DateUtils.formatStringToLocalDate(dataNascimento));
	}

	private Integer calcularIdade(LocalDate dataNascimento) {
		return Period.between(dataNascimento, LocalDate.now()).getYears();
	}

}
