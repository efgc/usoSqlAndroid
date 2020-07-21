package com.asche.usosqlite.utilidades;

import com.asche.usosqlite.entidades.Usuario;

public class UtilidadesUsuario {

    //Constantes campos tabla usuario
    public static final String TABLA_USUARIO = "usuario";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_TELEFONO = "telefono";

    //Sentencias SQL
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLA_USUARIO
                    + "(" + CAMPO_ID + " INTEGER, "
                    + CAMPO_NOMBRE + " TEXT, "
                    + CAMPO_TELEFONO + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLA_USUARIO;

    public static final String getSqlInsertar(Usuario usuario) {
        String respuesta = "INSERT INTO " + TABLA_USUARIO
                + "(" + CAMPO_ID + ", "
                + CAMPO_NOMBRE + ", "
                + CAMPO_TELEFONO + ")"
                + " VALUES("
                + usuario.getId()
                + ", '"+usuario.getNombre()+"'"
                +", '"+usuario.getTelefono()+"')";

        return respuesta;
    }

    public static final String getSqlBuscarId() {
        String respuesta = "SELECT * FROM " + TABLA_USUARIO
                + " WHERE " + CAMPO_ID + " = ?" ;

        return respuesta;
    }

    public static final String getSqlActualizar() {
        String respuesta = "UPDATE " + TABLA_USUARIO
                + " SET " + CAMPO_NOMBRE + " = ?, "
                + CAMPO_TELEFONO + " = ? "
                + " WHERE " + CAMPO_ID + " = ?";

        return respuesta;
    }

    public static final String getSqlEliminar(){
        String respuesta = "DELETE FROM " + TABLA_USUARIO
                + " WHERE " + CAMPO_ID + " = ?" ;

        return respuesta;
    }
}
