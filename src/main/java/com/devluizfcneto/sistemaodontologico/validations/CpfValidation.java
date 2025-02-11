package com.devluizfcneto.sistemaodontologico.validations;

public class CpfValidation {

	public static boolean isValid(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        int firstDigit = calculateVerifierDigit(cpf, 10);
        int secondDigit = calculateVerifierDigit(cpf, 11);

        return cpf.charAt(9) - '0' == firstDigit && cpf.charAt(10) - '0' == secondDigit;
    }

    private static int calculateVerifierDigit(String cpf, int weight) {
        int sum = 0;
        for (int i = 0; i < weight - 1; i++) {
            sum += (cpf.charAt(i) - '0') * (weight - i);
        }
        int remainder = sum % 11;
        int digit = 11 - remainder;
        return digit > 9 ? 0 : digit;
    }
	
}
