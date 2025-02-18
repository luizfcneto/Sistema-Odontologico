package com.devluizfcneto.sistemaodontologico.consulta;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;

public class CadastrarConsultaDTOTest {
	private CadastrarConsultaDTO consultaDTO;

    @BeforeEach
    void setUp() {
        consultaDTO = new CadastrarConsultaDTO(
            1L, 
            "2024-05-15", 
            "0900", 
            "1030"
        );
    }

    @Test
    @DisplayName("Deve calcular tempo corretamente para intervalo normal")
    void testCalculaTempo_Normal() {
    	consultaDTO.calcularTempo();
        assertEquals("0130", consultaDTO.getTempo());
    }

    @Test
    @DisplayName("Deve calcular tempo corretamente para intervalo exato")
    void testCalculaTempo_IntervaloExato() {
        consultaDTO.setHoraFinal("1200");
        consultaDTO.setHoraInicial("1000");
        consultaDTO.calcularTempo();
        assertEquals("0200", consultaDTO.getTempo());
    }

    @Test
    @DisplayName("Deve retornar 00:00 para mesmo horário")
    void testCalculaTempo_IntervaloZerado() {
        consultaDTO.setHoraFinal(consultaDTO.getHoraInicial());
        consultaDTO.calcularTempo();
        assertEquals("0000", consultaDTO.getTempo());
    }

    @Test
    @DisplayName("Deve atualizar tempo automaticamente ao modificar horários")
    void testAtualizacaoAutomaticaTempo() {
        consultaDTO.setHoraFinal("1130");
        consultaDTO.calcularTempo();
        assertEquals("0230", consultaDTO.getTempo());
    }

    @Test
    @DisplayName("Testando getTempo e setHoraInicial e setHoraFinal")
    void testTempo() {
        consultaDTO.setHoraInicial("0915");
        consultaDTO.setHoraFinal("0945");
        consultaDTO.calcularTempo();
        assertEquals("0030", consultaDTO.getTempo());
    }

    @Test
    @DisplayName("Testando get e setData")
    void testData() {
        consultaDTO.setData("2024-12-31");
        assertEquals("2024-12-31", consultaDTO.getData());
    }

    @Test
    @DisplayName("Deve manter consistência nos dados após modificações")
    void testConsistenciaDados() {
        consultaDTO.setPacienteId(2L);
        consultaDTO.setData("2024-06-01");
        consultaDTO.setHoraInicial("1400");
        consultaDTO.setHoraFinal("1530");
        consultaDTO.calcularTempo();
        
        assertAll("Verificar todos os campos",
            () -> assertEquals(2L, consultaDTO.getPacienteId()),
            () -> assertEquals("2024-06-01", consultaDTO.getData()),
            () -> assertEquals("1400", consultaDTO.getHoraInicial()),
            () -> assertEquals("1530", consultaDTO.getHoraFinal()),
            () -> assertEquals("0130", consultaDTO.getTempo())
        );
    }
}
