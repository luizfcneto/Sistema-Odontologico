package com.devluizfcneto.sistemaodontologico.validations;

import org.springframework.stereotype.Component;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;

@Component
public class CadastrarPacienteValidation {
	
	public CadastrarPacienteValidation() {}

	public void validateCadastrarPaciente(CadastrarPacienteDTO paciente) {
		if(paciente == null) {
			throw new BadRequestException("Erro ao validar paciente");
		}
		
		this.validateCpf(paciente.getCpf());
		this.validateNome(paciente.getNome());
		this.validateDataNascimento(paciente.getDataNascimento());
		this.validateIdade(paciente.getIdade());
	}
	
	private void validateCpf(String cpf) {
		if(cpf == null || cpf.length() != 11) {
			throw new BadRequestException("Erro ao validar cpf paciente");
		}
	}
	
	private void validateNome(String nome) {
		if(nome == null || nome.length() < 5) {
			throw new BadRequestException("Erro ao validar nome do paciente");
		}
	}
	
	private void validateDataNascimento(String dataNascimento) {
		if(dataNascimento == null || dataNascimento.length() < 10) {
			throw new BadRequestException("Erro ao validar data de nascimento");
		}
		
		String[] dataNascimentoSplitted = dataNascimento.split("/");
		if(dataNascimentoSplitted.length != 3) {
			throw new BadRequestException("Erro ao validar data de nascimento");
		}
		
		if(Integer.valueOf(dataNascimentoSplitted[0]) < 1 || Integer.valueOf(dataNascimentoSplitted[0]) > 31) {
			throw new BadRequestException("Erro ao validar data de nascimento");
		}
		
		if(Integer.valueOf(dataNascimentoSplitted[1]) < 0 || Integer.valueOf(dataNascimentoSplitted[1]) > 12) {
			throw new BadRequestException("Erro ao validar data de nascimento");
		}
	}
	
	private void validateIdade(int idade) {
		if(idade < 13) {
			throw new BadRequestException("Erro ao validar idade do paciente");
		}
	}
	
}
