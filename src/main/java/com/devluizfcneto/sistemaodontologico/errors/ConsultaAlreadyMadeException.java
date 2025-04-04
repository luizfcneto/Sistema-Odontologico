package com.devluizfcneto.sistemaodontologico.errors;

public class ConsultaAlreadyMadeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private int code;
	
	public ConsultaAlreadyMadeException(String message) {
		super(message);
		this.code = 409;
	}
	
	public int getCode() {
		return this.code;
	}
	
}
