package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;
import com.devluizfcneto.sistemaodontologico.errors.PacienteAlreadyExistsException;
import com.devluizfcneto.sistemaodontologico.repositories.PacienteRepository;
import com.devluizfcneto.sistemaodontologico.services.impl.PacienteServiceImpl;
import com.devluizfcneto.sistemaodontologico.utils.DateUtils;
import com.devluizfcneto.sistemaodontologico.validations.CadastrarPacienteValidation;

public class PacienteServiceCadastrarTest {
	
	@Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private CadastrarPacienteValidation cadastrarPacienteValidation;

    @InjectMocks
    private PacienteServiceImpl pacienteService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testando cadastrar paciente com sucesso")
    public void testCadastrarPacienteComSucesso() {
        String dataNascimentoStr = "10/05/1990";
        CadastrarPacienteDTO pacienteDTO = new CadastrarPacienteDTO("12345678900", "Nome Paciente", dataNascimentoStr);
        LocalDate dataNascimento = DateUtils.formatStringToLocalDate(dataNascimentoStr);
        Paciente paciente = new Paciente("12345678900", "Nome Paciente", dataNascimento);

        when(pacienteRepository.findByCpf(pacienteDTO.getCpf())).thenReturn(null); // Ou Optional.empty() se findByCpf retornar Optional
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente pacienteCadastrado = pacienteService.cadastrar(pacienteDTO);

        assertNotNull(pacienteCadastrado);
        assertEquals("12345678900", pacienteCadastrado.getCpf());
        assertEquals("Nome Paciente", pacienteCadastrado.getNome());
        assertEquals(dataNascimento, pacienteCadastrado.getDataNascimento());

        verify(cadastrarPacienteValidation).validateCadastrarPaciente(pacienteDTO);
        verify(pacienteRepository).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Testando cadastrar paciente com erro, paciente já existente")
    public void testCadastrarPacienteComCpfExistente() {
        CadastrarPacienteDTO pacienteDTO = new CadastrarPacienteDTO("12345678900", "Nome Paciente", "10/05/1990");
        Paciente pacienteExistente = new Paciente("12345678900", "Outro Nome", LocalDate.now());

        when(pacienteRepository.findByCpf(pacienteDTO.getCpf())).thenReturn(pacienteExistente); // Retorna o paciente existente

        assertThrows(PacienteAlreadyExistsException.class, () -> pacienteService.cadastrar(pacienteDTO));

        verify(cadastrarPacienteValidation).validateCadastrarPaciente(pacienteDTO);
        verify(pacienteRepository, never()).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Testando cadastrar paciente com erro, validacao")
    public void testCadastrarPacienteComValidacaoFalha() {
        CadastrarPacienteDTO pacienteDTO = new CadastrarPacienteDTO("12345678900", "Nome Paciente", "10/05/1990");

        doThrow(new BadRequestException("Erro de validação")).when(cadastrarPacienteValidation).validateCadastrarPaciente(pacienteDTO);

        assertThrows(BadRequestException.class, () -> pacienteService.cadastrar(pacienteDTO));

        verify(cadastrarPacienteValidation).validateCadastrarPaciente(pacienteDTO);
        verify(pacienteRepository, never()).save(any(Paciente.class));
    }
}
