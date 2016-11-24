package com.example.rennan.neoedu;

import java.util.Date;

/**
 * Created by André on 24/11/2016.
 */

public abstract class Pessoas {

    private String nome;
    private String sobrenome;
    private Date dtNasc;        //public Date(int year, int month, int date)
    private String email;

    public Pessoas(){}

    public Pessoas(String nome, String sobrenome, Date dtNasc, String email){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = this.dtNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

