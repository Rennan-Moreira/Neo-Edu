package com.example.rennan.neoedu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by André on 01/12/2016.
 */

public class BancoControle {
    private SQLiteDatabase db;

    public BancoControle(Context context){
        CriaBanco auxdb = new CriaBanco(context);
        db = auxdb.getWritableDatabase();
    }

    public void insertAluno(){

    }
}
