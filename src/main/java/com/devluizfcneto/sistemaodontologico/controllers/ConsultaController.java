package com.devluizfcneto.sistemaodontologico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerConsulta(@PathVariable(required = true, name = "id") Long id){
		System.out.println("Remover consulta do id: " + id);
		this.consultaService.remover(id);
		return ResponseEntity.noContent().build();
	}
}
