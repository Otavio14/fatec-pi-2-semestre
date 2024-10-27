package com.fobov.fobov.model;

public class Estados {
    private int id_estado;
    private String nome;
    private String sigla;

    // ID
    public int getId() {
        return id_estado;
    }

    public void setId(int id_estado) {
        this.id_estado = id_estado;
    }

    // NOME
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // SIGLA
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}