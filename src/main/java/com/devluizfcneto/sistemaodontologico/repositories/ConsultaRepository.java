package com.devluizfcneto.sistemaodontologico.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devluizfcneto.sistemaodontologico.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	@Query("""
			SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consulta c 
				WHERE c.paciente.id = :pacienteId AND c.data > :dataAtual
	   """)
	boolean existeConsultaFutura(@Param("pacienteId") Long pacienteId, @Param("dataAtual") LocalDate dataAtual);

	@Query("""
			SELECT c FROM Consulta c WHERE c.data = :dataConsulta AND
			(	(c.horaInicial BETWEEN :horaInicial AND :horaFinal) OR
				(c.horaFinal BETWEEN :horaInicial AND :horaFinal) OR
				(:horaInicial BETWEEN c.horaInicial AND c.horaFinal) OR
				(:horaFinal BETWEEN c.horaInicial AND c.horaFinal)
			)
		""")
	List<Consulta> findConflitosHorario(
			@Param("dataConsulta") LocalDate dataConsulta,
		    @Param("horaInicial") LocalTime horaInicial,
		    @Param("horaFinal") LocalTime horaFinal);
	
}
