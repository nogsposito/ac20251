package br.edu.cs.poo.ac.seguro.testes;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;

public class TesteSinistroDAO extends TesteDAO {
	
	private SinistroDAO dao = new SinistroDAO();
	@Override
	protected Class getClasse() {
		return Sinistro.class;
	}
	 
	@Test
	public void teste01() {
		String numero = "00000000";
		cadastro.incluir(new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO), numero);
		Sinistro sinistro = dao.buscar(numero);
		Assertions.assertNotNull(sinistro); 
	}

	@Test
	public void teste02() {
		String numero = "10000000";
		cadastro.incluir(new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO), numero);
		Sinistro sinistro = dao.buscar(numero);
		Assertions.assertNotNull(sinistro); 
	}
	
	@Test
	public void teste03() {
		String numero = "20000000";
		cadastro.incluir(new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO), numero);
		boolean ret = dao.excluir(numero);
		Assertions.assertTrue(ret);
	}

	@Test
	public void teste04() {
		String numero = "30000000";
		cadastro.incluir(new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO), numero);
		boolean ret = dao.excluir("31000000");
		Assertions.assertFalse(ret);
	}

	@Test
	public void teste05() {
        String numero = "40000000";
		Sinistro sin1 = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO);
		sin1.setNumero(numero);
		boolean ret = dao.incluir(sin1);	
		Assertions.assertTrue(ret);
		Sinistro sin = dao.buscar(numero);
		Assertions.assertNotNull(sin);	
    }
	
	@Test
    public void teste06() {
        String numero = "50000000";		
		Sinistro sinistro = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO);
		sinistro.setNumero(numero);
		cadastro.incluir(sinistro, numero);
		boolean ret = dao.incluir(sinistro);
		Assertions.assertFalse(ret);
    }

	@Test
	public void teste07() {
		String numero = "60000000";			
		boolean ret = dao.alterar(new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO));
		Assertions.assertFalse(ret);
		Sinistro sin  = dao.buscar(numero);
		Assertions.assertNull(sin);		
	}
	
	@Test
	public void teste08() {
		String numero = "70000000";			
		Sinistro sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO);
		sin.setNumero(numero);
		cadastro.incluir(sin, numero);
		sin = new Sinistro(null, LocalDateTime.now(), LocalDateTime.now(), null, null, TipoSinistro.COLISAO);
		sin.setNumero(numero);
		boolean ret = dao.alterar(sin);
		Assertions.assertTrue(ret);
	}

}
