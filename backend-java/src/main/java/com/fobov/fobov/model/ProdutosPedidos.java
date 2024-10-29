package com.fobov.fobov.model;

public class ProdutosPedidos {
    private int id;
    private double preco;
    private int quantidade;
    private int id_produtos;
    private int id_pedidos;

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // PREÇO
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

    // CHAVE ESTRANGEIRA id_produtos
    public int getIdCidades(){
        return id_produtos;
    }
    public void setIdCidades(int id_produtos){
        this.id_produtos = id_produtos;
    }

    // CHAVE ESTRANGEIRA id_pedidos
    public int getIdCidades(){
        return id_pedidos;
    }
    public void setIdCidades(int id_pedidos){
        this.id_pedidos = id_pedidos;
    }
}