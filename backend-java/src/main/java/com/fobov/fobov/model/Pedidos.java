package com.fobov.fobov.model;

import java.util.Date;

public class Pedidos {
    private int id_pedidos;
    private Date dt_pedido;
    private String endereco;
    private String status;
    private double total;

    // ID
    public int getId() {
        return id_pedidos;
    }

    public void setId(int id_pedidos) {
        this.id_pedidos = id_pedidos;
    }

    // DATA DO PEDIDO
    public Date getDtPedido() {
        return dt_pedido;
    }

    public void setDtPedido(Date dt_pedido) {
        this.dt_pedido = dt_pedido;
    }

    // ENDEREÇO
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    // STATUS
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // TOTAL
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}