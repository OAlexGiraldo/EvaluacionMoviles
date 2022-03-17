package com.example.viajesevaluacion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Coneciones_Viajes extends SQLiteOpenHelper {

    public Coneciones_Viajes(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table TbLViajes(Codigo text primary key,Destino text not null," +
                "Cantida text not null,Valor text not null,activo text not null default 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE TblViajes");
        {
            onCreate(sqLiteDatabase);
        }


    }
}
