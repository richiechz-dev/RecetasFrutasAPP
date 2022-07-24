package com.example.appbeta.model;

public class user {
    String ApellidoM,ApellidoP,Email,contraseña,nombre;

    public user(){}

    public user(String apellidoM, String apellidoP, String email, String contraseña, String nombre) {
        ApellidoM = apellidoM;
        ApellidoP = apellidoP;
        Email = email;
        this.contraseña = contraseña;
        this.nombre = nombre;
    }

    public String getApellidoM() {
        return ApellidoM;
    }

    public void setApellidoM(String apellidoM) {
        ApellidoM = apellidoM;
    }

    public String getApellidoP() {
        return ApellidoP;
    }

    public void setApellidoP(String apellidoP) {
        ApellidoP = apellidoP;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
