package br.edu.cs.poo.ac.seguro.entidades;

public enum TipoSinistro {

    COLISAO(1,"Colisão"),
    INCENDIO(2,"Incêndio"),
    FURTO(3, "Furto"),
    ENCHENTE(4, "Enchente"),
    DEPREDACAO(5, "Depredação");
    
    private int codigo;
    private String name;

    private TipoSinistro(int codigo, String name){
        this.codigo = codigo;
        this.name = name;
    }

    public int getCodigo(){
        return codigo;
    }

    public String getName(){
        return name;
    }

    public static TipoSinistro getTipoSinistro(int codigo){
        for (TipoSinistro t : TipoSinistro.values()){
            if (t.getCodigo() == codigo){
                return t;
            }
        }
        return null;
    }

}
