package com.devluizfcneto.sistemaodontologico.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;

public class DateUtils {
	
	/**
	 * Transforma data LocalDate para o formato dd/mm/yyyy em String
	 * @param LocalDate
	 * @return String
	 */
	public static String formatLocalDateToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    return date.format(formatter);
	}
	
	/**
	 * Transforma data no padrão dd/mm/yyyy para o respectivo no LocalDate
	 * @param String
	 * @return LocalDate
	 */
	public static LocalDate formatStringToLocalDate(String date) {
		String[] dateSplitted = date.split("/");
		try {
			return LocalDate.of(Integer.valueOf(dateSplitted[2]), Integer.valueOf(dateSplitted[1]), Integer.valueOf(dateSplitted[0]));
		}catch(Exception ex) {
			throw new BadRequestException("Erro ao validar data: Formato inválido. Use dd/mm/yyyy");
		}
	}
}
