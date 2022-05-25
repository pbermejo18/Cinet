package com.example.cinet;

public class User {
    String nombre, email, movil, fecha_de_nacimiento;

    public User() {}

    public User(String nombre, String email, String movil, String fecha_de_nacimiento) {
        this.nombre = nombre;
        this.email = email;
        this.movil = movil;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getFecha_de_nacimiento() {
        return fecha_de_nacimiento;
    }

    public void setFecha_de_nacimiento(String fecha_de_nacimiento) {
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }

    @Override
    public String toString() {
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", movil='" + movil + '\'' +
                ", fecha_de_nacimiento='" + fecha_de_nacimiento + '\'' +
                '}';
    }
}
