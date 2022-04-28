package com.example.cinet;

public class Post {
    public String imagen;
    // Constructor vacio requerido por Firestore
    public Post() {}
    public Post(String imagen) {
        this.imagen = imagen;
    }
}
