package com.devluizfcneto.sistemaodontologico.consulta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.entities.Consulta;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;

public class ConsultaTest {
	
	@Test
    @DisplayName("Deve criar uma consulta com os dados do DTO e paciente")
    void testCriarConsultaComDTOePaciente() {
        CadastrarConsultaDTO consultaDTO = new CadastrarConsultaDTO();
        consultaDTO.setData("31/12/2024");
        consultaDTO.setHoraInicial("0800");
        consultaDTO.setHoraFinal("0900");
        consultaDTO.calcularTempo();

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("João da Silva");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));

        Consulta consulta = new Consulta(consultaDTO, paciente);

        assertNotNull(consulta);
        assertEquals(LocalDate.of(2024, 12, 31), consulta.getData());
        assertEquals(LocalTime.of(8, 0), consulta.getHoraInicial());
        assertEquals(LocalTime.of(9, 0), consulta.getHoraFinal());
        assertEquals(LocalTime.of(1, 0), consulta.getTempo());
        assertEquals(paciente, consulta.getPaciente());
    }

    @Test
    @DisplayName("Deve criar uma consulta com os dados fornecidos")
    void testCriarConsultaComDados() {
        LocalDate data = LocalDate.of(2024, 12, 31);
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);
        LocalTime tempo = LocalTime.of(1, 0);
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Maria Oliveira");
        paciente.setDataNascimento(LocalDate.of(1995, 6, 20));

        Consulta consulta = new Consulta(data, horaInicial, horaFinal, tempo, paciente);

        assertNotNull(consulta);
        assertEquals(data, consulta.getData());
        assertEquals(horaInicial, consulta.getHoraInicial());
        assertEquals(horaFinal, consulta.getHoraFinal());
        assertEquals(tempo, consulta.getTempo());
        assertEquals(paciente, consulta.getPaciente());
    }

    @Test
    @DisplayName("Deve criar uma consulta com ID e outros dados")
    void testCriarConsultaComIDeDados() {
        Long id = 1L;
        LocalDate data = LocalDate.of(2024, 12, 31);
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);
        LocalTime tempo = LocalTime.of(1, 0);
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("José Almeida");
        paciente.setDataNascimento(LocalDate.of(2000, 1, 1));


        Consulta consulta = new Consulta(id, data, horaInicial, horaFinal, tempo, paciente);

        assertNotNull(consulta);
        assertEquals(id, consulta.getId());
        assertEquals(data, consulta.getData());
        assertEquals(horaInicial, consulta.getHoraInicial());
        assertEquals(horaFinal, consulta.getHoraFinal());
        assertEquals(tempo, consulta.getTempo());
        assertEquals(paciente, consulta.getPaciente());
    }

    @Test
    @DisplayName("Deve retornar e definir o ID da consulta")
    void testGetSetId() {
        Consulta consulta = new Consulta();
        consulta.setId(1L);
        assertEquals(1L, consulta.getId());
    }

    @Test
    @DisplayName("Deve retornar e definir a data da consulta")
    void testGetSetData() {
        Consulta consulta = new Consulta();
        LocalDate data = LocalDate.of(2024, 12, 31);
        consulta.setData(data);
        assertEquals(data, consulta.getData());
    }

    @Test
    @DisplayName("Deve retornar e definir a hora inicial da consulta")
    void testGetSetHoraInicial() {
        Consulta consulta = new Consulta();
        LocalTime horaInicial = LocalTime.of(8, 0);
        consulta.setHoraInicial(horaInicial);
        assertEquals(horaInicial, consulta.getHoraInicial());
    }

    @Test
    @DisplayName("Deve retornar e definir a hora final da consulta")
    void testGetSetHoraFinal() {
        Consulta consulta = new Consulta();
        LocalTime horaFinal = LocalTime.of(9, 0);
        consulta.setHoraFinal(horaFinal);
        assertEquals(horaFinal, consulta.getHoraFinal());
    }

    @Test
    @DisplayName("Deve retornar e definir o tempo da consulta")
    void testGetSetTempo() {
        Consulta consulta = new Consulta();
        LocalTime tempo = LocalTime.of(1, 0);
        consulta.setTempo(tempo);
        assertEquals(tempo, consulta.getTempo());
    }

    @Test
    @DisplayName("Deve retornar e definir o paciente da consulta")
    void testGetSetPaciente() {
        Consulta consulta = new Consulta();
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Teste");
        paciente.setDataNascimento(LocalDate.now());
        consulta.setPaciente(paciente);
        assertEquals(paciente, consulta.getPaciente());
    }

    @Test
    @DisplayName("Deve retornar o hashCode correto")
    void testHashCode() {
        LocalDate data = LocalDate.of(2024, 12, 31);
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Teste");
        paciente.setDataNascimento(LocalDate.now());

        Consulta consulta1 = new Consulta(data, horaInicial, horaFinal, LocalTime.now(), paciente);
        Consulta consulta2 = new Consulta(data, horaInicial, horaFinal, LocalTime.now(), paciente);

        assertEquals(consulta1.hashCode(), consulta2.hashCode());
    }

    @Test
    @DisplayName("Deve retornar true para equals quando os objetos são iguais")
    void testEqualsTrue() {
        LocalDate data = LocalDate.of(2024, 12, 31);
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Teste");
        paciente.setDataNascimento(LocalDate.now());

        Consulta consulta1 = new Consulta(data, horaInicial, horaFinal, LocalTime.now(), paciente);
        Consulta consulta2 = new Consulta(data, horaInicial, horaFinal, LocalTime.now(), paciente);

        assertTrue(consulta1.equals(consulta2));
    }

    @Test
    @DisplayName("Deve retornar false para equals quando os objetos são diferentes")
    void testEqualsFalse() {
        LocalDate data = LocalDate.of(2024, 12, 31);
        LocalTime horaInicial = LocalTime.of(8, 0);
        LocalTime horaFinal = LocalTime.of(9, 0);
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        paciente1.setNome("Teste1");
        paciente1.setDataNascimento(LocalDate.now());
        Paciente paciente2 = new Paciente();
        paciente2.setId(2L);
        paciente2.setNome("Teste2");
        paciente2.setDataNascimento(LocalDate.now());


        Consulta consulta1 = new Consulta(data, horaInicial, horaFinal, LocalTime.now(), paciente1);
        Consulta consulta2 = new Consulta(data, horaInicial, horaFinal, LocalTime.now(), paciente2);

        assertFalse(consulta1.equals(consulta2));
    }

}
