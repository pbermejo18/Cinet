package com.example.cinet;

public class Compra {
    // boolean aperitivos;
    String entradas, fecha, hora_entradas, precio, titulo, uid, id_compra;
    // Long entradas;

    public Compra() { }

    public Compra(String entradas, String fecha, String hora_entradas, String precio, String titulo, String uid, String id_compra) {
        this.entradas = entradas;
        this.fecha = fecha;
        this.hora_entradas = hora_entradas;
        this.precio = precio;
        this.titulo = titulo;
        this.uid = uid;
        this.id_compra = id_compra;
    }

    public String getId_compra() {
        return id_compra;
    }

    public void setId_compra(String id_compra) {
        this.id_compra = id_compra;
    }

    public String getEntradas() {
        return entradas;
    }

    public void setEntradas(String entradas) {
        this.entradas = entradas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora_entradas() {
        return hora_entradas;
    }

    public void setHora_entradas(String hora_entradas) {
        this.hora_entradas = hora_entradas;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "entradas='" + entradas + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora_entradas='" + hora_entradas + '\'' +
                ", precio='" + precio + '\'' +
                ", titulo='" + titulo + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
