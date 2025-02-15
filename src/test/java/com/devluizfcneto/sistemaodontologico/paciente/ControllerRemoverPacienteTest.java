package com.devluizfcneto.sistemaodontologico.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devluizfcneto.sistemaodontologico.controllers.PacienteController;
import com.devluizfcneto.sistemaodontologico.dtos.ErrorDTO;
import com.devluizfcneto.sistemaodontologico.errors.PacienteNotFoundException;
import com.devluizfcneto.sistemaodontologico.services.PacienteService;

public class ControllerRemoverPacienteTest {
	
	@Mock
	private PacienteService pacienteService;
	
	@InjectMocks
	private PacienteController pacienteController;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


	@Test
    @DisplayName("Testando remover paciente SUCESSO")
	public void deveRemoverPacienteComSucesso() {
	    Long id = 1L;
	    
	    doNothing().when(pacienteService).remover(id);
	    ResponseEntity<?> response = pacienteController.removerPaciente(id);
	
	    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	    verify(pacienteService, times(1)).remover(id);
	}
	
	@Test
    @DisplayName("Testando remover paciente ERRO NAO ENCONTRADO")
	public void deveRetornarNotFoundQuandoPacienteNaoEncontrado() {
	    Long id = 1L;
	     
	    doThrow(PacienteNotFoundException.class).when(pacienteService).remover(id);
	    PacienteNotFoundException pNFE = assertThrows(PacienteNotFoundException.class, () -> pacienteController.removerPaciente(id));
	    assertEquals(404, pNFE.getCode());
	}
	
	@Test
    @DisplayName("Testando remover paciente ERRO ERRO INESPERADO")
	public void deveRetornarInternalServerErrorQuandoOcorrerErroInesperado() {
	    Long id = 1L;
	    String mensagemErro = "Erro inesperado";
	    doThrow(new RuntimeException(mensagemErro)).when(pacienteService).remover(id);
	   
	    ResponseEntity<?> response = pacienteController.removerPaciente(id);
	
	    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	    ErrorDTO errorDTO = (ErrorDTO) response.getBody();
	    assert errorDTO != null;
	    assertEquals(mensagemErro, errorDTO.getMessage());
	}
}
