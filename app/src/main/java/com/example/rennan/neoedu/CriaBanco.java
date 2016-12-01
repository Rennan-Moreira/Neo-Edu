package com.example.rennan.neoedu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "cadastro.db";
    private static final int VERSAO_DB = 1;
    private static final String TAB_ALUNO ="aluno";
    private static final String TAB_PROFESSOR = "professor";
    private static final String NOME_ALUNO = "nomeAluno";
    private static final String NOME_PROFESSOR = "nomeProfessor";
    private static final String USER_ALUNO = "usuarioAluno";
    private static final String USER_PROFESSOR ="usuarioProfessor";
    private static final String SENHA_ALUNO = "senhaAluno";
    private static final String SENHA_PROFESSOR = "senhaProfessor";
    private static final String DATA_NASC_ALUNO = "dtNascAluno";
    private static final String DATA_NASC_PROFESSOR = "dtNascProfessor";
    private static final String EMAIL_ALUNO = "emailAluno";
    private static final String EMAIL_PROFESSOR ="emailProfessor";

    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}