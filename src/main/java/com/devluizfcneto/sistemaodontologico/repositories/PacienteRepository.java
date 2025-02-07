package com.devluizfcneto.sistemaodontologico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	
	public Paciente findByNome(String nome);
	
}
