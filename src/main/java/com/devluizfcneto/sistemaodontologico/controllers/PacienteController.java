package com.devluizfcneto.sistemaodontologico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
	
	@Autowired
	private PacienteService pacienteService;
	
	@PostMapping("/")
	public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody CadastrarPacienteDTO paciente){
		System.out.println("Executoou o Cadastrar Paciente");
		try {
			Paciente pacienteCadastrado = this.pacienteService.cadastrar(paciente);
			return new ResponseEntity<Paciente>(pacienteCadastrado, HttpStatusCode.valueOf(201));
			
		}catch(Error erro) {
			return new ResponseEntity<Paciente>(HttpStatusCode.valueOf(500));			
		}
	}

}
