package com.fobov.fobov.model;

import java.time.LocalDateTime;

public class Avaliacao {
    private int id;
    private int nota;
    private String comentario;
    private LocalDateTime dtAvaliacao;
    private int idCliente;
    private int idProduto;
    private Produto produto;
    private Cliente cliente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDtAvaliacao() {
        return dtAvaliacao;
    }

    public void setDtAvaliacao(LocalDateTime dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
