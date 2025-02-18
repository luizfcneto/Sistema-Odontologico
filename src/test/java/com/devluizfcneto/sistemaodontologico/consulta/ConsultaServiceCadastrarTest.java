package com.devluizfcneto.sistemaodontologico.consulta;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.entities.Consulta;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.ConsultaAlreadyMadeException;
import com.devluizfcneto.sistemaodontologico.errors.ConsultaConflictedException;
import com.devluizfcneto.sistemaodontologico.repositories.ConsultaRepository;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;
import com.devluizfcneto.sistemaodontologico.services.impl.ConsultaServiceImpl;
import com.devluizfcneto.sistemaodontologico.validations.CadastrarConsultaValidation;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceCadastrarTest {
	
	@InjectMocks
    private ConsultaServiceImpl consultaService;

    @Mock
    private PacienteService pacienteService;

    @Mock
    private CadastrarConsultaValidation cadastrarConsultaValidation;

    @Mock
    private ConsultaRepository consultaRepository;

    @Test
    @DisplayName("Deve cadastrar uma consulta com sucesso")
    void testCadastrarConsultaSucesso() {
        CadastrarConsultaDTO consultaDTO = new CadastrarConsultaDTO(1L, "31/12/2024", "0800", "0900");
        consultaDTO.calcularTempo();
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("João da Silva");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));
        Consulta consulta = new Consulta(consultaDTO, paciente);

        when(pacienteService.buscar(1L)).thenReturn(paciente);
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        ConsultaResponseDTO response = consultaService.cadastrar(consultaDTO);

        assertNotNull(response);
        assertEquals("31/12/2024", response.getData());
        assertEquals("08:00", response.getHoraInicial());
        assertEquals("09:00", response.getHoraFinal());
        assertEquals("01:00", response.getTempo());
        assertEquals("João da Silva", response.getPaciente().getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção se o paciente já possuir consulta futura")
    void testPacienteComConsultaFutura() {
        CadastrarConsultaDTO consultaDTO = new CadastrarConsultaDTO(1L, "31/12/2024", "0800", "0900");
        consultaDTO.calcularTempo();
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        when(pacienteService.buscar(1L)).thenReturn(paciente);
        when(consultaRepository.existeConsultaFutura(1L, LocalDate.now())).thenReturn(true);

        assertThrows(ConsultaAlreadyMadeException.class, () -> consultaService.cadastrar(consultaDTO));
    }

    @Test
    @DisplayName("Deve lançar exceção se houver conflito de horário com outra consulta")
    void testConflitoHorario() {
        CadastrarConsultaDTO consultaDTO = new CadastrarConsultaDTO(1L, "31/12/2024", "0800", "0900");
        consultaDTO.calcularTempo();
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        when(pacienteService.buscar(1L)).thenReturn(paciente);
        when(consultaRepository.findConflitosHorario(any(LocalDate.class), any(LocalTime.class), any(LocalTime.class)))
                .thenReturn(Collections.singletonList(new Consulta()));

        assertThrows(ConsultaConflictedException.class, () -> consultaService.cadastrar(consultaDTO));
    }

    @Test
    @DisplayName("Deve retornar true se o paciente possuir consulta futura")
    void testChecaPacientePossuiConsultaFuturaTrue() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        when(consultaRepository.existeConsultaFutura(1L, LocalDate.now())).thenReturn(true);

        assertThrows(ConsultaAlreadyMadeException.class, () -> consultaService.checaPacientePossuiConsultaFutura(paciente, LocalDate.now()));
    }

    @Test
    @DisplayName("Deve retornar false se o paciente não possuir consulta futura")
    void testChecaPacientePossuiConsultaFuturaFalse() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        when(consultaRepository.existeConsultaFutura(1L, LocalDate.now())).thenReturn(false);

        assertFalse(consultaService.checaPacientePossuiConsultaFutura(paciente, LocalDate.now()));
    }

    @Test
    @DisplayName("Deve lançar exceção se houver conflito de horário")
    void testChecaColisaoConsultaComConflito() {
        LocalDate now = LocalDate.now();
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);
        List<Consulta> consultas = Collections.singletonList(new Consulta());

        when(consultaRepository.findConflitosHorario(now, horaInicial, horaFinal)).thenReturn(consultas);

        assertThrows(ConsultaConflictedException.class, () -> consultaService.checaColisaoConsulta(now, horaInicial, horaFinal));
    }

    @Test
    @DisplayName("Não deve lançar exceção se não houver conflito de horário")
    void testChecaColisaoConsultaSemConflito() {
        LocalDate now = LocalDate.now();
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);

        when(consultaRepository.findConflitosHorario(now, horaInicial, horaFinal)).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> consultaService.checaColisaoConsulta(now, horaInicial, horaFinal));
    }
	
}
