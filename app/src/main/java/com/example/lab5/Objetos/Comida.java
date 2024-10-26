package com.example.lab5.Objetos;

import java.io.Serializable;

public class Comida implements Serializable {

    private Integer id;
    private String nomrbe;
    private Float calorias;

    public Comida(String nombre, float calorias) {
        this.nomrbe = nombre;
        this.calorias = calorias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomrbe() {
        return nomrbe;
    }

    public void setNomrbe(String nomrbe) {
        this.nomrbe = nomrbe;
    }

    public Float getCalorias() {
        return calorias;
    }

    public void setCalorias(Float calorias) {
        this.calorias = calorias;
    }
}
