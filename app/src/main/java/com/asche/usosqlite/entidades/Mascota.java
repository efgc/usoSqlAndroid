package com.asche.usosqlite.entidades;

import java.io.Serializable;

public class Mascota implements Serializable {

    private Integer idUsuario;
    private Integer idMascota;
    private String nombreMascota;
    private Integer idRaza;

    public Mascota(Integer idUsuario, Integer idMascota, String nombreMascota, Integer idRaza) {
        this.idUsuario = idUsuario;
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.idRaza = idRaza;
    }

    public Mascota() {
    }


    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public Integer getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Integer idRaza) {
        this.idRaza = idRaza;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "idUsuario=" + idUsuario +
                ", idMascota=" + idMascota +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", idRaza=" + idRaza +
                '}';
    }
}
