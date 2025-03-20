package com.devluizfcneto.sistemaodontologico.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String cpf;
	private String nome;
	
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

//	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
	@OneToMany(mappedBy = "paciente")
	@JsonManagedReference
	private List<Consulta> consultas;
	
	public Paciente() {};
	
	public Paciente(String cpf, String nome, LocalDate dataNascimento) {
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}
	
	
	public Paciente(Long id, String cpf, String nome, LocalDate dataNascimento) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(cpf, paciente.cpf) 
        		&& Objects.equals(nome, paciente.nome) 
        		&& Objects.equals(dataNascimento, paciente.dataNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, nome, dataNascimento);
    }
		
}
