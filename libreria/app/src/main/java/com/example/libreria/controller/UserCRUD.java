package com.example.libreria.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.libreria.model.DBHelper;
import com.example.libreria.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserCRUD {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public UserCRUD(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Crear un nuevo usuario
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("idusar", user.getIdusar());
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("pass", user.getPass());
        values.put("status", user.getStatus());
        database.insert("User", null, values);
    }

    // Obtener un usuario por ID
    public User getUserById(int idusar) {
        User user = null;
        Cursor cursor = null;
        try {
            cursor = database.query("User", null, "idusar=?", new String[]{String.valueOf(idusar)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                user = new User();
                user.setIdusar(cursor.getInt(cursor.getColumnIndexOrThrow("idusar")));
                user.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                user.setPass(cursor.getString(cursor.getColumnIndexOrThrow("pass")));
                user.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return user;
    }

    // Listar todos los usuarios
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query("User", null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setIdusar(cursor.getInt(cursor.getColumnIndexOrThrow("idusar")));
                    user.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                    user.setPass(cursor.getString(cursor.getColumnIndexOrThrow("pass")));
                    user.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                    users.add(user);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return users;
    }

    // Actualizar un usuario
    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("pass", user.getPass());
        values.put("status", user.getStatus());
        database.update("User", values, "idusar=?", new String[]{String.valueOf(user.getIdusar())});
    }

    // Eliminar un usuario
    public void deleteUser(int idusar) {
        database.delete("User", "idusar=?", new String[]{String.valueOf(idusar)});
    }

    // Cerrar la base de datos
    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
