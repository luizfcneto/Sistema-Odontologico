package com.devluizfcneto.sistemaodontologico.dtos;

import com.devluizfcneto.sistemaodontologico.entities.Consulta;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;
import com.devluizfcneto.sistemaodontologico.utils.TimeUtils;

public class ConsultaResponseDTO {
	
	private String data;
	private String horaInicial;
	private String horaFinal;
	private String tempo;
	private PacienteMinDTO paciente;
	
	public ConsultaResponseDTO(String data, String horaInicial, String horaFinal, String tempo,
			PacienteMinDTO paciente) {
		this.data = data;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
		this.tempo = tempo;
		this.paciente = paciente;
	}
	
	public ConsultaResponseDTO(Consulta consulta) {
		this.data = DateUtils.formatLocalDateToString(consulta.getData());
		this.horaInicial = TimeUtils.formatLocalTimeToString(consulta.getHoraInicial());
		this.horaFinal = TimeUtils.formatLocalTimeToString(consulta.getHoraFinal());
		this.tempo = TimeUtils.formatLocalTimeToString(consulta.getTempo());
		this.paciente = new PacienteMinDTO(consulta.getPaciente());
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
	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	public PacienteMinDTO getPaciente() {
		return paciente;
	}
	public void setPaciente(PacienteMinDTO paciente) {
		this.paciente = paciente;
	}	
}
