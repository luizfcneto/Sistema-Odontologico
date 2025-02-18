package com.devluizfcneto.sistemaodontologico;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({
    "com.devluizfcneto.sistemaodontologico.consulta",
    "com.devluizfcneto.sistemaodontologico.paciente",
})
public class AllTests {

}
