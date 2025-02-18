package com.devluizfcneto.sistemaodontologico.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;

public class HorarioValidation {

	private static final String REGEX_HORARIO = "^(0[8-9]|1[0-8])(00|15|30|45)$";
    private static final Pattern PADRAO_HORARIO = Pattern.compile(REGEX_HORARIO);
    
    public static boolean isValid(String horario) {
    	Matcher matcher = PADRAO_HORARIO.matcher(horario);
		if(!matcher.matches()) {
			throw new BadRequestException("Erro ao validar horario");
		}else {
			return true;
		}
    }

}
