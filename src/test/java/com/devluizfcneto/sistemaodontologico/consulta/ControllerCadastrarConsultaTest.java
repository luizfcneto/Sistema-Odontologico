package com.devluizfcneto.sistemaodontologico.consulta;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.devluizfcneto.sistemaodontologico.controllers.ConsultaController;
import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.dtos.PacienteMinDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.ConsultaAlreadyMadeException;
import com.devluizfcneto.sistemaodontologico.errors.ConsultaConflictedException;
import com.devluizfcneto.sistemaodontologico.errors.GlobalExceptionHandler;
import com.devluizfcneto.sistemaodontologico.services.ConsultaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ControllerCadastrarConsultaTest {

	
	private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Mock
    private ConsultaService consultaService;

    @InjectMocks
    private ConsultaController consultaController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(consultaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Deve cadastrar uma consulta com dados válidos")
    void testCadastrarConsulta_Sucesso() throws Exception {
        CadastrarConsultaDTO consultaDTO = new CadastrarConsultaDTO(1L, "31/12/2024", "0800", "0900");
        consultaDTO.calcularTempo();
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Nome Paciente");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1)); 

        ConsultaResponseDTO consultaResponseDTO = new ConsultaResponseDTO(
                "31/12/2024",
                "08:00",
                "09:00",
                "01:00",
                new PacienteMinDTO(paciente)
        );

        when(consultaService.cadastrar(any(CadastrarConsultaDTO.class))).thenReturn(consultaResponseDTO);

        mockMvc.perform(post("/api/consulta/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consultaDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value("31/12/2024"))
                .andExpect(jsonPath("$.horaInicial").value("08:00"))
                .andExpect(jsonPath("$.horaFinal").value("09:00"))
                .andExpect(jsonPath("$.tempo").value("01:00"))
                .andExpect(jsonPath("$.paciente.nome").value("Nome Paciente"));
    }
    
    @Test
    @DisplayName("Deve retornar conflito (409) se a consulta já foi agendada")
    void testCadastrarConsulta_ConsultaJaAgendada() throws Exception {
        CadastrarConsultaDTO consultaDTO = new CadastrarConsultaDTO(1L, "31/12/2024", "0800", "0900");

        when(consultaService.cadastrar(any(CadastrarConsultaDTO.class)))
                .thenThrow(new ConsultaAlreadyMadeException("Erro, paciente já possui consulta marcada"));

        mockMvc.perform(post("/api/consulta/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consultaDTO)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Erro, paciente já possui consulta marcada"));
    }

    @Test
    @DisplayName("Deve retornar conflito (409) se houver conflito de horário")
    void testCadastrarConsulta_ConflitoHorario() throws Exception {
        CadastrarConsultaDTO consultaDTO = new CadastrarConsultaDTO(1L, "31/12/2024", "0800", "0900");

        when(consultaService.cadastrar(any(CadastrarConsultaDTO.class)))
                .thenThrow(new ConsultaConflictedException("Erro, já existe consulta marcada dentro deste horário"));

        mockMvc.perform(post("/api/consulta/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consultaDTO)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Erro, já existe consulta marcada dentro deste horário"));
    }

    @Test
    @DisplayName("Deve retornar erro interno (500) se ocorrer um erro genérico")
    void testCadastrarConsulta_ErroGenerico() throws Exception {
        CadastrarConsultaDTO consultaDTO = new CadastrarConsultaDTO(1L, "31/12/2024", "0800", "0900");

        when(consultaService.cadastrar(any(CadastrarConsultaDTO.class)))
                .thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(post("/api/consulta/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consultaDTO)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ocorreu um erro interno no servidor."));
    }
}
