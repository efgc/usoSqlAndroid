package com.asche.usosqlite.formularios.mascota;

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
import com.asche.usosqlite.entidades.Mascota;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.formularios.usuario.ActivityUsuarioConsultaLista;
import com.asche.usosqlite.formularios.usuario.ActivityUsuarioDetalle;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesMascota;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.ArrayList;

public class ActivityMascotaConsultaLista extends AppCompatActivity {

    ListView listViewMascotas;
    ArrayList<String> listaInformacion;
    ArrayList<Mascota> listaMascotas;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_consulta_lista);

        this.iniciarComponentes();
        this.consultarListaMascotas();

        this.vincularListView();
    }

    private void vincularListView() {
        ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaInformacion);
        this.listViewMascotas.setAdapter(adaptador);

        this.listViewMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Datos: " + listaMascotas.get(position).getIdMascota() +
                        " " + listaMascotas.get(position).getNombreMascota(), Toast.LENGTH_SHORT).show();

                Mascota mascota = listaMascotas.get(position);
                Intent intent = new Intent(ActivityMascotaConsultaLista.this, ActivityMascotaDetalle.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("mascota", mascota);

                intent.putExtras(bundle);
                startActivity(intent);
            }


        });
    }

    private void consultarListaMascotas() {
        SQLiteDatabase db = this.conn.getReadableDatabase();

        Mascota mascota = null;

        this.listaMascotas = new ArrayList<Mascota>();
        Cursor cursor = db.rawQuery(UtilidadesMascota.getSqlBuscarTodos(), null);
        while (cursor.moveToNext()) {
            mascota = new Mascota();
            mascota.setIdMascota(cursor.getInt(0));
            mascota.setIdRaza(cursor.getInt(1));
            mascota.setIdUsuario(cursor.getInt(2));
            mascota.setNombreMascota(cursor.getString(3));
            
            this.listaMascotas.add(mascota);
        }
        db.close();
        cursor.close();
        this.obtenerLista();
    }

    private void obtenerLista() {
        this.listaInformacion = new ArrayList<>();
        for (int i = 0; i < this.listaMascotas.size(); i++) {
            this.listaInformacion.add(this.listaMascotas.get(i).getIdMascota() + " --- " + this.listaMascotas.get(i).getNombreMascota());
        }
    }
    private void iniciarComponentes() {
        this.listViewMascotas = findViewById(R.id.mascotaConsultaListaLstMascotas);
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);
    }
}