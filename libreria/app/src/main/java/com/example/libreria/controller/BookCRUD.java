package com.example.libreria.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.libreria.model.Book;
import com.example.libreria.model.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class BookCRUD {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public BookCRUD(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Crear un nuevo libro
    public void addBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("idbook", book.getIdbook());
        values.put("name", book.getName());
        values.put("coste", book.getCoste());
        values.put("available", book.isAvailable() ? 1 : 0);
        database.insert("Book", null, values);
    }

    // Actualizar un libro
    public void updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("name", book.getName());
        values.put("coste", book.getCoste());
        values.put("available", book.isAvailable() ? 1 : 0);
        database.update("Book", values, "idbook=?", new String[]{String.valueOf(book.getIdbook())});
    }

    // Eliminar un libro
    public void deleteBook(int idbook) {
        database.delete("Book", "idbook=?", new String[]{String.valueOf(idbook)});
    }

    // Obtener todos los libros
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query("Book", null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Book book = new Book();
                    book.setIdbook(cursor.getInt(cursor.getColumnIndexOrThrow("idbook")));
                    book.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    book.setCoste(cursor.getDouble(cursor.getColumnIndexOrThrow("coste")));
                    book.setAvailable(cursor.getInt(cursor.getColumnIndexOrThrow("available")) == 1);
                    books.add(book);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return books;
    }

    // Obtener un libro por ID
    public Book getBookById(int idbook) {
        Book book = null;
        Cursor cursor = null;
        try {
            cursor = database.query("Book", null, "idbook=?", new String[]{String.valueOf(idbook)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                book = new Book();
                book.setIdbook(cursor.getInt(cursor.getColumnIndexOrThrow("idbook")));
                book.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                book.setCoste(cursor.getDouble(cursor.getColumnIndexOrThrow("coste")));
                book.setAvailable(cursor.getInt(cursor.getColumnIndexOrThrow("available")) == 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return book;
    }
    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        Cursor cursor = null;
        try {
            // Consulta para obtener solo los libros que están disponibles
            cursor = database.query("Book", null, "available = ?", new String[]{"1"}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Book book = new Book();
                    book.setIdbook(cursor.getInt(cursor.getColumnIndexOrThrow("idbook")));
                    book.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    book.setCoste(cursor.getDouble(cursor.getColumnIndexOrThrow("coste")));
                    book.setAvailable(cursor.getInt(cursor.getColumnIndexOrThrow("available")) == 1);
                    availableBooks.add(book);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return availableBooks;
    }
    public void updateBookAvailability(int idbook, int available) {
        ContentValues values = new ContentValues();
        values.put("available", available);
        database.update("Book", values, "idbook=?", new String[]{String.valueOf(idbook)});
    }


}
