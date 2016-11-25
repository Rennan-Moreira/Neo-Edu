package com.example.rennan.neoedu;

import java.util.Date;

/**
 * Created by André on 24/11/2016.
 */

public class Professor extends Pessoas {

    int codProfessor;

    public Professor(){}

    public Professor(String usuario, String senha, String nome, Date dtNasc, String email, int codProfessor) {
        super(usuario, senha, nome, dtNasc, email);
        this.codProfessor = codProfessor;
    }

    public int getCodProfessor() {
        return codProfessor;
    }
}
