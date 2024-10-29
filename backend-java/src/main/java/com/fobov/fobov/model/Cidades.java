package com.fobov.fobov.model;

public class Cidades {
    private int id_cidades;
    private String nome;
    private int id_estados;

    // ID
    public int getId() {
        return id_cidades;
    }

    public void setId(int id_cidades) {
        this.id_cidades = id_cidades;
    }

    // NOME
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // CHAVE ESTRANGEIRA id_estados
    public int getIdCidades(){
        return id_estados;
    }
    public void setIdCidades(int id_estados){
        this.id_estados = id_estados;
    }

}