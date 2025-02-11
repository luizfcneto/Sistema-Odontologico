package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.validations.CadastrarPacienteValidation;

public class ValidarCadastroPacienteTest {

	private CadastrarPacienteValidation validation;

    @BeforeEach
    public void setUp() {
        validation = new CadastrarPacienteValidation();
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    @DisplayName("Testando validacao paciente nulo")
    public void testValidateCadastrarPaciente_PacienteNulo() {
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(null));
    }

    @Test
    @DisplayName("Testando validacao paciente com cpf nulo")
    public void testValidateCpf_CpfNulo() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setCpf(null);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }
    
    @Test
    @DisplayName("Testando validacao de paciente com cpf invalido")
    public void testValidateCpf_CpfInvalido_Tamanho() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setCpf("1234567890"); 
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        paciente.setCpf("123456789012"); 
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Testando validacao de paciente com cpf invalido")
    public void testValidateCpf_CpfInvalido_Digitos() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setCpf("11111111111");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        paciente.setCpf("12345678900");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Testando validacao paciente com cpf valido")
    public void testValidateCpf_CpfValido() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setCpf("28253090013");

        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Testando validacao paciente com nome nulo")
    public void testValidateNome_NomeNulo() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setNome(null);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Testando validacao paciente com nome invalido")
    public void testValidateNome_NomeInvalido() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setCpf("28253090013");
        paciente.setNome("Nome"); 
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Testando validacao paciente com data nascimento nula")
    public void testValidateDataNascimento_DataNascimentoNula() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setDataNascimento(null);
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Testando validacao paciente com data nascimento invalida")
    public void testValidateDataNascimento_DataNascimentoInvalida() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setCpf("28253090013");
        paciente.setNome("NomeValido"); 

        paciente.setDataNascimento("10/05/2001/5");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        paciente.setDataNascimento("10-05-2001");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        paciente.setDataNascimento("10/05"); 
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        paciente.setDataNascimento("32/05/2001");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));

        paciente.setDataNascimento("10/13/2001");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
        
        paciente.setDataNascimento("-1/12/2001");
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
        
    }

    @Test
    @DisplayName("Testando validacao paciente com idade invalida")
    public void testValidateIdade_IdadeInvalida() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setCpf("28253090013");
        paciente.setNome("NomeValido"); 
        paciente.setDataNascimento("01/01/2999");
        paciente.calculaIdade();
        assertThrows(BadRequestException.class, () -> validation.validateCadastrarPaciente(paciente));
    }

    @Test
    @DisplayName("Testando validacao paciente valido completamente")
    public void testValidateCadastrarPaciente_Valido() {
        CadastrarPacienteDTO paciente = new CadastrarPacienteDTO();
        paciente.setCpf("28253090013");
        paciente.setNome("Nome Completo");
        paciente.setDataNascimento("10/05/2001");
        paciente.calculaIdade();

        validation.validateCadastrarPaciente(paciente);
    }
}
