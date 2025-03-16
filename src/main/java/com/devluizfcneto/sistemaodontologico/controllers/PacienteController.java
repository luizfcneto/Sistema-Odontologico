package com.devluizfcneto.sistemaodontologico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteResponseDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
	
	@Autowired
	private PacienteService pacienteService;

	@GetMapping("/list/all")
	public ResponseEntity<?> listarPacientes(
			@RequestParam(required = false) String orderBy,
			@RequestParam(required = false) String direction){
		return ResponseEntity.ok(this.pacienteService.listarPacientes(orderBy, direction));
	}

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
