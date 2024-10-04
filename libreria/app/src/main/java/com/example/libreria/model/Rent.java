package com.example.libreria.model;

public class Rent {
    private int idrent;
    private User user; // Usando la clase User
    private Book book; // Usando la clase Book
    private String date;

    public Rent(int idrent, User user, Book book, String date) {
        this.idrent = idrent;
        this.user = user;
        this.book = book;
        this.date = date;
    }

    public Rent() {
    }

    // Getters y Setters
    public int getIdrent() {
        return idrent;
    }

    public void setIdrent(int idrent) {
        this.idrent = idrent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Métodos adicionales para obtener IDs
    public int getUserId() {
        return user.getIdusar();
    }

    public int getBookId() {
        return book.getIdbook();
    }

    @Override
    public String toString() {
        return "Renta: " + book.getName() + " por " + user.getName() + " en la fecha " + date; // Muestra información útil
    }
}
