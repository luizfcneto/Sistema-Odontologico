package com.devluizfcneto.sistemaodontologico.paciente;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.devluizfcneto.sistemaodontologico.controllers.PacienteController;
import com.devluizfcneto.sistemaodontologico.errors.GlobalExceptionHandler;
import com.devluizfcneto.sistemaodontologico.errors.PacienteNotFoundException;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;

@ExtendWith(MockitoExtension.class)
public class ControllerRemoverPacienteTest {

    private MockMvc mockMvc;

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pacienteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Deve retornar 204 No Content ao remover paciente existente")
    void removerPaciente_Sucesso() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(pacienteService).remover(id);

        // Act & Assert
        mockMvc.perform(delete("/api/paciente/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(pacienteService, times(1)).remover(id);
    }

    @Test
    @DisplayName("Deve retornar 404 Not Found ao tentar remover paciente inexistente")
    void removerPaciente_NaoEncontrado() throws Exception {
        // Arrange
        Long id = 999L;
        doThrow(new PacienteNotFoundException("Paciente não encontrado"))
            .when(pacienteService).remover(id);

        // Act & Assert
        mockMvc.perform(delete("/api/paciente/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Paciente não encontrado"));
    }

    @Test
    @DisplayName("Deve retornar 500 Internal Server Error em caso de erro inesperado")
    void removerPaciente_ErroInterno() throws Exception {
        // Arrange
        Long id = 1L;
        String errorMessage = "Ocorreu um erro interno no servidor.";
        doThrow(new RuntimeException(errorMessage))
            .when(pacienteService).remover(id);

        // Act & Assert
        mockMvc.perform(delete("/api/paciente/{id}", id))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }
}
