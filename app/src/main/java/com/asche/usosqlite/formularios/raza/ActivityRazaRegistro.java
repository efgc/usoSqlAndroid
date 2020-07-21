package com.asche.usosqlite.formularios.raza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Raza;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesMascota;
import com.asche.usosqlite.utilidades.UtilidadesRaza;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

public class ActivityRazaRegistro extends AppCompatActivity {

    TextView txtNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raza_registro);
        this.inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.txtNombre = findViewById(R.id.razaRegistroTxtNombre);
    }

    public void onClick(View view) {

        switch(view.getId()){
            case R.id.razaRegistroBtnRegistrar:
                this.registrarRaza();
                break;
        }
    }

    private void registrarRaza() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, Utilidades.BDD_NAME,
                null, Utilidades.BDD_VERSION);

        SQLiteDatabase db = conn.getWritableDatabase();
        //instruccion sql para insertar
        Raza raza = new Raza();

        raza.setNombreRaza(this.txtNombre.getText().toString());

        ContentValues values = new ContentValues();

        values.put(UtilidadesRaza.CAMPO_NOMBRE, raza.getNombreRaza());

        Long idResultado = db.insert(UtilidadesRaza.TABLA_RAZA, UtilidadesRaza.CAMPO_ID, values);
        //Cerrando la conexion
        db.close();
        Toast.makeText(getApplicationContext(),"Raza registrada con id: "+idResultado,Toast.LENGTH_SHORT).show();
        this.limpiarCampos();



    }

    private void limpiarCampos() {
        this.txtNombre.setText("");
    }
}