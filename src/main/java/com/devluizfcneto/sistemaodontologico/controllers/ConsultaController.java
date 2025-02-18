package com.devluizfcneto.sistemaodontologico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.services.ConsultaService;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaController {
	
	@Autowired
	private ConsultaService consultaService;
	
	@PostMapping("/")
	public ResponseEntity<ConsultaResponseDTO> cadastrarConsulta(@RequestBody CadastrarConsultaDTO consulta){	
		ConsultaResponseDTO consultaCadastrada = this.consultaService.cadastrar(consulta);
		return ResponseEntity.status(HttpStatus.CREATED).body(consultaCadastrada);
	}
	
}
