package com.example.rennan.neoedu;

import android.media.Image;

import java.util.Date;

/**
 * Created by André on 24/11/2016.
 */

public abstract class Pessoas {

    private String usuario;
    private String senha;
    private String nome;
    private Date   nasc;        //public Date(int year, int month, int date)
    private String email;

    public Pessoas(){}

    public Pessoas(String usuario, String senha, String nome, Date nasc, String email){
        this.nome = nome;
        this.nasc = nasc;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNasc() {
        return nasc;
    }

    public void setNasc(Date nasc) {
        this.nasc = nasc;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

