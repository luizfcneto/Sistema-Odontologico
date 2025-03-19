package com.devluizfcneto.sistemaodontologico.consulta;

import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.entities.Consulta;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.repositories.ConsultaRepository;
import com.devluizfcneto.sistemaodontologico.services.impl.ConsultaServiceImpl;
import com.devluizfcneto.sistemaodontologico.validations.ListarConsultaParamsValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceListarTest {
    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private ListarConsultaParamsValidation listarConsultaParamsValidation;

    @InjectMocks
    private ConsultaServiceImpl consultaService;

    private List<Consulta> consultas;

    @BeforeEach
    void setUp() {
        consultas = new ArrayList<>();

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente Teste");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));

        Consulta consulta1 = new Consulta();
        consulta1.setData(LocalDate.of(2024, 1, 15));
        consulta1.setHoraInicial(LocalTime.of(8, 0));
        consulta1.setHoraFinal(LocalTime.of(9, 0));
        consulta1.setTempo(LocalTime.of(1,0));
        consulta1.setPaciente(paciente);
        consultas.add(consulta1);

        Consulta consulta2 = new Consulta();
        consulta2.setData(LocalDate.of(2024, 2, 20));
        consulta2.setHoraInicial(LocalTime.of(10, 0));
        consulta2.setHoraFinal(LocalTime.of(11, 0));
        consulta2.setTempo(LocalTime.of(1,0));
        consulta2.setPaciente(paciente);
        consultas.add(consulta2);

        Consulta consulta3 = new Consulta();
        consulta3.setData(LocalDate.of(2024, 3, 10));
        consulta3.setHoraInicial(LocalTime.of(14, 0));
        consulta3.setHoraFinal(LocalTime.of(15, 0));
        consulta3.setTempo(LocalTime.of(1,0));
        consulta3.setPaciente(paciente);
        consultas.add(consulta3);
    }

    @Test
    @DisplayName("Deve listar todas as consultas sem filtros e ordenação ascendente")
    void testListarConsultasSemFiltrosAscendente() {
        Sort sort = Sort.by("data").ascending().and(Sort.by("horaInicial").ascending());
        when(consultaRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(consultas);

        List<ConsultaResponseDTO> response = consultaService.listarConsultas(null, null, "asc");

        assertEquals(consultas.size(), response.size());
        verify(listarConsultaParamsValidation, times(1)).validate(null, null);
        verify(consultaRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    @DisplayName("Deve listar todas as consultas sem filtros e ordenação descendente")
    void testListarConsultasSemFiltrosDescendente() {
        Sort sort = Sort.by("data").descending().and(Sort.by("horaInicial").ascending());
        when(consultaRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(consultas);

        List<ConsultaResponseDTO> response = consultaService.listarConsultas(null, null, "desc");

        assertEquals(consultas.size(), response.size());
        verify(listarConsultaParamsValidation, times(1)).validate(null, null);
        verify(consultaRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    @DisplayName("Deve listar consultas com filtro de data inicial e ordenação padrão")
    void testListarConsultasComDataInicial() {
        Sort sort = Sort.by("data").ascending().and(Sort.by("horaInicial").ascending());
        when(consultaRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(consultas);

        List<ConsultaResponseDTO> response = consultaService.listarConsultas("01/01/2024", null, null);

        assertEquals(consultas.size(), response.size());
        verify(listarConsultaParamsValidation, times(1)).validate("01/01/2024", null);
        verify(consultaRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    @DisplayName("Deve listar consultas com filtro de data final e ordenação padrão")
    void testListarConsultasComDataFinal() {
        Sort sort = Sort.by("data").ascending().and(Sort.by("horaInicial").ascending());
        when(consultaRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(consultas);

        List<ConsultaResponseDTO> response = consultaService.listarConsultas(null, "31/03/2024", null);

        assertEquals(consultas.size(), response.size());
        verify(listarConsultaParamsValidation, times(1)).validate(null, "31/03/2024");
        verify(consultaRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    @DisplayName("Deve listar consultas com filtro de data inicial e final e ordenação padrão")
    void testListarConsultasComDataInicialEFinal() {
        Sort sort = Sort.by("data").ascending().and(Sort.by("horaInicial").ascending());
        when(consultaRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(consultas);

        List<ConsultaResponseDTO> response = consultaService.listarConsultas("01/01/2024", "31/03/2024", null);

        assertEquals(consultas.size(), response.size());
        verify(listarConsultaParamsValidation, times(1)).validate("01/01/2024", "31/03/2024");
        verify(consultaRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    @DisplayName("Deve listar consultas com filtro de data inicial e final e ordenação descendente")
    void testListarConsultasComDataInicialEFinalDescendente() {
        Sort sort = Sort.by("data").descending().and(Sort.by("horaInicial").ascending());
        when(consultaRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(consultas);

        List<ConsultaResponseDTO> response = consultaService.listarConsultas("01/01/2024", "31/03/2024", "desc");

        assertEquals(consultas.size(), response.size());
        verify(listarConsultaParamsValidation, times(1)).validate("01/01/2024", "31/03/2024");
        verify(consultaRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }
}
