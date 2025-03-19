package com.devluizfcneto.sistemaodontologico.consulta;

import com.devluizfcneto.sistemaodontologico.controllers.ConsultaController;
import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.services.ConsultaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ControllerListarConsultaTest {
    @Mock
    private ConsultaService consultaService;

    @InjectMocks
    private ConsultaController consultaController;

    private List<ConsultaResponseDTO> consultas;

    @BeforeEach
    void setUp() {
        consultas = new ArrayList<>();
        // Add some sample ConsultaResponseDTO objects to the list
        ConsultaResponseDTO consulta1 = new ConsultaResponseDTO("01/01/2024", "08:00", "09:00", "01:00", null);
        consultas.add(consulta1);

        ConsultaResponseDTO consulta2 = new ConsultaResponseDTO("15/01/2024", "10:00", "11:00", "01:00", null);
        consultas.add(consulta2);

        ConsultaResponseDTO consulta3 = new ConsultaResponseDTO("31/01/2024", "14:00", "15:00", "01:00", null);
        consultas.add(consulta3);
    }

    @Test
    @DisplayName("Deve listar todas as consultas sem filtros")
    void testListarConsultasSemFiltros() {
        when(consultaService.listarConsultas(null, null, null)).thenReturn(consultas);

        ResponseEntity<?> responseEntity = consultaController.listarConsultas(null, null, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(consultas, responseEntity.getBody());
        verify(consultaService, times(1)).listarConsultas(null, null, null);
    }

    @Test
    @DisplayName("Deve listar consultas com filtro de data inicial")
    void testListarConsultasComDataInicial() {
        String dataInicial = "01/01/2024";
        when(consultaService.listarConsultas(dataInicial, null, null)).thenReturn(consultas);

        ResponseEntity<?> responseEntity = consultaController.listarConsultas(dataInicial, null, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(consultas, responseEntity.getBody());
        verify(consultaService, times(1)).listarConsultas(dataInicial, null, null);
    }

    @Test
    @DisplayName("Deve listar consultas com filtro de data final")
    void testListarConsultasComDataFinal() {
        String dataFinal = "31/01/2024";
        when(consultaService.listarConsultas(null, dataFinal, null)).thenReturn(consultas);

        ResponseEntity<?> responseEntity = consultaController.listarConsultas(null, dataFinal, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(consultas, responseEntity.getBody());
        verify(consultaService, times(1)).listarConsultas(null, dataFinal, null);
    }

    @Test
    @DisplayName("Deve listar consultas com filtro de data inicial e final")
    void testListarConsultasComDataInicialEFinal() {
        String dataInicial = "01/01/2024";
        String dataFinal = "31/01/2024";
        when(consultaService.listarConsultas(dataInicial, dataFinal, null)).thenReturn(consultas);

        ResponseEntity<?> responseEntity = consultaController.listarConsultas(dataInicial, dataFinal, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(consultas, responseEntity.getBody());
        verify(consultaService, times(1)).listarConsultas(dataInicial, dataFinal, null);
    }

    @Test
    @DisplayName("Deve listar consultas com filtro de data inicial, final e direction")
    void testListarConsultasComDataInicialEFinalEDirection() {
        String dataInicial = "01/01/2024";
        String dataFinal = "31/01/2024";
        String direction = "desc";
        when(consultaService.listarConsultas(dataInicial, dataFinal, direction)).thenReturn(consultas);

        ResponseEntity<?> responseEntity = consultaController.listarConsultas(dataInicial, dataFinal, direction);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(consultas, responseEntity.getBody());
        verify(consultaService, times(1)).listarConsultas(dataInicial, dataFinal, direction);
    }
}
