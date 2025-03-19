package com.devluizfcneto.sistemaodontologico.paciente;

import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.validations.ListarPacienteParamsValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidarListagemPacienteTest {
    private ListarPacienteParamsValidation validation;

    @BeforeEach
    void setUp() {
        validation = new ListarPacienteParamsValidation();
    }

    @Test
    @DisplayName("Deve validar com sucesso quando orderBy é nulo")
    void testValidarOrderByNulo() {
        assertDoesNotThrow(() -> validation.validateParameters(null));
    }

    @Test
    @DisplayName("Deve validar com sucesso quando orderBy é 'nome'")
    void testValidarOrderByNome() {
        assertDoesNotThrow(() -> validation.validateParameters("nome"));
    }

    @Test
    @DisplayName("Deve validar com sucesso quando orderBy é 'cpf'")
    void testValidarOrderByCpf() {
        assertDoesNotThrow(() -> validation.validateParameters("cpf"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando orderBy é inválido")
    void testValidarOrderByInvalido() {
        assertThrows(BadRequestException.class, () -> validation.validateParameters("idade"));
    }

    @Test
    @DisplayName("Deve validar com sucesso quando orderBy é 'NOME' (maiúsculo)")
    void testValidarOrderByNOME_Maiusculo() {
        assertDoesNotThrow(() -> validation.validateParameters("NOME"));
    }

    @Test
    @DisplayName("Deve validar com sucesso quando orderBy é 'CPF' (maiúsculo)")
    void testValidarOrderByCPF_Maiusculo() {
        assertDoesNotThrow(() -> validation.validateParameters("CPF"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando orderBy é '  nome  ' (com espaços)")
    void testValidarOrderByNomeComEspacos() {
        assertThrows(BadRequestException.class, () -> validation.validateParameters("  nome  "));
    }

    @Test
    @DisplayName("Deve lançar exceção quando orderBy é 'cpf_invalido'")
    void testValidarOrderByCpfInvalido() {
        assertThrows(BadRequestException.class, () -> validation.validateParameters("cpf_invalido"));
    }

}
