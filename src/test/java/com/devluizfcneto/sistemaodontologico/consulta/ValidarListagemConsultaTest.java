package com.devluizfcneto.sistemaodontologico.consulta;

import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.validations.ListarConsultaParamsValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidarListagemConsultaTest {
    private ListarConsultaParamsValidation validation;

    @BeforeEach
    void setUp() {
        validation = new ListarConsultaParamsValidation();
    }

    @Test
    @DisplayName("Deve validar com sucesso quando dataInicial e dataFinal são nulos")
    void testValidarDatasNulas() {
        assertDoesNotThrow(() -> validation.validate(null, null));
    }

    @Test
    @DisplayName("Deve validar com sucesso quando dataInicial e dataFinal são válidas")
    void testValidarDatasValidas() {
        assertDoesNotThrow(() -> validation.validate("01/01/2024", "31/12/2024"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando dataInicial é inválida (menor que 10 caracteres)")
    void testValidarDataInicialInvalida() {
        assertThrows(BadRequestException.class, () -> validation.validate("01/01/204", "31/12/2024"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando dataFinal é inválida (menor que 10 caracteres)")
    void testValidarDataFinalInvalida() {
        assertThrows(BadRequestException.class, () -> validation.validate("01/01/2024", "31/12/204"));
    }

    @Test
    @DisplayName("Não deve lançar exceção quando dataInicial é nula e dataFinal é válida")
    void testValidarDataInicialNulaDataFinalValida() {
        assertDoesNotThrow(() -> validation.validate(null, "31/12/2024"));
    }

    @Test
    @DisplayName("Não deve lançar exceção quando dataFinal é nula e dataInicial é válida")
    void testValidarDataFinalNulaDataInicialValida() {
        assertDoesNotThrow(() -> validation.validate("01/01/2024", null));
    }
}
