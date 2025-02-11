package com.devluizfcneto.sistemaodontologico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ErrorDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.errors.PacienteAlreadyExistsException;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
	
	@Autowired
	private PacienteService pacienteService;
	
	@PostMapping("/")
	public ResponseEntity<?> cadastrarPaciente(@RequestBody CadastrarPacienteDTO paciente){
		try {
			Paciente pacienteCadastrado = this.pacienteService.cadastrar(paciente);
			return new ResponseEntity<Paciente>(pacienteCadastrado, HttpStatusCode.valueOf(201));
			
		} catch (PacienteAlreadyExistsException | BadRequestException ex){
				throw ex;
				
		} catch (Exception exception) {
			return new ResponseEntity<ErrorDTO>(new ErrorDTO(exception.getMessage()), HttpStatusCode.valueOf(500));			
		}
	}

}
