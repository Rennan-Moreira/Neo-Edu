package com.example.rennan.neoedu;

import java.util.Date;

/**
 * Created by André on 24/11/2016.
 */

public class Professor extends Pessoas {

    int codProfessor;

    public Professor(){}

    public Professor(String nome, String sobrenome, Date dtNasc, String email, int codProfessor) {
        super(nome, sobrenome, dtNasc, email);
        this.codProfessor = codProfessor;
    }

    public int getCodProfessor() {
        return codProfessor;
    }
}
