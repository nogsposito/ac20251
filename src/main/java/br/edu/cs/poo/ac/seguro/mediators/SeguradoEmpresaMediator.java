package br.edu.cs.poo.ac.seguro.mediators;

import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.ehNuloOuBranco;
import static br.edu.cs.poo.ac.seguro.mediators.StringUtils.temSomenteNumeros;
import static br.edu.cs.poo.ac.seguro.mediators.ValidadorCpfCnpj.ehCnpjValido;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {

    private static SeguradoEmpresaDAO seguradoEmpresaDAO = new SeguradoEmpresaDAO();
	private static SeguradoEmpresaMediator instancia = new SeguradoEmpresaMediator();
	private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();

	SeguradoEmpresaMediator(){}

	public String validarCnpj(String cnpj) {
		return null;
	}

	public String validarFaturamento(double faturamento) {
		return null;
	}

	public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
		return null;
	}

	public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
		return null;
	}

	public String excluirSeguradoEmpresa(String cnpj) {
		return null;
	}

	public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
		return null;
	}

	public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
		return null;
	}

	public static SeguradoEmpresaMediator getInstancia(){
        return instancia;
    }
	

}