package com.devluizfcneto.sistemaodontologico.consulta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.dtos.PacienteMinDTO;
import com.devluizfcneto.sistemaodontologico.entities.Consulta;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;

public class ConsultaResponseDTOTest {
    @Test
    @DisplayName("Deve criar ConsultaResponseDTO com construtor que recebe Consulta")
    void testCriarConsultaResponseDTOComConsulta() {
        LocalDate data = LocalDate.of(2024, 12, 31);
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);
        LocalTime tempo = LocalTime.of(1, 0);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("João da Silva");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));

        Consulta consulta = new Consulta();
        consulta.setData(data);
        consulta.setHoraInicial(horaInicial);
        consulta.setHoraFinal(horaFinal);
        consulta.setTempo(tempo);
        consulta.setPaciente(paciente);

        ConsultaResponseDTO consultaDTO = new ConsultaResponseDTO(consulta);

        assertNotNull(consultaDTO);
        assertEquals("31/12/2024", consultaDTO.getData());
        assertEquals("08:00", consultaDTO.getHoraInicial());
        assertEquals("09:00", consultaDTO.getHoraFinal());
        assertEquals("01:00", consultaDTO.getTempo());
        assertNotNull(consultaDTO.getPaciente());
        assertEquals("João da Silva", consultaDTO.getPaciente().getNome());
        assertEquals("01/01/1990", consultaDTO.getPaciente().getDataNascimento());
    }

    @Test
    @DisplayName("Deve criar ConsultaResponseDTO com construtor que recebe parâmetros")
    void testCriarConsultaResponseDTOComParametros() {
        PacienteMinDTO pacienteMinDTO = new PacienteMinDTO("Maria Oliveira", "20/06/1995");

        ConsultaResponseDTO consultaDTO = new ConsultaResponseDTO(
                "30/12/2024",
                "10:00",
                "11:00",
                "01:00",
                pacienteMinDTO
        );

        assertNotNull(consultaDTO);
        assertEquals("30/12/2024", consultaDTO.getData());
        assertEquals("10:00", consultaDTO.getHoraInicial());
        assertEquals("11:00", consultaDTO.getHoraFinal());
        assertEquals("01:00", consultaDTO.getTempo());
        assertNotNull(consultaDTO.getPaciente());
        assertEquals("Maria Oliveira", consultaDTO.getPaciente().getNome());
        assertEquals("20/06/1995", consultaDTO.getPaciente().getDataNascimento());
    }

    @Test
    @DisplayName("Deve retornar e definir a data corretamente")
    void testData() {
        String dataNascimento = "30/01/1996";
        ConsultaResponseDTO consultaDTO = new ConsultaResponseDTO("30/12/2024", "10:00", "11:00", "01:00", new PacienteMinDTO("Nome", dataNascimento));
        consultaDTO.setData("01/01/2025");
        assertEquals("01/01/2025", consultaDTO.getData());
    }

    @Test
    @DisplayName("Deve retornar e definir horaInicial corretamente")
    void testHoraInicial() {
        String dataNascimento = "30/01/1996";
        ConsultaResponseDTO consultaDTO = new ConsultaResponseDTO("30/12/2024", "10:00", "11:00", "01:00", new PacienteMinDTO("Nome", dataNascimento));
        consultaDTO.setHoraInicial("12:00");
        assertEquals("12:00", consultaDTO.getHoraInicial());
    }

    @Test
    @DisplayName("Deve retornar e definir horaFinal corretamente")
    void testHoraFinal() {
        String dataNascimento = "30/01/1996";
        ConsultaResponseDTO consultaDTO = new ConsultaResponseDTO("30/12/2024", "10:00", "11:00", "01:00", new PacienteMinDTO("Nome", dataNascimento));
        consultaDTO.setHoraFinal("13:00");
        assertEquals("13:00", consultaDTO.getHoraFinal());
    }

    @Test
    @DisplayName("Deve retornar e definir tempo corretamente")
    void testTempo() {
        String dataNascimento = "30/01/1996";
        ConsultaResponseDTO consultaDTO = new ConsultaResponseDTO("30/12/2024", "10:00", "11:00", "01:00", new PacienteMinDTO("Nome", dataNascimento));
        consultaDTO.setTempo("02:00");
        assertEquals("02:00", consultaDTO.getTempo());
    }

    @Test
    @DisplayName("Deve retornar e definir paciente corretamente")
    void testPaciente() {
        String dataNascimento = "30/01/1996";
        PacienteMinDTO paciente = new PacienteMinDTO("José Almeida", "01/01/2000");
        ConsultaResponseDTO consultaDTO = new ConsultaResponseDTO("30/12/2024", "10:00", "11:00", "01:00", new PacienteMinDTO("Nome", dataNascimento));
        consultaDTO.setPaciente(paciente);
        assertEquals(paciente, consultaDTO.getPaciente());
    }
}
