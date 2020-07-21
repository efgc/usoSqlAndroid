package com.asche.usosqlite.utilidades;

import com.asche.usosqlite.entidades.Mascota;
import com.asche.usosqlite.entidades.Usuario;

public class UtilidadesMascota {

    //Constantes campos tabla usuario
    public static final String TABLA_MASCOTA = "mascota";
    public static final String CAMPO_ID_USUARIO = "id_usuario";
    public static final String CAMPO_ID_MASCOTA = "id_mascota";
    public static final String CAMPO_ID_RAZA = "id_raza";
    public static final String CAMPO_NOMBRE_MASCOTA = "nombre_mascota";

    //Sentencias SQL
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLA_MASCOTA
                    + "(" + CAMPO_ID_USUARIO + " INTEGER, "
                    + CAMPO_ID_MASCOTA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CAMPO_ID_RAZA + " INTEGER, "
                    + CAMPO_NOMBRE_MASCOTA + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLA_MASCOTA;

    public static final String getSqlInsertar(Mascota mascota) {
        String respuesta = "INSERT INTO " + TABLA_MASCOTA
                + "(" + CAMPO_ID_MASCOTA + ", "
                + CAMPO_ID_USUARIO + ", "
                + CAMPO_ID_RAZA + ", "
                + CAMPO_NOMBRE_MASCOTA + ")"
                + " VALUES("
                + mascota.getIdMascota()
                + ", " + mascota.getIdUsuario()
                + ", '" + mascota.getNombreMascota() + "')";

        return respuesta;
    }

    public static final String getSqlBuscarId() {
        String respuesta = "SELECT * FROM " + TABLA_MASCOTA
                + " WHERE " + CAMPO_ID_MASCOTA + " = ?";

        return respuesta;
    }

    public static final String getSqlActualizar() {
        String respuesta = "UPDATE " + TABLA_MASCOTA
                + " SET " + CAMPO_ID_USUARIO + " = ?, "
                + CAMPO_ID_RAZA + " = ? "
                + CAMPO_NOMBRE_MASCOTA + " = ? "
                + " WHERE " + CAMPO_ID_MASCOTA + " = ?";

        return respuesta;
    }

    public static final String getSqlEliminar() {
        String respuesta = "DELETE FROM " + TABLA_MASCOTA
                + " WHERE " + CAMPO_ID_MASCOTA + " = ?";

        return respuesta;
    }
}
