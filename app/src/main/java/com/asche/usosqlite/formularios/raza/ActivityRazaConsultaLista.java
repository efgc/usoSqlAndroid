package com.asche.usosqlite.formularios.raza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Raza;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.formularios.usuario.ActivityUsuarioConsultaLista;
import com.asche.usosqlite.formularios.usuario.ActivityUsuarioDetalle;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesRaza;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.ArrayList;

public class ActivityRazaConsultaLista extends AppCompatActivity {
    ListView listViewRazas;
    ArrayList<String> listaInformacion;
    ArrayList<Raza> listaRazas;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raza_consulta_lista);

        this.iniciarComponentes();
        this.consultarListaRazas();

        this.vincularListView();
    }

    private void iniciarComponentes() {
        this.listViewRazas = findViewById(R.id.razaConsultaListaLstRazas);
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

    }

    private void consultarListaRazas() {
        SQLiteDatabase db = this.conn.getReadableDatabase();

        Raza raza = null;
        this.listaRazas = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesRaza.TABLA_RAZA, null);
        while (cursor.moveToNext()) {
            raza = new Raza();
            raza.setIdRaza(cursor.getInt(0));
            raza.setNombreRaza(cursor.getString(1));

            System.out.println("---------"+raza.toString());

            this.listaRazas.add(raza);

        }
        db.close();
        cursor.close();
        this.obtenerLista();
    }

    private void obtenerLista() {
        this.listaInformacion = new ArrayList<>();
        for (int i = 0; i < this.listaRazas.size(); i++) {
            System.out.println("-----------------------------i: "+ i);
            this.listaInformacion.add(this.listaRazas.get(i).getIdRaza() + " --- " + this.listaRazas.get(i).getNombreRaza());
        }
    }

    private void vincularListView() {
        ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaInformacion);
        this.listViewRazas.setAdapter(adaptador);

        this.listViewRazas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Id: " + listaRazas.get(position).getIdRaza() +
                        "\nRaza: " + listaRazas.get(position).getNombreRaza(),Toast.LENGTH_SHORT).show();

//                Usuario usuario = listaUsuarios.get(position);
//                Intent intent = new Intent(ActivityUsuarioConsultaLista.this, ActivityUsuarioDetalle.class);
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("usuario", usuario);
//
//                intent.putExtras(bundle);
//                startActivity(intent);
            }


        });

    }
}