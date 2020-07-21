package com.asche.usosqlite.formularios.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

public class ActivityUsuarioRegistro extends AppCompatActivity {

    EditText txtId, txtNombre, txtTelefono;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_registro);

        this.inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.txtId = findViewById(R.id.usuarioRegistroTxtId);
        this.txtNombre = findViewById(R.id.usuarioRegistroTxtNombre);
        this.txtTelefono = findViewById(R.id.usuarioRegistroTxtTelefono);
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);
    }


    private void registrarUsuario() {

        SQLiteDatabase db = this.conn.getWritableDatabase();

        //Usando ContentValues agregamos los valores
        ContentValues values = new ContentValues();
        values.put(UtilidadesUsuario.CAMPO_ID, this.txtId.getText().toString());
        values.put(UtilidadesUsuario.CAMPO_NOMBRE, this.txtNombre.getText().toString());
        values.put(UtilidadesUsuario.CAMPO_TELEFONO, this.txtTelefono.getText().toString());

        //db.insert devuelve el valor del id generado en la BDD
        //pide como parametros: el nombre de la tabla, la columna a devolver y los valores a insertar
        //en un objeto del tipo ContentValues
        Long idResultante = db.insert(UtilidadesUsuario.TABLA_USUARIO, UtilidadesUsuario.CAMPO_ID, values);

        Toast.makeText(getApplicationContext(), "Id registrado: " + idResultante, Toast.LENGTH_SHORT).show();

        //Cerrando la conexion
        db.close();


    }

    private void registrarUsuarioSql() {

        SQLiteDatabase db = this.conn.getWritableDatabase();
        //instruccion sql para insertar
        Usuario usuario = new Usuario();
        usuario.setId(Integer.parseInt(this.txtId.getText().toString()));
        usuario.setNombre(this.txtNombre.getText().toString());
        usuario.setTelefono(this.txtTelefono.getText().toString());

        String sql = UtilidadesUsuario.getSqlInsertar(usuario);
        System.out.println("----------------El sql es: " + sql);

        db.execSQL(sql);


        //Cerrando la conexion
        db.close();
        Toast.makeText(getApplicationContext(),"Usuario creado",Toast.LENGTH_SHORT).show();
        this.limpiarCampos();


    }

    private void limpiarCampos() {
        this.txtId.setText("");
        this.txtNombre.setText("");
        this.txtTelefono.setText("");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.usuarioRegistroBtnRegistrar:
                this.registrarUsuarioSql();
                break;
        }
    }
}