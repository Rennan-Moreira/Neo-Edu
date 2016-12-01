package com.example.rennan.neoedu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Rennan on 30/11/2016.
 */

public class DBManager {
    private static DBHelper dbHelper=null;

    public DBManager(Context context){
        if(dbHelper == null){
            dbHelper = new DBHelper(context);
        }
    }

    public void addItem(String nome){
        String sql = "INSERT INTO itens (nome) VALUES ('"+nome+"')";
        ContentValues values = new ContentValues();
        dbHelper.getWritableDatabase().execSQL(sql);
    }
    public ArrayList<String> getAllItens(){
        String sql = "SELECT nome FROM itens";
        Cursor cursor;
        ArrayList<String> itens = null;

        cursor = dbHelper.getReadableDatabase().rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            itens = new ArrayList<String>();

            do {
                itens.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return itens;
    }
}
