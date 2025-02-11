package com.devluizfcneto.sistemaodontologico.errors;

public class BadRequestException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	
	public BadRequestException(String message) {
		super(message);
		this.code = 400;
	}
	
	public int getCode() {
		return this.code;
	}
}
