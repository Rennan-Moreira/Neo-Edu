package com.example.rennan.neoedu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rennan on 28/11/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "teste";
    private static int DV_VERSION = 1;

    private static String TABLE_ITENS = "CREATE TABLE itens("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nome TEXT"+
            ");";

    private static final String NOME_BANCO = "cadastro.db";
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

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DV_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ITENS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int vA, int vN) {

    }
}
