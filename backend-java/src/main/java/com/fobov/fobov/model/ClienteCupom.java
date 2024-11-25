package com.fobov.fobov.model;

import java.time.LocalDateTime;

public class ClienteCupom {
    private int id;
    private LocalDateTime dataUtilizacao;
    private int idCliente;
    private int idCupom;
    private Cupom cupom;
    private Cliente cliente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataUtilizacao() {
        return dataUtilizacao;
    }

    public void setDataUtilizacao(LocalDateTime dataUtilizacao) {
        this.dataUtilizacao = dataUtilizacao;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCupom() {
        return idCupom;
    }

    public void setIdCupom(int idCupom) {
        this.idCupom = idCupom;
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}