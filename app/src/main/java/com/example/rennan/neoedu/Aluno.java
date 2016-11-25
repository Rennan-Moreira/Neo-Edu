package com.example.rennan.neoedu;

import java.util.Date;

/**
 * Created by André on 24/11/2016.
 */

public class Aluno extends Pessoas {

    public int codAluno;

    public Aluno(){

    }

    public Aluno(String usuario, String senha, String nome, Date dtNasc, String email, int codAluno){
        super(usuario, senha, nome, dtNasc, email);
        this.codAluno = codAluno;
    }

    public int getcodAluno() {
        return codAluno;
    }

}
