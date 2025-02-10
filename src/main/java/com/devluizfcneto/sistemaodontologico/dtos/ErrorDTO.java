package com.devluizfcneto.sistemaodontologico.dtos;

public class ErrorDTO {
	
	private String message;
	
	public ErrorDTO(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
