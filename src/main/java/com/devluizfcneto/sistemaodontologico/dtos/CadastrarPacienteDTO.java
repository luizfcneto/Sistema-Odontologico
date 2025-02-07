package com.devluizfcneto.sistemaodontologico.dtos;

import java.time.LocalDate;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;

public class CadastrarPacienteDTO {

	private String cpf;
	private String nome;
	private LocalDate dataNascimento;
	private Integer idade;
	
	public CadastrarPacienteDTO() {}
	
	public CadastrarPacienteDTO(Paciente paciente) {
		this.cpf = paciente.getCpf();
		this.nome = paciente.getNome();
		this.dataNascimento = paciente.getDataNascimento();
		this.calculaIdade();
	}
	
	public CadastrarPacienteDTO(String cpf, String nome, LocalDate dataNascimento) {
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.calculaIdade();
	}
	
	private void calculaIdade() {
		this.idade = LocalDate.now().getYear() - this.dataNascimento.getYear();
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
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
}
