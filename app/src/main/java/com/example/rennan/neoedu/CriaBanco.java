package com.example.rennan.neoedu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "cadastro.db";
    private static final int VERSAO_DB = 1;
    private static final String TAB_ALUNO ="aluno";
    private static final int ID_ALUNO = 0;
    private static final int ID_PROFESSOR =0;
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
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TAB_ALUNO+"("+ID_ALUNO+" primary key autoincrement, "+NOME_ALUNO+" text not null, "+USER_ALUNO+" text not null, " +
                ""+SENHA_ALUNO+" text not null, "+DATA_NASC_ALUNO+" date not null, "+EMAIL_ALUNO+" text not null)");

        db.execSQL("CREATE TABLE "+TAB_PROFESSOR+"("+ID_PROFESSOR+" primary key autoincrement, "+NOME_PROFESSOR+" text not null, "+USER_PROFESSOR+" text not null, " +
                ""+SENHA_PROFESSOR+" text not null, "+DATA_NASC_PROFESSOR+" date not null, "+EMAIL_PROFESSOR+" text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+TAB_ALUNO+" ");
        db.execSQL("drop table if exists "+TAB_PROFESSOR+" ");
        onCreate(db);
    }
}