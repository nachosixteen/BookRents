package com.example.libreria.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "libreria.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (" +
                "idusar INTEGER PRIMARY KEY," +
                "name TEXT," +
                "email TEXT UNIQUE," +  // Asegúrate de que el email sea único
                "pass TEXT," +
                "status TEXT" +
                ")");

        db.execSQL("CREATE TABLE Book (" +
                "idbook INTEGER PRIMARY KEY," +
                "name TEXT," +
                "coste REAL," +
                "available INTEGER" +
                ")");

        db.execSQL("CREATE TABLE Rent (" +
                "idrent INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idusar INTEGER," +
                "idbook INTEGER," +
                "date TEXT," +
                "FOREIGN KEY (idusar) REFERENCES User(idusar)," +  // Clave foránea
                "FOREIGN KEY (idbook) REFERENCES Book(idbook)" +  // Clave foránea
                ")");

        db.execSQL("CREATE TABLE Devolucion (" +
                "idDevolucion INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idusar INTEGER," +
                "idBook INTEGER," +
                "date TEXT," +
                "FOREIGN KEY (idusar) REFERENCES User(idusar)," +  // Clave foránea
                "FOREIGN KEY (idBook) REFERENCES Book(idbook)" +  // Clave foránea
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Realiza aquí cualquier migración específica para la versión 2
            // o simplemente elimina y vuelve a crear como lo tienes ahora.
            db.execSQL("DROP TABLE IF EXISTS Devolucion");
            db.execSQL("DROP TABLE IF EXISTS Rent");
            db.execSQL("DROP TABLE IF EXISTS Book");
            db.execSQL("DROP TABLE IF EXISTS User");
            onCreate(db);
        }
    }
}
