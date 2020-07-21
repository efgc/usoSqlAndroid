package com.asche.usosqlite.formularios.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.R;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.Arrays;

public class ActivityUsuarioConsultaId extends AppCompatActivity {

    EditText txtId, txtNombre, txtTelefono;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_consulta_id);

        this.inicializarComponentes();
    }

    private void inicializarComponentes() {

        this.txtId = findViewById(R.id.usuarioConsultaIdTxtId);
        this.txtNombre = findViewById(R.id.usuarioConsultaIdTxtNombre);
        this.txtTelefono = findViewById(R.id.usuarioConsultaIdTxtTelefono);
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

    }

    private void consultar() {
        SQLiteDatabase db = this.conn.getReadableDatabase();
        String[] parametros = {this.txtId.getText().toString()};
        String[] campos = {UtilidadesUsuario.CAMPO_NOMBRE, UtilidadesUsuario.CAMPO_TELEFONO};

        try {
            Cursor cursor = db.query(UtilidadesUsuario.TABLA_USUARIO, campos,
                    UtilidadesUsuario.CAMPO_ID + "=?",
                    parametros, null, null, null);

            cursor.moveToFirst();
            System.out.println("---------------------Cursor count:  " + cursor.getCount());
            this.txtNombre.setText(cursor.getString(0));
            this.txtTelefono.setText(cursor.getString(1));
            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al buscar id", Toast.LENGTH_SHORT).show();
            this.limpiarCampos();
        }

    }

    private void consultarSql() {
        SQLiteDatabase db = this.conn.getReadableDatabase();
        String[] parametros = {this.txtId.getText().toString()};


        try {
            System.out.println("SQL ------------ " + UtilidadesUsuario.getSqlBuscarId() + " Param: "+ Arrays.toString(parametros));
            Cursor cursor = db.rawQuery(UtilidadesUsuario.getSqlBuscarId(), parametros);
            cursor.moveToFirst();
            this.txtNombre.setText(cursor.getString(1));
            this.txtTelefono.setText(cursor.getString(2));
            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al buscar id", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

    }

    private void actualizarUsuario() {
        SQLiteDatabase db = this.conn.getWritableDatabase();
        String[] parametros = {this.txtId.getText().toString()};
        ContentValues values = new ContentValues();
        values.put(UtilidadesUsuario.CAMPO_NOMBRE, this.txtNombre.getText().toString());
        values.put(UtilidadesUsuario.CAMPO_TELEFONO, this.txtTelefono.getText().toString());

        int numero = db.update(UtilidadesUsuario.TABLA_USUARIO, values, UtilidadesUsuario.CAMPO_ID + "=?", parametros);
        Toast.makeText(getApplicationContext(), "Actualizado, num: " + numero, Toast.LENGTH_SHORT).show();
        db.close();
    }



    private void eliminarUsuario() {
        SQLiteDatabase db = this.conn.getWritableDatabase();
        String[] parametros = {this.txtId.getText().toString()};


        int numero = db.delete(UtilidadesUsuario.TABLA_USUARIO, UtilidadesUsuario.CAMPO_ID + "=?", parametros);
        Toast.makeText(getApplicationContext(), "Eliminado, num: " + numero, Toast.LENGTH_SHORT).show();
        db.close();
    }


    private void actualizarUsuarioSql() {
        SQLiteDatabase db = this.conn.getWritableDatabase();
        String[] parametros = {this.txtNombre.getText().toString(),this.txtTelefono.getText().toString(),this.txtId.getText().toString()};


        db.execSQL(UtilidadesUsuario.getSqlActualizar(),parametros);
        Toast.makeText(getApplicationContext(), "Actualizado, con SQL: ", Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void eliminarUsuarioSql() {
        SQLiteDatabase db = this.conn.getWritableDatabase();
        String[] parametros = {this.txtId.getText().toString()};


        db.execSQL(UtilidadesUsuario.getSqlEliminar(),parametros);
        Toast.makeText(getApplicationContext(), "Eliminado, con SQL: ", Toast.LENGTH_SHORT).show();
        db.close();
    }


    private void limpiarCampos() {
        this.txtId.setText("");
        this.txtNombre.setText("");
        this.txtTelefono.setText("");
    }

    public void onClick(View view) {
        Intent myIntent;
        switch (view.getId()) {
            case R.id.usuarioConsultaIdBtnBuscar:
                //this.consultar();
                this.consultarSql();
                break;

            case R.id.usuarioConsultaIdBtnActualizar:
                //this.actualizarUsuario();
                this.actualizarUsuarioSql();
                this.limpiarCampos();
                break;

            case R.id.usuarioConsultaIdBtnEliminar:
                //this.eliminarUsuario();
                this.eliminarUsuarioSql();
                this.limpiarCampos();
                break;

        }
    }




}