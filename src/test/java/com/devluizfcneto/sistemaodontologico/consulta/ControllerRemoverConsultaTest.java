package com.devluizfcneto.sistemaodontologico.consulta;

import com.devluizfcneto.sistemaodontologico.controllers.ConsultaController;
import com.devluizfcneto.sistemaodontologico.errors.GlobalExceptionHandler;
import com.devluizfcneto.sistemaodontologico.services.ConsultaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerRemoverConsultaTest {

    private MockMvc mockMvc;

    @Mock
    private ConsultaService consultaService;

    @InjectMocks
    private ConsultaController consultaController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(consultaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Deve retornar 204 No Content ao remover consulta existente")
    void removerConsulta_Sucesso() throws Exception {
        Long id = 1L;
        doNothing().when(consultaService).remover(id);

        mockMvc.perform(delete("/api/consulta/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(consultaService, times(1)).remover(id);
    }

    @Test
    @DisplayName("Deve retornar 500 Internal Server Error em caso de erro inesperado")
    void removerConsulta_ErroInterno() throws Exception {
        Long id = 1L;
        String errorMessage = "Ocorreu um erro interno no servidor.";
        doThrow(new RuntimeException(errorMessage))
                .when(consultaService).remover(id);

        mockMvc.perform(delete("/api/consulta/{id}", id))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}