package com.asche.usosqlite.formularios.raza;

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
import android.widget.Toast;

import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Raza;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesMascota;
import com.asche.usosqlite.utilidades.UtilidadesRaza;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.ArrayList;

public class ActivityConsultaRaza extends AppCompatActivity {

    private Spinner comboRazas;
    private TextView txtRaza;
    private ArrayList<String> listaRazas;
    private ArrayList<Raza> listaRazasBack;
    private ConexionSQLiteHelper conn;
    private Integer idRaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_raza);

        this.inicializarComponentes();

    }

    private void inicializarComponentes() {
        this.comboRazas = findViewById(R.id.razaConsultaIdCmbRazas);
        this.txtRaza = findViewById(R.id.razaConsultaIdTxtNombre);
        this.listaRazas = new ArrayList<>();
        this.listaRazasBack = new ArrayList<>();
        this.idRaza = 0;
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

        this.consultarListaRazas();
        this.obtenerLista();
        this.agregarListener();

    }

    private void agregarListener() {
        //Setting listener for the spinner
        this.comboRazas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: " + position);
                if (position != 0) {
                    txtRaza.setText(listaRazasBack.get(position - 1).getNombreRaza());
                    idRaza = listaRazasBack.get(position -1).getIdRaza();

                } else {

                    txtRaza.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void consultarListaRazas() {
        this.listaRazas = new ArrayList<>();
        this.listaRazasBack = new ArrayList<>();
        SQLiteDatabase db = this.conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesRaza.TABLA_RAZA, null);

        while (cursor.moveToNext()) {
            Raza raza = new Raza();
            raza.setIdRaza(cursor.getInt(0));
            raza.setNombreRaza(cursor.getString(1));

            Log.i("id", raza.getIdRaza().toString());
            Log.i("raza", raza.getNombreRaza());

            this.listaRazasBack.add(raza);
        }
        cursor.close();
        db.close();

    }

    private void obtenerLista() {
        this.listaRazas = new ArrayList<String>();
        this.listaRazas.add("Seleccione");
        for (int i = 0; i < this.listaRazasBack.size(); i++) {
            this.listaRazas.add(this.listaRazasBack.get(i).getIdRaza()
                    + " - " + this.listaRazasBack.get(i).getNombreRaza());
        }

        System.out.println("Lista razas: " + listaRazas.size());
        System.out.println("Lista usuarios back: " + listaRazasBack.size());
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaRazas);
        this.comboRazas.setAdapter(adaptador);

    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.razaConsultaIdBtnActualizar:
                this.actualizarRaza();
                break;

            case R.id.razaConsultaIdBtnEliminar:
                this.eliminarRaza();
                break;

        }
    }

    private void eliminarRaza() {
        if(this.comboRazas.getSelectedItem().toString().equals("Seleccione")){
            Toast.makeText(getApplicationContext(), "Seleccione una raza", Toast.LENGTH_SHORT).show();
        }


        else if (this.txtRaza.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese nombre de raza", Toast.LENGTH_SHORT).show();
        }
        else{
            SQLiteDatabase db = this.conn.getWritableDatabase();
            String[] parametros = {this.idRaza.toString()};


            db.execSQL(UtilidadesRaza.getSqlEliminar(),parametros);
            Toast.makeText(getApplicationContext(), "Eliminado, con SQL: ", Toast.LENGTH_SHORT).show();
            db.close();
            this.consultarListaRazas();
            this.obtenerLista();
            this.agregarListener();
        }
    }

    private void actualizarRaza() {
        if(this.comboRazas.getSelectedItem().toString().equals("Seleccione")){
            Toast.makeText(getApplicationContext(), "Seleccione una raza", Toast.LENGTH_SHORT).show();
        }


        else if (this.txtRaza.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese nombre de raza", Toast.LENGTH_SHORT).show();
        }
        else{
            SQLiteDatabase db = this.conn.getWritableDatabase();
            String[] parametros = {this.txtRaza.getText().toString(), this.idRaza.toString()};


            db.execSQL(UtilidadesRaza.getSqlActualizar(),parametros);
            Toast.makeText(getApplicationContext(), "Actualizado, con SQL: ", Toast.LENGTH_SHORT).show();
            db.close();
            this.consultarListaRazas();
            this.obtenerLista();
            this.agregarListener();
        }
    }
}