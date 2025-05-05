
package br.edu.cs.poo.ac.seguro.mediators;

import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;

public class ValidadorCpfCnpj {

	public static boolean ehCnpjValido(String cnpj) {
		
		if (ehNuloOuBranco(cnpj)){
			return false;
		}
		
		if (temSomenteNumeros(cnpj)){
			return false;
		}

		return true; 
	}

	public static boolean ehCpfValido(String cpf) {

		if (ehNuloOuBranco(cpf)){
			return false;
		}

		if (cpf.length() != 11){
			return false;
		}

		if (!temSomenteNumeros(cpf)){
			return false;
		}

		return true; 
	}
	
}