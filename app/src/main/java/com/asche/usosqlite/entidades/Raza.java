package com.asche.usosqlite.entidades;

import java.io.Serializable;

public class Raza implements Serializable {

    private Integer idRaza;
    private String nombreRaza;

    public Raza() {
    }

    public Raza(Integer idRaza, String nombreRaza) {
        this.idRaza = idRaza;
        this.nombreRaza = nombreRaza;
    }

    public Integer getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Integer idRaza) {
        this.idRaza = idRaza;
    }

    public String getNombreRaza() {
        return nombreRaza;
    }

    public void setNombreRaza(String nombreRaza) {
        this.nombreRaza = nombreRaza;
    }

    @Override
    public String toString() {
        return "Raza{" +
                "idRaza=" + idRaza +
                ", nombreRaza='" + nombreRaza + '\'' +
                '}';
    }
}
