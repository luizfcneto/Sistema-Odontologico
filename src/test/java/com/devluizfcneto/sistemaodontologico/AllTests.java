package com.devluizfcneto.sistemaodontologico;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import com.devluizfcneto.sistemaodontologico.paciente.CadastrarPacienteDTOTest;
import com.devluizfcneto.sistemaodontologico.paciente.ControllerCadastrarPacienteTest;
import com.devluizfcneto.sistemaodontologico.paciente.PacienteServiceCadastrarTest;
import com.devluizfcneto.sistemaodontologico.paciente.PacienteTest;
import com.devluizfcneto.sistemaodontologico.paciente.ValidarCadastroPacienteTest;

@Suite
@SelectPackages("com.devluizfcneto")
@SelectClasses({	
	CadastrarPacienteDTOTest.class,
	PacienteTest.class,
	PacienteServiceCadastrarTest.class,
	ValidarCadastroPacienteTest.class,
	ControllerCadastrarPacienteTest.class
})
public class AllTests {

}
