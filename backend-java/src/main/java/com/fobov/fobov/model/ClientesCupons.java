package com.fobov.fobov.model;

import java.util.Date;

public class ClientesCupons {
    private int id;
    private Date data_utilizacao;

    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // DATA DA UTILIZAÇAO
    public Date getDataUtilizacao() {
        return data_utilizacao;
    }

    public void setDataUtilizacao(Date data_utilizacao) {
        this.data_utilizacao = data_utilizacao;
    }
}