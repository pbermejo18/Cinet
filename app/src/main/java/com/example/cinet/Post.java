package com.example.cinet;

public class Post {
    public String imagen, titulo, descripcion, tipo, productores, reparto;
    // Constructor vacio requerido por Firestore
    public Post() {}
    public Post(String imagen, String titulo, String descripcion, String tipo, String productores, String reparto) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo=tipo;
        this.productores=productores;
        this.reparto=reparto;
    }
}
