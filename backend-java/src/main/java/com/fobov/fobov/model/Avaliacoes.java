package com.fobov.fobov.model;

import java.util.Date;

public class Avaliacoes {
    private int id_avaliacoes;
    private int nota;
    private String comentario;
    private Date dt_avaliacao;
    private int id_clientes;
    private int id_produtos;

    // ID
    public int getId() {
        return id_avaliacoes;
    }

    public void setId(int id_avaliacoes) {
        this.id_avaliacoes = id_avaliacoes;
    }

    // NOTA PARA O PRODUTO AVALIADO
    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    // COMENTÁRIO
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    // DATA DA AVALIAÇÃO
    public Date getDtAvaliacao() {
        return dt_avaliacao;
    }

    public void setDtAvaliacao(Date dt_avaliacao) {
        this.dt_avaliacao = dt_avaliacao;
    }

    // CHAVE ESTRANGEIRA ID_CLIENTES
    public int getIdClientes(){
        return id_clientes;
    }
    public void setIdClientes(int id_clientes){
        this.id_clientes = id_clientes;
    }

    // CHAVE ESTRANGEIRA id_produtos
    public int getIdCidades(){
        return id_produtos;
    }
    public void setIdCidades(int id_produtos){
        this.id_produtos = id_produtos;
    }
}
