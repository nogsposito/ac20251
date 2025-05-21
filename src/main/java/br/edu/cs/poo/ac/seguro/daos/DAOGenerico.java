package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Registro;

public abstract class DAOGenerico <T extends Registro> {

    private CadastroObjetos cadastro;

    public DAOGenerico(){
        this.cadastro = new CadastroObjetos(getClasseEntidade());
    }

    public abstract Class<T> getClasseEntidade();

    public boolean incluir (T entidade){
        if (buscar(entidade.getIdUnico()) != null) {
			return false;
		} else {
			cadastro.incluir(entidade, entidade.getIdUnico());
			return true;
		}
    }

    public boolean alterar (T entidade){
        if (buscar(entidade.getIdUnico()) == null) {
			return false;
		} else {
			cadastro.alterar(entidade, entidade.getIdUnico());
			return true;
		}
    }

    public boolean excluir (String idUnico){
        if (buscar(idUnico) == null) {
			return false;
		} else {
			cadastro.excluir(idUnico);
			return true;
		}
    }

    public T buscar (String idUnico){
        return (T) cadastro.buscar(idUnico);
    }

    public T[] buscarTodos(){
        return (T[]) cadastro.buscarTodos();
    }

}