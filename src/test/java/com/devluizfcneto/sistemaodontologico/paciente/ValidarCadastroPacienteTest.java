package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.validations.CadastrarPacienteValidation;

public class ValidarCadastroPacienteTest {

	private CadastrarPacienteValidation validation;
    private CadastrarPacienteDTO pacienteValido;

    @BeforeEach
    void setUp() {
        validation = new CadastrarPacienteValidation();
        pacienteValido = new CadastrarPacienteDTO(
            "45086531039",
            "João da Silva",
            "15/05/2000"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando paciente é nulo")
    void testPacienteNulo() {
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(null));
    }

    @Test
    @DisplayName("Validação de CPF - Casos de erro")
    void testValidacaoCPF() {
        // CPF nulo
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO(null, "Nome", "01/01/2000");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        // CPF com tamanho inválido
        paciente.setCpf("1234567890");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        // CPF inválido (dígitos iguais)
        paciente.setCpf("11111111111");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Validação de CPF - Caso válido")
    void testCPFValido() {
        assertDoesNotThrow(() -> validation.validateCadastrarPaciente(pacienteValido));
    }

    @Test
    @DisplayName("Validação de Nome - Casos de erro")
    void testValidacaoNome() {
        // Nome nulo
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO("45086531039", null, "01/01/2000");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        // Nome curto
        paciente.setNome("Ana");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Validação de Data de Nascimento - Casos de erro")
    void testValidacaoDataNascimento() {
        // Data nula
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO("50297831022", "Nome", null);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        // Formato inválido
        testarDataInvalida("2023-05-15");
        testarDataInvalida("15/5/2023");
        testarDataInvalida("32/05/2023");
        testarDataInvalida("15/13/2023");
        testarDataInvalida("00/05/2023");

        // Idade insuficiente
        LocalDate dataRecente = LocalDate.now().minusYears(12);
        String dataJovem = String.format("%02d/%02d/%d", 
            dataRecente.getDayOfMonth(),
            dataRecente.getMonthValue(),
            dataRecente.getYear());
            
        paciente.setDataNascimento(dataJovem);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    private void testarDataInvalida(String dataInvalida) {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO("50297831022", "Nome", dataInvalida);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Validação completa - Caso válido")
    void testValidacaoCompleta() {
        assertDoesNotThrow(() -> validation.validateCadastrarPaciente(pacienteValido));
    }

    @Test
    @DisplayName("Validação de borda - Paciente com exatamente 13 anos")
    void testIdadeMinimaBorda() {
        LocalDate dataNascimento = LocalDate.now().minusYears(13);
        String data = String.format("%02d/%02d/%d", 
            dataNascimento.getDayOfMonth(),
            dataNascimento.getMonthValue(),
            dataNascimento.getYear());
            
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO("45086531039", "Maria Oliveira", data);
        assertDoesNotThrow(() -> validation.validateCadastrarPaciente(paciente));
    }
}
