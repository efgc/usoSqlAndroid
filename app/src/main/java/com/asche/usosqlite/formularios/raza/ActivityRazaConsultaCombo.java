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

import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Raza;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesRaza;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.ArrayList;

public class ActivityRazaConsultaCombo extends AppCompatActivity {

    Spinner comboRazas;
    TextView txtId, txtRaza;
    ArrayList<String> listaRazas;
    ArrayList<Raza> listaRazasBack;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raza_consulta_combo);

        this.iniciarComponentes();
    }

    private void iniciarComponentes() {
        this.comboRazas = findViewById(R.id.razaConsultaComboSpinnerRazas);
        this.txtId = findViewById(R.id.razaConsultaComboLblId);
        this.txtRaza = findViewById(R.id.razaConsultaComboLblRaza);

        this.listaRazas = new ArrayList<>();
        this.listaRazasBack = new ArrayList<>();
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

        this.consultarListaPersonas();
        this.obtenerLista();

        //Setting listener for the spinner
        this.comboRazas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: " + position);
                if (position != 0) {
                    txtId.setText(listaRazasBack.get(position - 1).getIdRaza().toString());
                    txtRaza.setText(listaRazasBack.get(position - 1).getNombreRaza());

                } else {
                    txtId.setText("-----");
                    txtRaza.setText("-----");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = this.conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesRaza.TABLA_RAZA, null);

        while (cursor.moveToNext()) {
            Raza raza = new Raza();
            raza.setIdRaza(cursor.getInt(0));
            raza.setNombreRaza(cursor.getString(1));


            Log.i("id", raza.getIdRaza().toString());
            Log.i("nombre", raza.getNombreRaza());

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

        System.out.println("Lista usuarios: " + listaRazas.size());
        System.out.println("Lista usuarios back: " + listaRazasBack.size());
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaRazas);
        this.comboRazas.setAdapter(adaptador);
    }
}