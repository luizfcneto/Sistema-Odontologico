package com.devluizfcneto.sistemaodontologico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteResponseDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
	
	@Autowired
	private PacienteService pacienteService;
	
	@PostMapping("/")
	public ResponseEntity<CadastrarPacienteResponseDTO> cadastrarPaciente(@RequestBody CadastrarPacienteDTO paciente){
		Paciente pacienteCadastrado = this.pacienteService.cadastrar(paciente);
		return ResponseEntity.status(HttpStatus.CREATED).body(new CadastrarPacienteResponseDTO(pacienteCadastrado));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerPaciente(@PathVariable(required = true, name = "id") Long id){	
		this.pacienteService.remover(id);
		return ResponseEntity.noContent().build();
	
	}
	
}
