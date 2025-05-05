package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import br.edu.cs.poo.ac.seguro.entidades.Endereco;

public class SeguradoMediator {

    private static SeguradoMediator instancia = new SeguradoMediator();
    
    private SeguradoMediator(){}

    public static SeguradoMediator getInstancia() {
        return instancia;
    }

	public String validarNome(String nome) {
		if (nome == null || nome.trim().isEmpty()){
			return "Nome não informado";
		}
		if (nome.length() > 100){
			return "Nome maior que 100 caractéres";
		}
		return null;
	}

	public String validarEndereco(Endereco endereco) {
		if (endereco.getLogradouro() == null || endereco.getCep() == null || endereco.getNumero() == null || 
		endereco.getComplemento() == null || endereco.getPais() == null || endereco.getEstado() == null || endereco.getCidade() == null){
			return "Campos nulos";
		}
		return null;
	}

	public String validarDataCriacao(LocalDate dataCriacao) {
		if (dataCriacao == null || dataCriacao.isAfter(LocalDate.now())){
			return "Data de criação inválida";
		}
		return null;
	}

	public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
		if (bonus == null || valorDebito == null){
			return BigDecimal.ZERO;
		}
		return bonus.min(valorDebito);
	}

}