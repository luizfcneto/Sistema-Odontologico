package com.devluizfcneto.sistemaodontologico.errors;

public class PacienteNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private Integer code;
	
	public PacienteNotFoundException(String message) {
		super(message);
		this.code = 404;
	}
	
	public Integer getCode() {
		return this.code;
	}
}
