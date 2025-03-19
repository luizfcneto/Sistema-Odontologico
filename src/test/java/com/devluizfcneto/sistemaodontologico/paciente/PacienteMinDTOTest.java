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
        assertEquals(24, pacienteDTO.getIdade());
    }

    @Test
    @DisplayName("Deve criar PacienteMinDTO com construtor que recebe parâmetros")
    void testCriarPacienteMinDTOComParametros() {
        PacienteMinDTO pacienteDTO = new PacienteMinDTO("Maria Oliveira", "20/06/1995");

        assertNotNull(pacienteDTO);
        assertEquals("Maria Oliveira", pacienteDTO.getNome());
        assertEquals("20/06/1995", pacienteDTO.getDataNascimento());
        assertEquals(29, pacienteDTO.getIdade());
    }

    @Test
    @DisplayName("Deve retornar e definir o nome corretamente")
    void testNome() {
        PacienteMinDTO pacienteDTO = new PacienteMinDTO("Nome", "30/01/1996");
        pacienteDTO.setNome("Novo Nome");
        assertEquals("Novo Nome", pacienteDTO.getNome());
    }

    @Test
    @DisplayName("Deve retornar e definir a data de nascimento corretamente")
    void testDataNascimento() {
        String data = "30/01/1996";
        PacienteMinDTO pacienteDTO = new PacienteMinDTO("Nome", data);
        pacienteDTO.setDataNascimento(data);
        assertEquals(data, pacienteDTO.getDataNascimento());
    }

    @Test
    @DisplayName("Deve retornar a idade corretamente")
    void testGetIdade() {
        PacienteMinDTO pacienteDTO = new PacienteMinDTO("Nome", "10/10/1990");
        assertEquals(34, pacienteDTO.getIdade());
    }
}
