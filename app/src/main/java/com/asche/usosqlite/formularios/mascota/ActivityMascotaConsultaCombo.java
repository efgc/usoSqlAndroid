package com.asche.usosqlite.formularios.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Mascota;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesMascota;
import com.asche.usosqlite.utilidades.UtilidadesRaza;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.ArrayList;

public class ActivityMascotaConsultaCombo extends AppCompatActivity {


    Spinner comboMascotas;
    TextView txtId, txtNombre, txtRaza, txtUsuario;
    ArrayList<String> listaMascotas;
    ArrayList<Mascota> listaMascotasBack;
    ConexionSQLiteHelper conn;
    Integer idUsuario, idRaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_consulta_combo);

        this.iniciarComponentes();


    }


    private void iniciarComponentes() {
        this.comboMascotas = findViewById(R.id.mascotaConsultaComboSpinnerMascotas);
        this.txtId = findViewById(R.id.mascotaConsultaComboLblId);
        this.txtNombre = findViewById(R.id.mascotaConsultaComboLblNombre);
        this.txtRaza = findViewById(R.id.mascotaConsultaComboLblRaza);
        this.txtUsuario = findViewById(R.id.mascotaConsultaComboLblUsuario);
        this.listaMascotas = new ArrayList<>();
        this.listaMascotasBack = new ArrayList<>();
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);
        this.idRaza = 0;
        this.idUsuario = 0;

        this.consultarListaMascotas();
        this.obtenerLista();
        //Setting listener for the spinner
        this.comboMascotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: " + position);
                if (position != 0) {
                    txtId.setText(listaMascotasBack.get(position - 1).getIdMascota().toString());
                    txtNombre.setText(listaMascotasBack.get(position - 1).getNombreMascota());
                    idRaza = listaMascotasBack.get(position - 1).getIdRaza();
                    idUsuario = listaMascotasBack.get(position - 1).getIdUsuario();
                    //TODO GET RAZA AND USUARIO
                    String[] valores = obtenerRaza(idRaza, idUsuario);
                    txtRaza.setText(valores[0]);
                    txtUsuario.setText(valores[1]);



                } else {
                    txtId.setText("-----");
                    txtNombre.setText("-----");
                    txtRaza.setText("-----");
                    txtUsuario.setText("-----");
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private String[] obtenerRaza(Integer idRaza, Integer idUsuario) {
        String[] respuesta = new String[]{"",""};
        SQLiteDatabase db = this.conn.getReadableDatabase();
        String[] parametros = {idRaza.toString()};

        Cursor cursor = db.rawQuery(UtilidadesRaza.getSqlBuscarId(), parametros);
        cursor.moveToFirst();
        respuesta[0] = cursor.getString(1);
        cursor.close();
        db.close();

        db = this.conn.getReadableDatabase();
        parametros = new String[]{idUsuario.toString()};

        cursor = db.rawQuery(UtilidadesUsuario.getSqlBuscarId(), parametros);
        cursor.moveToFirst();
        respuesta[1] = cursor.getString(1) + ", id: " + cursor.getString(0);
        cursor.close();
        db.close();


        return respuesta;
    }

    private void consultarListaMascotas() {
        this.listaMascotasBack = new ArrayList<>();
        SQLiteDatabase db = this.conn.getReadableDatabase();


        Cursor cursor = db.rawQuery(UtilidadesMascota.getSqlBuscarTodos(), null);

        while (cursor.moveToNext()) {
            Mascota mascota = new Mascota();
            mascota.setIdMascota(cursor.getInt(0));
            mascota.setIdRaza(cursor.getInt(1));
            mascota.setIdUsuario(cursor.getInt(2));
            mascota.setNombreMascota(cursor.getString(3));


            this.listaMascotasBack.add(mascota);
        }
        cursor.close();
        db.close();
    }

    private void obtenerLista() {
        this.listaMascotas = new ArrayList<String>();
        this.listaMascotas.add("Seleccione");
        for (int i = 0; i < this.listaMascotasBack.size(); i++) {
            this.listaMascotas.add(this.listaMascotasBack.get(i).getIdMascota()
                    + " - " + this.listaMascotasBack.get(i).getNombreMascota());
        }

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaMascotas);
        this.comboMascotas.setAdapter(adaptador);

    }

}