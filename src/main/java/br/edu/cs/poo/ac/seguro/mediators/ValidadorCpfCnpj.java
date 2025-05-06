
package br.edu.cs.poo.ac.seguro.mediators;

import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;

public class ValidadorCpfCnpj {

	public static boolean ehCpfValido(String cpf) {
		if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
			return false;
		}
	
		try {
			int soma1 = 0, soma2 = 0;
			for (int i = 0; i < 9; i++) {
				int digito = Character.getNumericValue(cpf.charAt(i));
				soma1 += digito * (10 - i);
				soma2 += digito * (11 - i);
			}
	
			int digito1 = (soma1 * 10) % 11;
			if (digito1 == 10) digito1 = 0;
			soma2 += digito1 * 2;
	
			int digito2 = (soma2 * 10) % 11;
			if (digito2 == 10) digito2 = 0;
	
			return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
				   digito2 == Character.getNumericValue(cpf.charAt(10));
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean ehCnpjValido(String cnpj) {
		if (cnpj == null || cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
			return false;
		}
	
		try {
			int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
			int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	
			int soma = 0;
			for (int i = 0; i < 12; i++) {
				soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
			}
	
			int digito1 = soma % 11;
			digito1 = (digito1 < 2) ? 0 : 11 - digito1;
	
			soma = 0;
			for (int i = 0; i < 13; i++) {
				soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
			}
	
			int digito2 = soma % 11;
			digito2 = (digito2 < 2) ? 0 : 11 - digito2;
	
			return digito1 == Character.getNumericValue(cnpj.charAt(12)) &&
				   digito2 == Character.getNumericValue(cnpj.charAt(13));
		} catch (Exception e) {
			return false;
		}
	}
	

	
}