package com.devluizfcneto.sistemaodontologico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ErrorDTO;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.errors.ConsultaAlreadyMadeException;
import com.devluizfcneto.sistemaodontologico.errors.ConsultaConflictedException;
import com.devluizfcneto.sistemaodontologico.services.ConsultaService;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {
	
	@Autowired
	private ConsultaService consultaService;
	
	@PostMapping("/")
	public ResponseEntity<?> cadastrarConsulta(@RequestBody CadastrarConsultaDTO consulta){
		try {
			ConsultaResponseDTO consultaCadastrada = this.consultaService.cadastrar(consulta);
			return new ResponseEntity<ConsultaResponseDTO>(consultaCadastrada, HttpStatusCode.valueOf(201));
			
		} catch (ConsultaConflictedException | ConsultaAlreadyMadeException | BadRequestException ex){
				throw ex;
				
		} catch (Exception exception) {
			System.out.println("Erro ao cadastrar consulta: " + exception.getMessage()); 
			return new ResponseEntity<ErrorDTO>(new ErrorDTO(exception.getMessage()), HttpStatusCode.valueOf(500));			
		}
	}
	
}
