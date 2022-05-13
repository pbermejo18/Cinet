package com.example.cinet;

public class Post {
    public String imagen, titulo, descripcion;
    // Constructor vacio requerido por Firestore
    public Post() {}
    public Post(String imagen, String titulo, String descripcion) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
}
