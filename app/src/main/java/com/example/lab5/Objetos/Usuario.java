package com.example.lab5.Objetos;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String peso;
    private String altura;
    private String edad;
    private String genero;
    private String nivelActividad;
    private String objetivo;

    public Usuario(String peso, String altura, String edad, String genero, String nivelActividad, String objetivo) {
        this.peso = peso;
        this.altura = altura;
        this.edad = edad;
        this.genero = genero;
        this.nivelActividad = nivelActividad;
        this.objetivo = objetivo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNivelActividad() {
        return nivelActividad;
    }

    public void setNivelActividad(String nivelActividad) {
        this.nivelActividad = nivelActividad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}
