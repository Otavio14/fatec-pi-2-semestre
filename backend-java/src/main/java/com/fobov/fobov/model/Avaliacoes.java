package com.fobov.fobov.model;

import java.util.Date;

public class Avaliacoes {
    private int id_avaliacoes;
    private int nota;
    private String comentario;
    private Date dt_avaliacao;

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

    // DATA DA AVALLIAÇÃO
    public Date getDtAvaliacao() {
        return dt_avaliacao;
    }

    public void setDtAvaliacao(Date dt_avaliacao) {
        this.dt_avaliacao = dt_avaliacao;
    }
}