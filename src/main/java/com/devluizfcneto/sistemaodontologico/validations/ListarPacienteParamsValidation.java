package com.devluizfcneto.sistemaodontologico.validations;

import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ListarPacienteParamsValidation {

    private final HashMap<String, Boolean> validParameters = new HashMap<String, Boolean>();

    public ListarPacienteParamsValidation() {
        this.validParameters.put("NOME", true);
        this.validParameters.put("CPF", true);
    }

    public void validateParameters(String orderBy) {
        if (orderBy != null) {
            if (!this.validParameters.containsKey(orderBy.toUpperCase())) {
                throw new BadRequestException("Parametro de ordenação inválido, só é possível ordernar por nome ou cpf");
            }
        }
    }
}
