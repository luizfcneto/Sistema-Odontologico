package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;

public class CadastrarPacienteDTOTest {

private CadastrarPacienteDTO pacienteDTO;
    
    @BeforeEach
    void setUp() {
        pacienteDTO = new CadastrarPacienteDTO();
    }

    @Test
    @DisplayName("Testar construção com parâmetros diretos")
    public void testConstrutorComParametros() {
        String cpf = "98765432100";
        String nome = "Outro Nome";
        String dataNascimento = "20/02/2000";
        
        CadastrarPacienteDTO dto = new CadastrarPacienteDTO(cpf, nome, dataNascimento);
        
        assertEquals(cpf, dto.getCpf());
        assertEquals(nome, dto.getNome());
        assertEquals(dataNascimento, dto.getDataNascimento());
    }

    @Test
    @DisplayName("Testar setters")
    public void testSetPacienteComSucesso() {
        String cpf = "98765432100";
        String nome = "Outro Nome";
        String dataNascimento = "20/02/2000";
        
        pacienteDTO.setCpf(cpf);
        pacienteDTO.setNome(nome);
        pacienteDTO.setDataNascimento(dataNascimento);
        
        assertEquals(cpf, pacienteDTO.getCpf());
        assertEquals(nome, pacienteDTO.getNome());
        assertEquals(dataNascimento, pacienteDTO.getDataNascimento());
    }
	
}
