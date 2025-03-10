package com.devluizfcneto.sistemaodontologico.validations;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;

@Component
public class CadastrarConsultaValidation {
	private final int CONSULTORIO_HORA_INICIO_FUNCIONAMENTO = 8;
	private final int CONSULTORIO_HORA_FIM_FUNCIONAMENTO = 19;
	
	public CadastrarConsultaValidation() {}
	
	public void validateCadastrarConsulta(CadastrarConsultaDTO consultaDTO) {
		if(consultaDTO == null) {
			throw new BadRequestException("Erro ao validar consulta");
		}
		
		this.validatePacienteId(consultaDTO.getPacienteId());
		this.validateData(consultaDTO.getData());
		this.validateHorario(consultaDTO.getHoraInicial());
		this.validateHorario(consultaDTO.getHoraFinal());
		consultaDTO.calcularTempo();			
		this.validateTempo(consultaDTO.getTempo());
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

		HorarioValidation.isValid(horario);
	}
	
	private void validateTempo(String tempo) {
		if(tempo == null || tempo.length() != 4) {
			throw new BadRequestException("Erro ao validar tempo");
		}
	}
	
	private void validateCompareHorarios(String horarioInicial, String horarioFinal) {
		try {
			int horaInicio = Integer.parseInt(horarioInicial.substring(0,2));
			int minutosInicio = Integer.parseInt(horarioInicial.substring(2, 4));

			int horaFim = Integer.parseInt(horarioFinal.substring(0, 2));
			int minutosFim = Integer.parseInt(horarioFinal.substring(2, 4));

			if(horaInicio < CONSULTORIO_HORA_INICIO_FUNCIONAMENTO
					|| horaInicio >= CONSULTORIO_HORA_FIM_FUNCIONAMENTO
					|| horaFim < CONSULTORIO_HORA_INICIO_FUNCIONAMENTO
					|| horaFim >= CONSULTORIO_HORA_FIM_FUNCIONAMENTO) {
				throw new BadRequestException("Horario de funcionamento do consutorio é de 08:00 até as 19:00");
			}

			if(horaFim < horaInicio || (horaFim == horaInicio && minutosFim < minutosInicio)) {
				throw new BadRequestException("Erro ao validar horario");
			}

		} catch (NumberFormatException e) {
			throw new BadRequestException("Horario invalido");
		}

	}
	
}
