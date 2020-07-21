package com.asche.usosqlite.formularios.usuario;

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

import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.ArrayList;

public class ActivityUsuarioConsultaLista extends AppCompatActivity {

    ListView listViewUsuarios;
    ArrayList<String> listaInformacion;
    ArrayList<Usuario> listaUsuarios;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_consulta_lista);


        this.iniciarComponentes();
        this.consultarListaPersonas();

        this.vincularListView();
    }

    private void vincularListView() {
        ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaInformacion);
        this.listViewUsuarios.setAdapter(adaptador);

        this.listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Datos: " + listaUsuarios.get(position).getId() +
                        " " + listaUsuarios.get(position).getNombre() + " " + listaUsuarios.get(position).getTelefono(), Toast.LENGTH_SHORT).show();

                Usuario usuario = listaUsuarios.get(position);
                Intent intent = new Intent(ActivityUsuarioConsultaLista.this, ActivityUsuarioDetalle.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);

                intent.putExtras(bundle);
                startActivity(intent);
            }


        });
    }


    private void iniciarComponentes() {
        this.listViewUsuarios = findViewById(R.id.usuarioConsultaListaLstUsuarios);
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = this.conn.getReadableDatabase();

        Usuario usuario = null;
        this.listaUsuarios = new ArrayList<Usuario>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesUsuario.TABLA_USUARIO, null);
        while (cursor.moveToNext()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setTelefono(cursor.getString(2));

            this.listaUsuarios.add(usuario);

        }
        db.close();
        cursor.close();
        this.obtenerLista();
    }

    private void obtenerLista() {
        this.listaInformacion = new ArrayList<>();
        for (int i = 0; i < this.listaUsuarios.size(); i++) {
            this.listaInformacion.add(this.listaUsuarios.get(i).getId() + " --- " + this.listaUsuarios.get(i).getNombre());
        }
    }

}