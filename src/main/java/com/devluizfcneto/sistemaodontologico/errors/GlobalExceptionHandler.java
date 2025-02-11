package com.devluizfcneto.sistemaodontologico.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devluizfcneto.sistemaodontologico.dtos.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException badRequestException){
		return ResponseEntity
				.status(badRequestException.getCode())
				.body(new ErrorDTO(badRequestException.getMessage()));
	}
	
	@ExceptionHandler(PacienteAlreadyExistsException.class)
	public ResponseEntity<ErrorDTO> handlePacienteAlreadyExistsException(PacienteAlreadyExistsException pacienteAlreadyExistsException){
		return ResponseEntity
				.status(pacienteAlreadyExistsException.getCode())
				.body(new ErrorDTO(pacienteAlreadyExistsException.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO("Ocorreu um erro interno no servidor."));
    }
}
