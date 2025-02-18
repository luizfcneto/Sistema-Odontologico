package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devluizfcneto.sistemaodontologico.dtos.PacienteMinDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;

public class PacienteMinDTOTest {

	@Test
    @DisplayName("Deve criar PacienteMinDTO com construtor que recebe Paciente")
    void testCriarPacienteMinDTOComPaciente() {
        LocalDate dataNascimento = LocalDate.of(2000, 05, 15);
        Paciente paciente = new Paciente();
        paciente.setNome("João da Silva");
        paciente.setDataNascimento(dataNascimento);

        PacienteMinDTO pacienteDTO = new PacienteMinDTO(paciente);

        assertNotNull(pacienteDTO);
        assertEquals("João da Silva", pacienteDTO.getNome());
        assertEquals("15/05/2000", pacienteDTO.getDataNascimento());
    }

    @Test
    @DisplayName("Deve criar PacienteMinDTO com construtor que recebe parâmetros")
    void testCriarPacienteMinDTOComParametros() {
        PacienteMinDTO pacienteDTO = new PacienteMinDTO("Maria Oliveira", "20/06/1995");

        assertNotNull(pacienteDTO);
        assertEquals("Maria Oliveira", pacienteDTO.getNome());
        assertEquals("20/06/1995", pacienteDTO.getDataNascimento());
    }

    @Test
    @DisplayName("Deve retornar e definir o nome corretamente")
    void testNome() {
        PacienteMinDTO pacienteDTO = new PacienteMinDTO("Nome", "Data");
        pacienteDTO.setNome("Novo Nome");
        assertEquals("Novo Nome", pacienteDTO.getNome());
    }

    @Test
    @DisplayName("Deve retornar e definir a data de nascimento corretamente")
    void testDataNascimento() {
        PacienteMinDTO pacienteDTO = new PacienteMinDTO("Nome", "Data");
        pacienteDTO.setDataNascimento("Nova Data");
        assertEquals("Nova Data", pacienteDTO.getDataNascimento());
    }
}
