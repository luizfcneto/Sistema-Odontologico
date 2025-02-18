package com.devluizfcneto.sistemaodontologico.consulta;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.validations.CadastrarConsultaValidation;

@ExtendWith(MockitoExtension.class)
public class ValidarCadastroConsultaTest {

	@InjectMocks
    private CadastrarConsultaValidation validation;

    private CadastrarConsultaDTO consulta;

    @BeforeEach
    public void setUp() {
        consulta = new CadastrarConsultaDTO(1L, "31/12/2030", "0815", "0900");
    }

    @Test
    @DisplayName("Deve lançar exceção quando a consulta for nula")
    void testConsultaNula() {
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(null));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID do paciente for nulo")
    void testValidacaoPacienteIdNulo() {
        consulta.setPacienteId(null);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID do paciente for inválido (menor que 1)")
    void testValidacaoPacienteIdInvalido() {
        consulta.setPacienteId(0L);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a data for nula")
    void testValidacaoDataNula() {
        consulta.setData(null);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a data tiver formato inválido")
    void testValidacaoDataFormatoInvalido() {
        consulta.setData("2024-12-28");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a data for no passado")
    void testValidacaoDataNoPassado() {
        consulta.setData("28/12/2022");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário inicial for nulo")
    void testValidacaoHorarioInicialNulo() {
        consulta.setHoraInicial(null);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário inicial tiver formato inválido")
    void testValidacaoHorarioInicialFormatoInvalido() {
        consulta.setHoraInicial("800");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário final for nulo")
    void testValidacaoHorarioFinalNulo() {
        consulta.setHoraFinal(null);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário final tiver formato inválido")
    void testValidacaoHorarioFinalFormatoInvalido() {
        consulta.setHoraFinal("9:00");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário inicial estiver fora do horário de funcionamento")
    void testValidacaoHorarioForaDoHorarioFuncionamentoInicio() {
        consulta.setHoraInicial("0700");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário final estiver fora do horário de funcionamento")
    void testValidacaoHorarioForaDoHorarioFuncionamentoFim() {
        consulta.setHoraFinal("2000");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário final for anterior ao inicial")
    void testValidacaoHorarioFinalAnteriorAoInicial() {
        consulta.setHoraInicial("0900");
        consulta.setHoraFinal("0800");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o horário final for igual ao inicial e o minuto final for anterior")
    void testValidacaoHorarioFinalIgualAoInicialMinutoFinalAnterior() {
        consulta.setHoraInicial("0915");
        consulta.setHoraFinal("0900");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando o horário final for fora do horario de funcionamento")
    void testValidacaoHorarioFinalForaDoHorarioDeFuncionamento() {
        consulta.setHoraInicial("1900");
        consulta.setHoraFinal("1915");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando o horário inicial for fora do horario de funcionamento")
    void testValidacaoHorarioInicialForaDoHorarioDeFuncionamento() {
        consulta.setHoraInicial("0745");
        consulta.setHoraFinal("0845");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando o horário inicial e Final for fora da regra do horario de funcionamento")
    void testValidacaoHorarioInicialEFinalForaDoHorarioDeFuncionamento() {
        consulta.setHoraInicial("0812");
        consulta.setHoraFinal("0849");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarConsulta(consulta));
    }

    @Test
    @DisplayName("Deve validar a consulta com sucesso quando os dados forem válidos")
    void testValidacaoCompleta() {
        validation.validateCadastrarConsulta(consulta);
    }
}
