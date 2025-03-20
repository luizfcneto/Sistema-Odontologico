package com.devluizfcneto.sistemaodontologico.paciente;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.repositories.PacienteRepository;
import com.devluizfcneto.sistemaodontologico.services.impl.PacienteServiceImpl;
import com.devluizfcneto.sistemaodontologico.validations.ListarPacienteParamsValidation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceListarTest {
    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ListarPacienteParamsValidation listarPacienteParamsValidation;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    @Test
    @DisplayName("Deve listar pacientes sem ordenação")
    void deveListarPacientesSemOrdenacao() {
        List<Paciente> pacientes = Arrays.asList(
                new Paciente(1L, "12345678901", "Paciente 1", null),
                new Paciente(2L, "98765432109", "Paciente 2", null)
        );
        when(pacienteRepository.listPacientesComConsultasFuturas(Sort.unsorted())).thenReturn(pacientes);

        List<Paciente> result = pacienteService.listarPacientes(null, null);

        assertEquals(pacientes.size(), result.size());
        assertEquals(pacientes.get(0).getNome(), result.get(0).getNome());
        assertEquals(pacientes.get(1).getNome(), result.get(1).getNome());

        verify(listarPacienteParamsValidation, times(1)).validateParameters(null);
        verify(pacienteRepository, times(1)).listPacientesComConsultasFuturas(Sort.unsorted());
    }

    @Test
    @DisplayName("Deve listar pacientes ordenados por nome em ordem crescente")
    void deveListarPacientesOrdenadosPorNomeAscendente() {
        String orderBy = "nome";
        String direction = "asc";
        Sort sort = Sort.by(Sort.Direction.ASC, orderBy);

        List<Paciente> pacientes = Arrays.asList(
                new Paciente(1L, "12345678901", "Paciente 1", null),
                new Paciente(2L, "98765432109", "Paciente 2", null)
        );
        when(pacienteRepository.listPacientesComConsultasFuturas(sort)).thenReturn(pacientes);

        List<Paciente> result = pacienteService.listarPacientes(orderBy, direction);

        assertEquals(pacientes.size(), result.size());
        assertEquals(pacientes.get(0).getNome(), result.get(0).getNome());
        assertEquals(pacientes.get(1).getNome(), result.get(1).getNome());

        verify(listarPacienteParamsValidation, times(1)).validateParameters(orderBy);
        verify(pacienteRepository, times(1)).listPacientesComConsultasFuturas(sort);
    }

    @Test
    @DisplayName("Deve listar pacientes ordenados por nome em ordem decrescente")
    void deveListarPacientesOrdenadosPorNomeDescendente() {
        String orderBy = "nome";
        String direction = "desc";
        Sort sort = Sort.by(Sort.Direction.DESC, orderBy);

        List<Paciente> pacientes = Arrays.asList(
                new Paciente(2L, "98765432109", "Paciente 2", null),
                new Paciente(1L, "12345678901", "Paciente 1", null)
        );
        when(pacienteRepository.listPacientesComConsultasFuturas(sort)).thenReturn(pacientes);

        List<Paciente> result = pacienteService.listarPacientes(orderBy, direction);

        assertEquals(pacientes.size(), result.size());
        assertEquals(pacientes.get(0).getNome(), result.get(0).getNome());
        assertEquals(pacientes.get(1).getNome(), result.get(1).getNome());

        verify(listarPacienteParamsValidation, times(1)).validateParameters(orderBy);
        verify(pacienteRepository, times(1)).listPacientesComConsultasFuturas(sort);
    }

    @Test
    @DisplayName("Deve chamar a validação de parametros")
    void deveChamarValidacaoDeParametros() {
        String orderBy = "nome";
        String direction = "desc";
        Sort sort = Sort.by(Sort.Direction.DESC, orderBy);

        List<Paciente> pacientes = Arrays.asList(
                new Paciente(2L, "98765432109", "Paciente 2", null),
                new Paciente(1L, "12345678901", "Paciente 1", null)
        );
        when(pacienteRepository.listPacientesComConsultasFuturas(sort)).thenReturn(pacientes);

        pacienteService.listarPacientes(orderBy, direction);

        verify(listarPacienteParamsValidation, times(1)).validateParameters(orderBy);
    }
}
