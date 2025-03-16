package com.devluizfcneto.sistemaodontologico.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	public Paciente findByNome(String nome);
	public Paciente findByCpf(String cpf);

	@Query("SELECT p FROM Paciente p LEFT JOIN FETCH p.consultas c WHERE c.data >= CURRENT_DATE OR c IS NULL")
	List<Paciente> listPacientesComConsultasFuturas(Sort sort);
}
