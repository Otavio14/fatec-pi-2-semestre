package com.fobov.fobov.model;

import java.util.Date;

 public class Produtos {
    private int id_produtos;
    private String nome;
    private Date dt_validade;
    private double preco;
    private int estoque;
    private String descricao;
    private int id_categoria;

    // ID
    public int getId() {
        return id_produtos;
    }

    public void setId(int id_produtos) {
        this.id_produtos = id_produtos;
    }

    // NOME
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // DATA DE VALIDADE
    public Date getDtValidade() {
        return dt_validade;
    }

    public void setDtValidade(Date dt_validade) {
        this.dt_validade = dt_validade;
    }

    // PREÇO
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // ESTOQUE
    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    // DESCRIÇAO
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // CHAVE ESTRANGEIRA ID_CATEGORIA
    public int getIdCategoria(){
        return id_categoria;
    }

    public void setIdCategoria(){
        this.id_categoria = id_categoria;
    }

}