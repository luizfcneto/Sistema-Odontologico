package com.devluizfcneto.sistemaodontologico.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devluizfcneto.sistemaodontologico.dtos.CadastrarConsultaDTO;
import com.devluizfcneto.sistemaodontologico.dtos.ConsultaResponseDTO;
import com.devluizfcneto.sistemaodontologico.entities.Consulta;


@Service
public interface ConsultaService {
	public ConsultaResponseDTO cadastrar(CadastrarConsultaDTO consulta);
	public Boolean atualizaConsulta();
	public void remover(Long id);
	public List<ConsultaResponseDTO> listarConsultas(String dataInicial, String dataFinal, String direction);
}
