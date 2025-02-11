package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devluizfcneto.sistemaodontologico.controllers.PacienteController;
import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ErrorDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.errors.PacienteAlreadyExistsException;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;

public class ControllerCadastrarPacienteTest {

	@Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testando cadastrar paciente SUCESSO")
    public void testCadastrarPaciente_Sucesso() {
        String dataNascimentoStr = "10/05/1990";
        CadastrarPacienteDTO pacienteDTO = new CadastrarPacienteDTO("12345678900", "Nome Paciente", dataNascimentoStr);
        LocalDate dataNascimento = LocalDate.of(1990, 5, 10);
        Paciente paciente = new Paciente("12345678900", "Nome Paciente", dataNascimento);

        when(pacienteService.cadastrar(pacienteDTO)).thenReturn(paciente);

        ResponseEntity<?> response = pacienteController.cadastrarPaciente(pacienteDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(paciente, response.getBody());
    }

    @Test
    @DisplayName("Testando cadastrar paciente CPF Existente FALHA")
    public void testCadastrarPaciente_CpfExistente() {
        String dataNascimentoStr = "10/05/1990";
        CadastrarPacienteDTO pacienteDTO = new CadastrarPacienteDTO("12345678900", "Nome Paciente", dataNascimentoStr);

        when(pacienteService.cadastrar(pacienteDTO)).thenThrow(PacienteAlreadyExistsException.class);

        assertThrows(PacienteAlreadyExistsException.class, () -> pacienteController.cadastrarPaciente(pacienteDTO));
    }

    @Test
    @DisplayName("Testando cadastrar paciente validacao FALHA")
    public void testCadastrarPaciente_ValidacaoFalha() {
        String dataNascimentoStr = "10/05/1990";
        CadastrarPacienteDTO pacienteDTO = new CadastrarPacienteDTO("12345678900", "Nome Paciente", dataNascimentoStr);

        when(pacienteService.cadastrar(pacienteDTO)).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> pacienteController.cadastrarPaciente(pacienteDTO));
    }

    @Test
    @DisplayName("Testando cadastrar paciente Erro Genérico")
    public void testCadastrarPaciente_ErroGenerico() {
        String dataNascimentoStr = "10/05/1990";
        CadastrarPacienteDTO pacienteDTO = new CadastrarPacienteDTO("12345678900", "Nome Paciente", dataNascimentoStr);
        String mensagemErro = "Erro genérico";

        when(pacienteService.cadastrar(pacienteDTO)).thenThrow(new RuntimeException(mensagemErro));

        ResponseEntity<?> response = pacienteController.cadastrarPaciente(pacienteDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ErrorDTO);
        assertEquals(mensagemErro, ((ErrorDTO) response.getBody()).getMessage());
    }
}
