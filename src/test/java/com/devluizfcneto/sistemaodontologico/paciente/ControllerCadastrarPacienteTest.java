package com.devluizfcneto.sistemaodontologico.paciente;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

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

import com.devluizfcneto.sistemaodontologico.controllers.PacienteController;
import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.errors.GlobalExceptionHandler;
import com.devluizfcneto.sistemaodontologico.errors.PacienteAlreadyExistsException;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ControllerCadastrarPacienteTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
	
	private MockMvc mockMvc;

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    public void setup() {
    	objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        mockMvc = MockMvcBuilders.standaloneSetup(pacienteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Deve cadastrar paciente com dados válidos")
    public void testCadastrarPaciente_Sucesso() throws Exception {
        String dataNascimentoStr = "10/05/1990";
        CadastrarPacienteDTO dto = new CadastrarPacienteDTO("12345678900", "Nome Paciente", dataNascimentoStr);
        Paciente pacienteSalvo = new Paciente("12345678900", "Nome Paciente", LocalDate.of(1990, 5, 10));

        when(pacienteService.cadastrar(any(CadastrarPacienteDTO.class))).thenReturn(pacienteSalvo);
        LocalDate dataNascimento = DateUtils.formatStringToLocalDate(dataNascimentoStr);
        int idadeEsperada = Period.between(dataNascimento, LocalDate.now()).getYears();

        mockMvc.perform(post("/api/paciente/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value("12345678900"))
                .andExpect(jsonPath("$.nome").value("Nome Paciente"))
                .andExpect(jsonPath("$.dataNascimento").value("10/05/1990"))
                .andExpect(jsonPath("$.idade").value(idadeEsperada));
    }
    
    @Test
    @DisplayName("Deve falhar ao cadastrar paciente com CPF existente")
    public void testCadastrarPaciente_CpfExistente() throws Exception {
        String dataNascimentoStr = "10/05/1990";
        CadastrarPacienteDTO dto = new CadastrarPacienteDTO("12345678900", "Nome Paciente", dataNascimentoStr);

        when(pacienteService.cadastrar(any(CadastrarPacienteDTO.class)))
            .thenThrow(new PacienteAlreadyExistsException("CPF já cadastrado"));

        mockMvc.perform(post("/api/paciente/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("CPF já cadastrado"));
    }

    @Test
    @DisplayName("Deve falhar ao cadastrar paciente com dados inválidos")
    public void testCadastrarPaciente_ValidacaoFalha() throws Exception {
                String dataNascimentoStr = "10/05/2020";
        CadastrarPacienteDTO dto = new CadastrarPacienteDTO("12345678900", "Nome", dataNascimentoStr);

        when(pacienteService.cadastrar(any(CadastrarPacienteDTO.class)))
            .thenThrow(new BadRequestException("Dados inválidos"));

        mockMvc.perform(post("/api/paciente/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Dados inválidos"));
    }

    @Test
    @DisplayName("Deve retornar erro interno ao ocorrer uma exceção não tratada")
    public void testCadastrarPaciente_ErroGenerico() throws Exception {
        String dataNascimentoStr = "10/05/1990";
        CadastrarPacienteDTO dto = new CadastrarPacienteDTO("12345678900", "Nome Paciente", dataNascimentoStr);

        when(pacienteService.cadastrar(any(CadastrarPacienteDTO.class)))
            .thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(post("/api/paciente/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Ocorreu um erro interno no servidor."));
    }
}
