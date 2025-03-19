package com.devluizfcneto.sistemaodontologico.validations;

import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class ListarConsultaParamsValidation {

    public void validate(String dataInicial, String dataFinal) {
        this.validateData(dataInicial);
        this.validateData(dataFinal);
    }

    private void validateData(String data) {
        if(data != null && data.length() < 10) {
            throw new BadRequestException("Erro ao validar data: " + data);
        }
    }

}
