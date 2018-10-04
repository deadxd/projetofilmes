package com.example.monster.projetofilmeseries.model;

public class Usuario {

    public String nome;
    public String id;

    private static Usuario instancia;

    private Usuario(){

    }

    //SINGLETON
    public static synchronized Usuario getInstancia(){
        if (instancia == null) {
            instancia = new Usuario();
        }
        return instancia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

