package br.edu.cs.poo.ac.seguro.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Apolice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String numero;

    private Veiculo veiculo;
    private BigDecimal valorFranquia;
    private BigDecimal valorPremio;
    private BigDecimal valorMaximoSegurado;

	public Apolice(Veiculo veiculo, BigDecimal valorFranquia, BigDecimal valorPremio, BigDecimal valorMaximoSegurado) {
		this.veiculo = veiculo;
		this.valorFranquia = valorFranquia;
		this.valorPremio = valorPremio;
		this.valorMaximoSegurado = valorMaximoSegurado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apolice other = (Apolice) obj;
		return Objects.equals(numero, other.numero);
	}
    
}
