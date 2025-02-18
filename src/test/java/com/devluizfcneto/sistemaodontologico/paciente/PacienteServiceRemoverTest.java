package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.errors.PacienteNotFoundException;
import com.devluizfcneto.sistemaodontologico.repositories.PacienteRepository;
import com.devluizfcneto.sistemaodontologico.services.impl.PacienteServiceImpl;

// JUnit versão 5
@ExtendWith(MockitoExtension.class)
public class PacienteServiceRemoverTest {

	@Mock
	private PacienteRepository pacienteRepository;
	
	@InjectMocks
	private PacienteServiceImpl pacienteService;
	
//	JUnit versão 4
//	@BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

	
	@Test
	@DisplayName("Testando remover paciente quando id existir")
	public void deveRemoverPacienteQuandoIdExistir() {
	    Long id = 1L;
	    Paciente paciente = new Paciente();
	    paciente.setId(id);
	    Optional<Paciente> pacienteExistente = Optional.of(paciente);
	
	    when(pacienteRepository.findById(id)).thenReturn(pacienteExistente);
	    pacienteService.remover(id);
	
	    verify(pacienteRepository, times(1)).delete(paciente);
	}
	
	@Test
	@DisplayName("Deve lançar exceção quando id não existir")
	public void deveLancarExcecaoQuandoIdNaoExistir() {
	    Long id = 1L;
	    Optional<Paciente> pacienteExistente = Optional.empty();
	
	    when(pacienteRepository.findById(id)).thenReturn(pacienteExistente);
	
	    assertThrows(PacienteNotFoundException.class, () -> pacienteService.remover(id));
	}
	
}
