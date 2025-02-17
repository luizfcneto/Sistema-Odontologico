package com.devluizfcneto.sistemaodontologico.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;
import com.devluizfcneto.sistemaodontologico.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consulta")
public class Consulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate data;
	
	@Column(name = "hora_inicial")
	private LocalTime horaInicial;
	
	@Column(name = "hora_final")
	private LocalTime horaFinal;
	
	private LocalTime tempo;
	
	@ManyToOne
	@JoinColumn(name = "paciente_id")
	@JsonBackReference
	private Paciente paciente;
	
	public Consulta() {};
	
	public Consulta(CadastrarConsultaDTO consultaDTO, Paciente paciente) {
		super();
		this.data = DateUtils.formatStringToLocalDate(consultaDTO.getData());
		this.horaInicial = TimeUtils.formatStringToLocalTime(consultaDTO.getHoraInicial());
		this.horaFinal = TimeUtils.formatStringToLocalTime(consultaDTO.getHoraFinal());
		this.tempo = TimeUtils.formatStringToLocalTime(consultaDTO.getTempo());
		this.paciente = paciente;
	}
	
	public Consulta(LocalDate data, LocalTime horaInicial, LocalTime horaFinal, LocalTime tempo, Paciente paciente) {
		super();
		this.data = data;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
		this.tempo = tempo;
		this.paciente = paciente;
	}

	public Consulta(Long id, LocalDate data, LocalTime horaInicial, LocalTime horaFinal, LocalTime tempo, Paciente paciente) {
		super();
		this.id = id;
		this.data = data;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
		this.tempo = tempo;
		this.paciente = paciente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(LocalTime horaInicial) {
		this.horaInicial = horaInicial;
	}

	public LocalTime getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(LocalTime horaFinal) {
		this.horaFinal = horaFinal;
	}

	public LocalTime getTempo() {
		return tempo;
	}

	public void setTempo(LocalTime tempo) {
		this.tempo = tempo;
	}
	
	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPacienteId(Paciente paciente) {
		this.paciente = paciente;
	}
	@Override
	public int hashCode() {
		return Objects.hash(data, horaInicial, horaFinal, paciente);
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(data, consulta.data) 
        		&& Objects.equals(horaInicial, consulta.horaInicial) 
        		&& Objects.equals(horaFinal, consulta.horaFinal) 
        		&& Objects.equals(paciente, consulta.paciente);
    }

	
}
