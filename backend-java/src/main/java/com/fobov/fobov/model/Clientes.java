package com.fobov.fobov.model;

public class Clientes {
    private int id_clientes;
    private String nome;
    private String cep;
    private String endereco;
    private String email;
    private String telefone;
    private String bairro;
    private String numero;

    // ID
    public int getId() {
        return id_clientes;
    }

    public void setId(int id_clientes) {
        this.id_clientes = id_clientes;
    }

    // NOME
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // CEP
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    // ENDEREÇO
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    // EMAIL
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // TELEFONE
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // BAIRRO
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    // NÚMERO
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}