package com.fobov.fobov.model;

public class Fornecedores{
    private int id_fornecedores;
    private String nome;
    
    public int getId(){
        return id_fornecedores;
    }

    public void setId(int id_fornecedores){
        this.id_fornecedores = id_fornecedores;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}