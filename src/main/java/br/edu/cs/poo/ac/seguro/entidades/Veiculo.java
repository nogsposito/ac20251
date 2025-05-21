package br.edu.cs.poo.ac.seguro.entidades;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Veiculo implements Registro {

	private static final long serialVersionUID = 1L;
    
    private String placa;
    private int ano;
	private Segurado proprietario;
    private CategoriaVeiculo categoria;

	@Override
	public int hashCode() {
		return Objects.hash(placa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		return Objects.equals(placa, other.placa);
	}

	@Override
    public String getIdUnico() {
        return placa;
    }

}

