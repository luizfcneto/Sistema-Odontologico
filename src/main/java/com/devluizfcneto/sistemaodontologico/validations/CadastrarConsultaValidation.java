package com.devluizfcneto.sistemaodontologico.validations;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;

@Component
public class CadastrarConsultaValidation {

	public CadastrarConsultaValidation() {}
	
	public void validateCadastrarConsulta(CadastrarConsultaDTO consultaDTO) {
		if(consultaDTO == null) {
			throw new BadRequestException("Erro ao validar consulta");
		}
		
		this.validatePacienteId(consultaDTO.getPacienteId());
		this.validateData(consultaDTO.getData());
		this.validateHorario(consultaDTO.getHoraInicial());
		this.validateHorario(consultaDTO.getHoraFinal());	
		this.validateCompareHorarios(consultaDTO.getHoraInicial(), consultaDTO.getHoraFinal());
	}
	
	private void validatePacienteId(Long id){
		if(id == null || id < 1) {
			throw new BadRequestException("Erro ao validar id do paciente");
		}
	}
	
	private void validateData(String data) {
		if(data == null || data.length() < 10) {
			throw new BadRequestException("Erro ao validar data");
		}
		
		LocalDate dataLocalDate = DateUtils.formatStringToLocalDate(data);
		LocalDate now = LocalDate.now();
		
		if(dataLocalDate.isBefore(now)) {
			throw new BadRequestException("Erro ao validar data. Data consulta já passou");
		}
		
	}
	
	private void validateHorario(String horario) {
		if(horario == null || horario.length() != 4) {
			throw new BadRequestException("Erro ao validar horario");
		}

		if(!HorarioValidation.isValid(horario)) {
			throw new BadRequestException("Erro ao validar horario");
		}
	}
	
	private void validateCompareHorarios(String horarioInicial, String horarioFinal) {
		int horaInicio = Integer.valueOf(horarioInicial.substring(0,2));
		int minutosInicio = Integer.valueOf(horarioInicial.substring(2, 4));
		
		int horaFim = Integer.valueOf(horarioFinal.substring(0, 2));
		int minutosFim = Integer.valueOf(horarioFinal.substring(2, 4));
		
		if(horaFim < horaInicio || (horaFim == horaInicio && minutosFim < minutosInicio)) {
			throw new BadRequestException("Erro ao validar horario");
		}
	}	
	
}
