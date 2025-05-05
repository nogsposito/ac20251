package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;

public class TesteApoliceDAO extends TesteDAO {
	
	private ApoliceDAO dao = new ApoliceDAO();
	@Override
	protected Class getClasse() {
		return Apolice.class;
	}
	 
	@Test
	public void teste01() {
		String numero = "00000000";
		cadastro.incluir(new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO), numero);
		Apolice Apolice = dao.buscar(numero);
		Assertions.assertNotNull(Apolice); 
	}

	@Test
	public void teste02() {
		String numero = "10000000";
		cadastro.incluir(new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO), numero);
		Apolice Apolice = dao.buscar(numero);
		Assertions.assertNotNull(Apolice); 
	}
	
	@Test
	public void teste03() {
		String numero = "20000000";
		cadastro.incluir(new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO), numero);
		boolean ret = dao.excluir(numero);
		Assertions.assertTrue(ret);
	}

	@Test
	public void teste04() {
		String numero = "30000000";
		cadastro.incluir(new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO), numero);
		boolean ret = dao.excluir("31000000");
		Assertions.assertFalse(ret);
	}

	@Test
	public void teste05() {
		String numero = "40000000";	
		Apolice apo1 = new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO);
		apo1.setNumero(numero);
		boolean ret = dao.incluir(apo1);		
		Assertions.assertTrue(ret);
		Apolice apo2 = dao.buscar(numero);
		Assertions.assertNotNull(apo2);		
	}
	
	@Test
    public void teste06() {
        String numero = "50000000";		
		Apolice apo1 = new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO);
		apo1.setNumero(numero);
		cadastro.incluir(apo1, numero);
		boolean ret = dao.incluir(apo1);
		Assertions.assertFalse(ret);
    }

	@Test
	public void teste07() {
		String numero = "60000000";			
		boolean ret = dao.alterar(new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO));
		Assertions.assertFalse(ret);
		Apolice apo  = dao.buscar(numero);
		Assertions.assertNull(apo);		
	}
	
	@Test
	public void teste08() {
		String numero = "70000000";			
		Apolice apo = new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO);
		apo.setNumero(numero);
		cadastro.incluir(apo, numero);
		apo = new Apolice(null, new BigDecimal("5.00"), BigDecimal.ZERO, BigDecimal.ZERO);
		apo.setNumero(numero);
		boolean ret = dao.alterar(apo);
		Assertions.assertTrue(ret);
	}

}
