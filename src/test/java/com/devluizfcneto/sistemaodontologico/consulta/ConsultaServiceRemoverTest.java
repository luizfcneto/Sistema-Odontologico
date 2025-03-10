package com.devluizfcneto.sistemaodontologico.consulta;

import com.devluizfcneto.sistemaodontologico.repositories.ConsultaRepository;
import com.devluizfcneto.sistemaodontologico.services.impl.ConsultaServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceRemoverTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private ConsultaServiceImpl consultaService;

    @Test
    @DisplayName("Testando remover consulta quando id existir")
    public void deveRemoverConsultaQuandoIdExistir() {
        Long id = 1L;

        consultaService.remover(id);
        verify(consultaRepository, times(1)).deleteById(id);
    }
}