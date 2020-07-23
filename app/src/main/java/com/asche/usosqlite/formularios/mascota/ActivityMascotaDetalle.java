package com.asche.usosqlite.formularios.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Mascota;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesRaza;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

public class ActivityMascotaDetalle extends AppCompatActivity {

    TextView txtIdMascota, txtNombreMascota, txtRaza, txtUsuario;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_detalle);

        this.iniciarComponentes();
        this.recivirDatos();
    }

    private void iniciarComponentes() {
        this.txtIdMascota = findViewById(R.id.mascotaDetalleTxtIdMascota);
        this.txtNombreMascota = findViewById(R.id.mascotaDetalleTxtNombre);
        this.txtRaza = findViewById(R.id.mascotaDetalleTxtRaza);
        this.txtUsuario = findViewById(R.id.mascotaDetalleTxtUsuario);
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);


    }

    private void recivirDatos() {
        Bundle objetoRecibido = getIntent().getExtras();
        Mascota mascota = null;
        if(objetoRecibido != null){
            mascota = (Mascota)objetoRecibido.getSerializable("mascota");
        }
        this.modificarCampos(mascota);

    }

    private void modificarCampos(Mascota mascota) {
        this.txtIdMascota.setText(mascota.getIdMascota().toString());
        this.txtNombreMascota.setText(mascota.getNombreMascota());
        SQLiteDatabase db = this.conn.getReadableDatabase();
        String[] parametros = {mascota.getIdRaza().toString()};

        Cursor cursor = db.rawQuery(UtilidadesRaza.getSqlBuscarId(), parametros);
        cursor.moveToFirst();
        this.txtRaza.setText(cursor.getString(1));
        cursor.close();
        db.close();

        db = this.conn.getReadableDatabase();
        parametros =new String[] {mascota.getIdUsuario().toString()};

        cursor = db.rawQuery(UtilidadesUsuario.getSqlBuscarId(), parametros);
        cursor.moveToFirst();
        this.txtUsuario.setText(cursor.getString(1)+", id: "+mascota.getIdUsuario());
        cursor.close();
        db.close();



    }
}