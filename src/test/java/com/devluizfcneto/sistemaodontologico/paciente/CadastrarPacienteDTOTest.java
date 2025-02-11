package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarPacienteDTO;
import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.BadRequestException;

public class CadastrarPacienteDTOTest {

	private CadastrarPacienteDTO pacienteDTO;
	
	@BeforeEach
	void setUp() {
		pacienteDTO = new CadastrarPacienteDTO();
	}
	
	@Test
    public void testCalculaIdadeComDataNascimentoValida() {
        String dataNascimento = "10/05/1990";
        this.pacienteDTO = new CadastrarPacienteDTO();
        this.pacienteDTO.setDataNascimento(dataNascimento);
        this.pacienteDTO.calculaIdade();

        int idadeEsperada = LocalDate.now().getYear() - 1990;
        assertEquals(idadeEsperada, this.pacienteDTO.getIdade());
    }

    @Test
    @DisplayName("Testando calcular idade do paciente com data de nascimento nulo")
    public void testCalculaIdadeComDataNascimentoNula() {
        this.pacienteDTO = new CadastrarPacienteDTO();
//        assertThrows(BadRequestException.class, this.pacienteDTO::calculaIdade);
        assertThrows(BadRequestException.class, () -> this.pacienteDTO.calculaIdade());
    }

    @Test
    @DisplayName("Testando construcao do paciente com construtor da Entidade Paciente")
    public void testConstrutorComPaciente() {
        LocalDate dataNascimento = LocalDate.of(1990, 5, 10);
        Paciente paciente = new Paciente("12345678900", "Nome do Paciente", dataNascimento);
        this.pacienteDTO = new CadastrarPacienteDTO(paciente);

        assertEquals("12345678900", this.pacienteDTO.getCpf());
        assertEquals("Nome do Paciente", this.pacienteDTO.getNome());
        assertEquals("10/5/1990", this.pacienteDTO.getDataNascimento());

        int idadeEsperada = LocalDate.now().getYear() - 1990;
        assertEquals(idadeEsperada, this.pacienteDTO.getIdade());
    }

    @Test
    @DisplayName("Testando construcao do paciente com construtor passando variaveis")
    public void testConstrutorComCpfNomeDataNascimento() {
        String cpf = "98765432100";
        String nome = "Outro Nome";
        String dataNascimento = "20/02/2000";

        this.pacienteDTO = new CadastrarPacienteDTO(cpf, nome, dataNascimento);

        assertEquals(cpf, this.pacienteDTO.getCpf());
        assertEquals(nome, this.pacienteDTO.getNome());
        assertEquals(dataNascimento, this.pacienteDTO.getDataNascimento());

        int idadeEsperada = LocalDate.now().getYear() - 2000;
        assertEquals(idadeEsperada, this.pacienteDTO.getIdade());
    }
    
    @Test
    @DisplayName("Testando construcao do paciente setando variaveis")
    public void testSetPacienteComSucesso() {
        String cpf = "98765432100";
        String nome = "Outro Nome";
        String dataNascimento = "20/02/2000";

        this.pacienteDTO = new CadastrarPacienteDTO();
        this.pacienteDTO.setCpf(cpf);
        this.pacienteDTO.setNome(nome);
        this.pacienteDTO.setDataNascimento(dataNascimento);
        this.pacienteDTO.calculaIdade();
        
        assertEquals(cpf, this.pacienteDTO.getCpf());
        assertEquals(nome, this.pacienteDTO.getNome());
        assertEquals(dataNascimento, this.pacienteDTO.getDataNascimento());
        
        int idadeEsperada = LocalDate.now().getYear() - 2000;
        assertEquals(idadeEsperada, this.pacienteDTO.getIdade());
    }

    @Test
    @DisplayName("Testando calcular idade com data de nascimento com formato invalido")
    public void testCalculaIdadeComDataNascimentoFormatoInvalido() {
        String dataNascimento = "10-05-1990";
        this.pacienteDTO = new CadastrarPacienteDTO();
        this.pacienteDTO.setDataNascimento(dataNascimento);

//        assertThrows(BadRequestException.class, this.pacienteDTO::calculaIdade);
        assertThrows(BadRequestException.class, () -> this.pacienteDTO.calculaIdade() );
    }

    @Test
    @DisplayName("Testando calcular idade com data de nascimento com dia invalido")
    public void testCalculaIdadeComDataNascimentoDiaInvalido() {
        String dataNascimento = "32/05/1990"; 
        this.pacienteDTO = new CadastrarPacienteDTO();
        this.pacienteDTO.setDataNascimento(dataNascimento);

//        assertThrows(BadRequestException.class, this.pacienteDTO::calculaIdade);
        assertThrows(BadRequestException.class, () -> this.pacienteDTO.calculaIdade() );
    }

    @Test
    @DisplayName("Testando calcular idade com data de nascimento com mês invalido")
    public void testCalculaIdadeComDataNascimentoMesInvalido() {
        String dataNascimento = "10/13/1990";
        this.pacienteDTO = new CadastrarPacienteDTO();
        this.pacienteDTO.setDataNascimento(dataNascimento);

//        assertThrows(BadRequestException.class, this.pacienteDTO::calculaIdadeas);
        assertThrows(BadRequestException.class, () -> this.pacienteDTO.calculaIdade() );
        
    }
	
}
