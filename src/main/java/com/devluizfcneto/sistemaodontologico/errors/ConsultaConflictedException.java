package com.devluizfcneto.sistemaodontologico.errors;

public class ConsultaConflictedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private int code;
	
	public ConsultaConflictedException(String message) {
		super(message);
		this.code = 409;
	}
	
	public int getCode() {
		return this.code;
	}
	
}
