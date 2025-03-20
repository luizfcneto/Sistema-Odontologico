package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devluizfcneto.sistemaodontologico.entities.Paciente;
import com.devluizfcneto.sistemaodontologico.entities.Consulta;
import com.devluizfcneto.sistemaodontologico.errors.PacienteNotFoundException;
import com.devluizfcneto.sistemaodontologico.repositories.PacienteRepository;
import com.devluizfcneto.sistemaodontologico.repositories.ConsultaRepository;
import com.devluizfcneto.sistemaodontologico.services.impl.PacienteServiceImpl;

// JUnit versão 5
@ExtendWith(MockitoExtension.class)
public class PacienteServiceRemoverTest {

	@Mock
	private PacienteRepository pacienteRepository;

	@Mock
	private ConsultaRepository consultaRepository;
	
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
		List<Consulta> consultas = new ArrayList<>();
		paciente.setConsultas(consultas);

		Optional<Paciente> pacienteExistente = Optional.of(paciente);

		when(pacienteRepository.findByIdWithConsultas(id)).thenReturn(pacienteExistente);
		doNothing().when(consultaRepository).deleteAll(consultas);
		pacienteService.remover(id);

		verify(consultaRepository, times(1)).deleteAll(consultas);
		verify(pacienteRepository, times(1)).delete(paciente);
	}

	@Test
	@DisplayName("Deve lançar exceção quando id não existir")
	public void deveLancarExcecaoQuandoIdNaoExistir() {
		Long id = 1L;
		Optional<Paciente> pacienteExistente = Optional.empty();

		when(pacienteRepository.findByIdWithConsultas(id)).thenReturn(pacienteExistente);

		assertThrows(PacienteNotFoundException.class, () -> pacienteService.remover(id));
	}
	
}
