package com.example.libreria.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.libreria.model.Book;
import com.example.libreria.model.DBHelper;
import com.example.libreria.model.Rent;
import com.example.libreria.model.User;

import java.util.ArrayList;
import java.util.List;

public class RentCRUD {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private UserCRUD userCRUD;
    private BookCRUD bookCRUD;

    public RentCRUD(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        userCRUD = new UserCRUD(context);
        bookCRUD = new BookCRUD(context);
    }

    public void addRent(Rent rent) {
        ContentValues values = new ContentValues();
        values.put("idusar", rent.getUserId());
        values.put("idbook", rent.getBookId());
        values.put("date", rent.getDate());
        database.insert("Rent", null, values);
    }

    public List<Rent> getAllRents() {
        List<Rent> rents = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query("Rent", null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Rent rent = new Rent();
                    rent.setIdrent(cursor.getInt(cursor.getColumnIndexOrThrow("idrent")));

                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow("idusar"));
                    int bookId = cursor.getInt(cursor.getColumnIndexOrThrow("idbook"));

                    User user = userCRUD.getUserById(userId);
                    Book book = bookCRUD.getBookById(bookId);

                    if (user != null && book != null) {
                        rent.setUser(user);
                        rent.setBook(book);
                        rent.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                        rents.add(rent);
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return rents;
    }

    public void deleteRent(int idrent) {
        database.delete("Rent", "idrent=?", new String[]{String.valueOf(idrent)});
    }
}
