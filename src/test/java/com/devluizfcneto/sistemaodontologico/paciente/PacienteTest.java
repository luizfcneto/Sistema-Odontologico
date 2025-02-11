package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;

public class PacienteTest {

	@Test
	@DisplayName("Testando criação de paciente com construtor passando alguns parametros")
    public void testPacienteCreationWithConstructor() {
        LocalDate dataNascimento = LocalDate.of(1990, 5, 10);
        Paciente paciente = new Paciente("12345678900", "Nome do Paciente", dataNascimento);

        assertEquals("12345678900", paciente.getCpf());
        assertEquals("Nome do Paciente", paciente.getNome());
        assertEquals(dataNascimento, paciente.getDataNascimento());
    }

    @Test
    @DisplayName("Testando criação de paciente com construtor passando todos os parametros")
    public void testPacienteCreationWithAllArgsConstructor() {
        LocalDate dataNascimento = LocalDate.of(2000, 1, 1);
        Paciente paciente = new Paciente(1L, "98765432100", "Outro Nome", dataNascimento);

        assertEquals(1L, paciente.getId());
        assertEquals("98765432100", paciente.getCpf());
        assertEquals("Outro Nome", paciente.getNome());
        assertEquals(dataNascimento, paciente.getDataNascimento());
    }


    @Test
    @DisplayName("Testando paciente equals e hashcode")
    public void testPacienteEqualsAndHashCode() {
        LocalDate dataNascimento1 = LocalDate.of(1990, 5, 10);
        Paciente paciente1 = new Paciente("12345678900", "Nome do Paciente", dataNascimento1);

        LocalDate dataNascimento2 = LocalDate.of(2000, 1, 1); 
        Paciente paciente2 = new Paciente("12345678900", "Nome Diferente", dataNascimento2);

        assertEquals(paciente1, paciente2);
        assertEquals(paciente1.hashCode(), paciente2.hashCode());

        Paciente paciente3 = new Paciente("99999999999", "Nome Diferente", dataNascimento2);
        assertNotEquals(paciente1, paciente3);
        assertNotEquals(paciente1.hashCode(), paciente3.hashCode());
    }

    @Test
    @DisplayName("Testando paciente getters e setters")
    public void testPacienteSettersAndGetters() {
        LocalDate dataNascimento = LocalDate.now();
        Paciente paciente = new Paciente();

        paciente.setCpf("11122233344");
        paciente.setNome("Nome Teste");
        paciente.setDataNascimento(dataNascimento);
        paciente.setId(1L);

        assertEquals("11122233344", paciente.getCpf());
        assertEquals("Nome Teste", paciente.getNome());
        assertEquals(dataNascimento, paciente.getDataNascimento());
        assertEquals(1L, paciente.getId());
    }
	
}
