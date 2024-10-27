package com.fobov.fobov.model;

public class Cupons {
    private int id_cupons;
    private String nome;
    private double porcentagem;

    // ID
    public int getId() {
        return id_cupons;
    }

    public void setId(int id_cupons) {
        this.id_cupons = id_cupons;
    }

    // NOME
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // PORCENTAGEM
    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }
}