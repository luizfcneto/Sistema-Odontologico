package com.devluizfcneto.sistemaodontologico.paciente;

import com.devluizfcneto.sistemaodontologico.controllers.PacienteController;
import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteResponseDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ControllerListarPacienteTest {

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    private List<Paciente> pacientes;

    @BeforeEach
    void setUp() {
        pacientes = new ArrayList<>();
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        paciente1.setNome("João da Silva");
        paciente1.setCpf("12345678901");
        paciente1.setDataNascimento(LocalDate.of(1990, 1, 1));
        pacientes.add(paciente1);

        Paciente paciente2 = new Paciente();
        paciente2.setId(2L);
        paciente2.setNome("Maria Oliveira");
        paciente2.setCpf("98765432109");
        paciente2.setDataNascimento(LocalDate.of(1985, 5, 10));
        pacientes.add(paciente2);

        Paciente paciente3 = new Paciente();
        paciente3.setId(3L);
        paciente3.setNome("José Almeida");
        paciente3.setCpf("11223344556");
        paciente3.setDataNascimento(LocalDate.of(2000, 12, 25));
        pacientes.add(paciente3);
    }

    @Test
    @DisplayName("Deve listar todos os pacientes sem filtros")
    void testListarPacientesSemFiltros() {
        when(pacienteService.listarPacientes(null, null)).thenReturn(pacientes);

        ResponseEntity<?> responseEntity = pacienteController.listarPacientes(null, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pacientes, responseEntity.getBody());
        verify(pacienteService, times(1)).listarPacientes(null, null);
    }

    @Test
    @DisplayName("Deve listar pacientes com filtro de ordenação por nome")
    void testListarPacientesComOrderByNome() {
        String orderBy = "nome";
        when(pacienteService.listarPacientes(orderBy, null)).thenReturn(pacientes);

        ResponseEntity<?> responseEntity = pacienteController.listarPacientes(orderBy, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pacientes, responseEntity.getBody());
        verify(pacienteService, times(1)).listarPacientes(orderBy, null);
    }

    @Test
    @DisplayName("Deve listar pacientes com filtro de ordenação por cpf")
    void testListarPacientesComOrderByCpf() {
        String orderBy = "cpf";
        when(pacienteService.listarPacientes(orderBy, null)).thenReturn(pacientes);

        ResponseEntity<?> responseEntity = pacienteController.listarPacientes(orderBy, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pacientes, responseEntity.getBody());
        verify(pacienteService, times(1)).listarPacientes(orderBy, null);
    }

    @Test
    @DisplayName("Deve listar pacientes com filtro de direção ascendente")
    void testListarPacientesComDirectionAsc() {
        String direction = "asc";
        when(pacienteService.listarPacientes(null, direction)).thenReturn(pacientes);

        ResponseEntity<?> responseEntity = pacienteController.listarPacientes(null, direction);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pacientes, responseEntity.getBody());
        verify(pacienteService, times(1)).listarPacientes(null, direction);
    }

    @Test
    @DisplayName("Deve listar pacientes com filtro de direção descendente")
    void testListarPacientesComDirectionDesc() {
        String direction = "desc";
        when(pacienteService.listarPacientes(null, direction)).thenReturn(pacientes);

        ResponseEntity<?> responseEntity = pacienteController.listarPacientes(null, direction);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pacientes, responseEntity.getBody());
        verify(pacienteService, times(1)).listarPacientes(null, direction);
    }

    @Test
    @DisplayName("Deve listar pacientes com filtro de ordenação e direção")
    void testListarPacientesComOrderByEDirection() {
        String orderBy = "nome";
        String direction = "desc";
        when(pacienteService.listarPacientes(orderBy, direction)).thenReturn(pacientes);

        ResponseEntity<?> responseEntity = pacienteController.listarPacientes(orderBy, direction);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pacientes, responseEntity.getBody());
        verify(pacienteService, times(1)).listarPacientes(orderBy, direction);
    }
}
