package com.devluizfcneto.sistemaodontologico.dtos;

public class CadastrarPacienteDTO {

	private String cpf;
	private String nome;
	private String dataNascimento;
	
	public CadastrarPacienteDTO() {}
	
	public CadastrarPacienteDTO(String cpf, String nome, String dataNascimento) {
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
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
