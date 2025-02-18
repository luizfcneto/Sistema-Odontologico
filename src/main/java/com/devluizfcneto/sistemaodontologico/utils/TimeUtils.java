package com.devluizfcneto.sistemaodontologico.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;

public class TimeUtils {

	private static final DateTimeFormatter FORMATTER_HHMM = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter FORMATTER_HH_MM = DateTimeFormatter.ofPattern("HH:mm");
	
    /**
     * Formata um LocalTime para o tipo String na formatação: "HH:mm"
     * @param hour
     * @return
     */
	public static String formatLocalTimeToString(LocalTime hour){
		if(hour == null) {
			throw new BadRequestException("Erro ao tentar formatar Horario");
		}else {
			return hour.format(FORMATTER_HH_MM);
		}
	}
	
	/**
	 * Formata uma String de horario "HHMM" para um LocalTime
	 * @param hour
	 * @return
	 */
	public static LocalTime formatStringToLocalTime(String hour){
		if(hour == null) {
			throw new BadRequestException("Erro ao tentar formatar Horario");
		}else {
			return LocalTime.parse(hour, FORMATTER_HHMM);
		}
	}
	
}
