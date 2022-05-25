package com.example.cinet;

public class Compra {
    boolean aperitivos;
    String hora, uid;
    Long entradas;

    public Compra() { }

    public Compra(boolean aperitivos, Long entradas, String hora, String uid) {
        this.aperitivos = aperitivos;
        this.entradas = entradas;
        this.hora = hora;
        this.uid = uid;
    }

    public boolean isAperitivos() {
        return aperitivos;
    }

    public void setAperitivos(boolean aperitivos) {
        this.aperitivos = aperitivos;
    }

    public Long getEntradas() {
        return entradas;
    }

    public void setEntradas(Long entradas) {
        this.entradas = entradas;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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
                "aperitivos=" + aperitivos +
                ", entradas=" + entradas +
                ", hora='" + hora + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
