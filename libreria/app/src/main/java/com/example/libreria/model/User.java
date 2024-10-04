package com.example.libreria.model;

public class User {
    private int idusar;
    private String name;
    private String email;
    private String pass;
    private String status;

    public User(int idusar, String name, String email, String pass, String status) {
        this.idusar = idusar;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.status = status;
    }

    public User() {
    }

    // Getters y Setters
    public int getIdusar() {
        return idusar;
    }

    public void setIdusar(int idusar) {
        this.idusar = idusar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name; // Mostrar el nombre del usuario en el Spinner
    }
}
