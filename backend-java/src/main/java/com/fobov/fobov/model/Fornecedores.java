package com.fobov.fobov.model;

public class Fornecedores{
    private int id_fornecedores;
    private String nome;
    private String cep;
    private String endereco;
    private String complemento;
    private String telefone;
    private String status;
    

    //  ID
    public int getId(){
        return id_fornecedores;
    }

    public void setId(int id_fornecedores){
        this.id_fornecedores = id_fornecedores;
    }

    //  NOME
    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    //  CEP
    public String getCep(){
        return cep;
    }

    public void setCep(String cep){
        this.cep = cep;
    }

    //  ENDEREÇO
    public String getEndereco(){
        return endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    //  COMPLEMENTO
    public String getComplemento(){
        return complemento;
    }

    public void setComplemento(String complemento){
        this.complemento = complemento;
    }

    //  TELEFONE
    public String getTelefone(){
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    // STATUS
    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    // CHAVE ESTRANGEIRA ID_CIDADES
    public int getIdCidades(){
        return id_cidades;
    }
    public void setIdCidades(int id_cidades){
        this.id_cidades = id_cidades;
    }
}