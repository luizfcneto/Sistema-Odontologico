package com.devluizfcneto.sistemaodontologico.dtos;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;

public class CadastrarConsultaDTO {
	
	private Long pacienteId;
	private String data;
	private String horaInicial;
	private String horaFinal;
	private String tempo;
	
	public CadastrarConsultaDTO() {}	
	
	public CadastrarConsultaDTO(Long pacienteId, String data, String horaInicial, String horaFinal) {
		super();
		this.pacienteId = pacienteId;
		this.data = data;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
	}
	
	public void calcularTempo() {
        DateTimeFormatter formatterHorario = DateTimeFormatter.ofPattern("HHmm");
        try {
        	LocalTime horarioInicio = LocalTime.parse(this.horaInicial, formatterHorario);
        	LocalTime horarioFim = LocalTime.parse(this.horaFinal, formatterHorario);
        	
        	Duration duracao = Duration.between(horarioInicio, horarioFim);
        	
        	long horas = duracao.toHours();
        	long minutos = duracao.toMinutesPart();
        	
        	this.tempo = String.format("%02d%02d", horas, minutos);
        }catch(Exception ex) {
        	throw new BadRequestException("Erro ao calcular tempo de duração da consulta");
        }

	}
	
	public String getTempo() {
		return this.tempo;
	}
             
	public Long getPacienteId() {
		return pacienteId;
	}
	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHoraInicial() {
		return horaInicial;
	}
	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}
	public String getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}	
}
