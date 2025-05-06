package br.edu.cs.poo.ac.seguro.mediators;

import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;
import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCnpjValido;

import java.time.LocalDate;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO; 
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
//cadastro
public class SeguradoEmpresaMediator {

    private static SeguradoEmpresaDAO dao = new SeguradoEmpresaDAO();
	private static SeguradoEmpresaMediator instancia = new SeguradoEmpresaMediator();
	private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();

	SeguradoEmpresaMediator(){}

	public boolean calcularCnpjValido(String cnpj) {

		if (cnpj == null || cnpj.length() != 14) {
			return false;
		}
	
		if (cnpj.matches("(\\d)\\1{13}")) {
			return false; // todos os dígitos iguais
		}
	
		int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
		int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	
		int soma = 0;
		for (int i = 0; i < 12; i++) {
			soma += (cnpj.charAt(i) - '0') * pesos1[i];
		}
	
		int resto = soma % 11;
		int digito1 = (resto < 2) ? 0 : 11 - resto;
		if (digito1 != (cnpj.charAt(12) - '0')) {
			return false;
		}
	
		soma = 0;
		for (int i = 0; i < 13; i++) {
			soma += (cnpj.charAt(i) - '0') * pesos2[i];
		}
	
		resto = soma % 11;
		int digito2 = (resto < 2) ? 0 : 11 - resto;
		return digito2 == (cnpj.charAt(13) - '0');
	}
	
	public String validarDataAbertura(LocalDate dataAbertura) {
        if (dataAbertura == null) {
            return "Data da abertura deve ser informada";
        }
        return null;
    }

	public String validarCnpj(String cnpj) {
		if (cnpj == null || cnpj.isBlank()){
			return "CNPJ deve ser informado";
		}
		if (cnpj.length() != 14){
			return "CNPJ deve ter 14 caracteres";
		}
		if (!temSomenteNumeros(cnpj) || !calcularCnpjValido(cnpj)){
			return "CNPJ com dígito inválido";
		}

		return null;
	}

	public String validarFaturamento(double faturamento) {
		if (faturamento <= 0){
			return "Faturamento deve ser maior que zero";
		}
		return null;
	}

	public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {

		if (dao.buscar(seg.getCnpj()) != null){
			return "CNPJ do segurado empresa já existente";
		}

		if (seg.getNome() == null){
			return "Nome deve ser informado";
		}
		if (seg.getEndereco() == null){
			return "Endereço deve ser informado";
		}
		if (seg.getDataAbetura() == null){
			return "Data da abertura deve ser informada";
		}
		if (validarCnpj(seg.getCnpj()) != null){
			return "CNPJ com dígito inválido";
		}
		if (validarFaturamento(seg.getFaturamento()) != null){
			return "Faturamento deve ser maior que zero";
		}

		boolean ret = dao.incluir(seg);
		
		if (!ret){
			return "Erro ao incluir segurado.";
		}
		
		return null;
	}

	public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
		String msg = validarSeguradoEmpresa(seg);
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		if (dao.buscar(seg.getCnpj()) == null){
			return "CNPJ do segurado empresa não existente";
		}		

		boolean ret = dao.alterar(seg); // erro ao alterar

		if (!ret){
			return "Erro ao alterar";
		}

		return null;
	}

	public String excluirSeguradoEmpresa(String cnpj) {

		if (dao.buscar(cnpj) == null){
			return "CNPJ do segurado empresa não existente";
		}
		if (validarCnpj(cnpj) != null){
			return "CNPJ inválido";
		}

		boolean ret = dao.excluir(cnpj);

		if (!ret){
			return "Erro ao excluir";
		}

		return null;
	}

	public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
		return dao.buscar(cnpj);
	}

	public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
		if (seg == null){
			return "Erro, segurado nulo";
		}
		String msg = seguradoMediator.validarNome(seg.getNome());
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		msg = seguradoMediator.validarEndereco(seg.getEndereco());
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		msg = validarDataAbertura(seg.getDataAbetura());
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		msg = validarCnpj(seg.getCnpj());
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		msg = validarFaturamento(seg.getFaturamento());
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		return null;
	}

	public static SeguradoEmpresaMediator getInstancia(){
        return instancia;
    }
	

}