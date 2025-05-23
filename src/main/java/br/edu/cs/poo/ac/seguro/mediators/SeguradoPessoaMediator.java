package br.edu.cs.poo.ac.seguro.mediators;

import java.time.LocalDate;

import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;
import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCpfValido;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {

    private static SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    private SeguradoPessoaDAO dao = new SeguradoPessoaDAO();
    private static SeguradoPessoaMediator instancia = new SeguradoPessoaMediator();

    SeguradoPessoaMediator(){}

	public boolean calcularCpfValido(String cpf) {

        if (cpf == null || cpf.length() != 11){
			return false;
		}

        if (cpf.matches("(\\d)\\1{10}")){
			return false;
		}

        int soma = 0;
        for (int i = 0, peso = 10; i < 9; i++, peso--) {
            soma += (cpf.charAt(i) - '0') * peso;
        }

        int resto = soma % 11;
        int digito1 = (resto < 2) ? 0 : 11 - resto;
        if (digito1 != (cpf.charAt(9) - '0')) return false;

        soma = 0;
        for (int i = 0, peso = 11; i < 10; i++, peso--) {
            soma += (cpf.charAt(i) - '0') * peso;
        }

        resto = soma % 11;
        int digito2 = (resto < 2) ? 0 : 11 - resto;

		if (digito2 == (cpf.charAt(10) - '0')){
			return true;
		}

        return false;
		
    }

	public String validarDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return "Data do nascimento deve ser informada";
        }
        return null;
    }

	public String validarRenda(double renda) {
		if (renda < 0){
			return "Renda deve ser maior ou igual à zero";
		}
		return null;
	}

	public String validarCpf(String cpf) { 

		if(ehNuloOuBranco(cpf)){
			return "CPF deve ser informado";
		}

		if (cpf.length() != 11){
			return "CPF deve ter 11 caracteres";
		}

		if (!temSomenteNumeros(cpf) || !calcularCpfValido(cpf)){
			return "CPF com dígito inválido";
		}

		if (!ehCpfValido(cpf)){
			return "CPF inválido";
		}

		return null;

	}

    public static SeguradoPessoaMediator getInstancia(){
        return instancia;
    }


	public String incluirSeguradoPessoa(SeguradoPessoa seg) {
		
		if(!ehNuloOuBranco(validarSeguradoPessoa(seg))){
			return validarSeguradoPessoa(seg);
		}

		if (dao.buscar(seg.getCpf()) != null){
			return "CPF do segurado pessoa já existente";
		}

		if (ehNuloOuBranco(seg.getNome())){
			return "Nome deve ser informado";
		}
		if (seg.getEndereco() == null){
			return "Endereço deve ser informado";
		}
		if (seg.getDataNascimento() == null){
			return "Data do nascimento deve ser informada";
		}
		if (validarCpf(seg.getCpf()) != null){
			return "CPF com dígito inválido";
		}
		if (validarRenda(seg.getRenda()) != null){
			return "Renda deve ser maior ou igual à zero";
		}

		boolean ret = dao.incluir(seg);
		if (!ret){
			return "Erro ao incluir segurado.";
		}
		
		return null;

	}

	public String alterarSeguradoPessoa(SeguradoPessoa seg) {
		
		String msg = validarSeguradoPessoa(seg);
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		if (dao.buscar(seg.getCpf()) == null){
			return "CPF do segurado pessoa não existente";
		}		

		boolean ret = dao.alterar(seg); // erro ao alterar

		if (!ret){
			return "Erro ao alterar";
		}

		return null;
	}

	public String excluirSeguradoPessoa(String cpf) {

		if (dao.buscar(cpf) == null){
			return "CPF do segurado pessoa não existente";
		}
		if (validarCpf(cpf) != null){
			return "CPF inválido";
		}

		boolean ret = dao.excluir(cpf);

		if (!ret){
			return "Erro ao excluir";
		}

		return null;

	}

	public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
		if (validarCpf(cpf) != null) { 
			return null;
		}
		return dao.buscar(cpf);
	}
	

	public String validarSeguradoPessoa(SeguradoPessoa seg) {
		
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
		msg = validarDataNascimento(seg.getDataNascimento());
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		msg = validarCpf(seg.getCpf());
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		msg = validarRenda(seg.getRenda());
		if (!ehNuloOuBranco(msg)) {
            return msg;
        }
		return null;
	}

}