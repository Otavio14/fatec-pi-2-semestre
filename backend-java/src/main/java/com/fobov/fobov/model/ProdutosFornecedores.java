package com.fobov.fobov.model;

import java.util.Date;

public class ProdutosFornecedores {
    private int id;
    private double preco;
    private int quantidade;
    private Date data;
    private int id_produtos;
    private int id_fornecedores;


    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //PREÇO
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // QUANTIDADE
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // DATA
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    // CHAVE ESTRANGEIRA id_produtos
    public int getIdCidades(){
        return id_produtos;
    }
    public void setIdCidades(int id_produtos){
        this.id_produtos = id_produtos;
    }

    // CHAVE ESTRANGEIRA id_fornecedores
    public int getIdCidades(){
        return id_fornecedores;
    }
    public void setIdCidades(int id_fornecedores){
        this.id_fornecedores = id_fornecedores;
    }
}