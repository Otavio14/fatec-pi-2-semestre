package com.fobov.fobov.model;

import java.util.Date;

public class ClientesCupons {
    private int id;
    private Date data_utilizacao;
    private int id_clientes;
    private int id_cupons;

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

    // CHAVE ESTRANGEIRA id_clientes
    public int getIdCidades(){
        return id_clientes;
    }
    public void setIdCidades(int id_clientes){
        this.id_clientes = id_clientes;
    }

    // CHAVE ESTRANGEIRA id_cupons
    public int getIdCidades(){
        return id_cupons;
    }
    public void setIdCidades(int id_cupons){
        this.id_cupons = id_cupons;
    }

    
}