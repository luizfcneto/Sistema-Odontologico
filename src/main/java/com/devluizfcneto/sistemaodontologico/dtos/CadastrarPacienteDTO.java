package com.devluizfcneto.sistemaodontologico.dtos;

import java.time.LocalDate;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;

public class CadastrarPacienteDTO {

	private String cpf;
	private String nome;
	private String dataNascimento;
	private Integer idade;
	
	public CadastrarPacienteDTO() {}
	
	public CadastrarPacienteDTO(Paciente paciente) {
		this.cpf = paciente.getCpf();
		this.nome = paciente.getNome();
		this.dataNascimento = DateUtils.formatLocalDateToString(paciente.getDataNascimento());
		this.calculaIdade();
	}
	
	public CadastrarPacienteDTO(String cpf, String nome, String dataNascimento) {
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.calculaIdade();
	}
	
	public void calculaIdade() {
		if(this.dataNascimento != null) {			
			this.idade = LocalDate.now().getYear() - (DateUtils.formatStringToLocalDate(dataNascimento)).getYear();
		} else {
			throw new BadRequestException("Não foi possível validar o campo dataNascimento");
		}
	}
	
	public Integer getIdade() {
		return this.idade;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
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
