package br.edu.cs.poo.ac.seguro.entidades;

import java.io.Serializable;
import java.math.BigDecimal;


import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class Sinistro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String numero;
    
    private Veiculo veiculo;
    private LocalDateTime dataHoraSinistro;
    private LocalDateTime dataHoraRegistro;
    private String usuarioRegistro;
    private BigDecimal valorSinistro;
    private TipoSinistro tipo;
    
	public Sinistro(Veiculo veiculo, LocalDateTime dataHoraSinistro, LocalDateTime dataHoraRegistro, String usuarioRegistro, BigDecimal valorSinistro, TipoSinistro tipo) {
		this.veiculo = veiculo;
		this.dataHoraSinistro = dataHoraSinistro;
		this.dataHoraRegistro = dataHoraRegistro;
		this.usuarioRegistro = usuarioRegistro;
		this.valorSinistro = valorSinistro;
		this.tipo = tipo;
	}

	public Sinistro(String numero, Veiculo veiculo, LocalDateTime dataHoraSinistro, LocalDateTime dataHoraRegistro, String usuarioRegistro, BigDecimal valorSinistro, TipoSinistro tipo) {
		this.numero = numero;
		this.veiculo = veiculo;
		this.dataHoraSinistro = dataHoraSinistro;
		this.dataHoraRegistro = dataHoraRegistro;
		this.usuarioRegistro = usuarioRegistro;
		this.valorSinistro = valorSinistro;
		this.tipo = tipo;
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
		Sinistro other = (Sinistro) obj;
		return Objects.equals(numero, other.numero);
	}
    
}
