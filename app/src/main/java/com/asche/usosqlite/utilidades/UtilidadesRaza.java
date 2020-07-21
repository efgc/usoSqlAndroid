package com.asche.usosqlite.utilidades;

import com.asche.usosqlite.entidades.Raza;
import com.asche.usosqlite.entidades.Usuario;

public class UtilidadesRaza {
    //Constantes campos tabla usuario
    public static final String TABLA_RAZA = "raza";
    public static final String CAMPO_ID = "id_raza";
    public static final String CAMPO_NOMBRE = "nombre_raza";


    //Sentencias SQL
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLA_RAZA
                    + "(" + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_NOMBRE + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLA_RAZA;

    public static final String getSqlInsertar(Raza raza) {
        String respuesta = "INSERT INTO " + TABLA_RAZA
                + "(" + CAMPO_ID + ", "
                + CAMPO_NOMBRE + ")"
                + " VALUES("
                + raza.getIdRaza()
                + ", '" + raza.getNombreRaza() + "')";

        return respuesta;
    }

    public static final String getSqlBuscarId() {
        String respuesta = "SELECT * FROM " + TABLA_RAZA
                + " WHERE " + CAMPO_ID + " = ?";

        return respuesta;
    }

    public static final String getSqlActualizar() {
        String respuesta = "UPDATE " + TABLA_RAZA
                + " SET " + CAMPO_NOMBRE + " = ? "
                + " WHERE " + CAMPO_ID + " = ?";

        return respuesta;
    }

    public static final String getSqlEliminar() {
        String respuesta = "DELETE FROM " + TABLA_RAZA
                + " WHERE " + CAMPO_ID + " = ?";

        return respuesta;
    }
}
