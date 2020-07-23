package com.asche.usosqlite.formularios.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import com.asche.usosqlite.entidades.Mascota;
import com.asche.usosqlite.entidades.Raza;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesMascota;
import com.asche.usosqlite.utilidades.UtilidadesRaza;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.ArrayList;
import java.util.List;

public class ActivityMascotaRegistro extends AppCompatActivity {

    List<Usuario> listaBackUsuarios;
    List<String> listaUsuarios;
    List<Raza> listaBackRazas;
    List<String> listaRazas;

    Spinner cmbUsuarios, cmbRazas;
    TextView txtNombreMascota;
    Integer idUsuario;
    Integer idRaza;
    ConexionSQLiteHelper conn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_registro);
        this.inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.listaBackUsuarios = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.listaBackRazas = new ArrayList<>();
        this.listaRazas = new ArrayList<>();

        this.cmbUsuarios = findViewById(R.id.mascotaRegistroCmbUsuario);
        this.cmbRazas = findViewById(R.id.mascotaRegistroCmbRaza);

        this.txtNombreMascota = findViewById(R.id.mascotaRegistroTxtNombreMascota);

        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

        this.idUsuario = 0;
        this.idRaza = 0;

        this.consultarListaUsuarios();
        this.consultarListaRazas();
        this.llenarCombos();
        this.colocarComboListeners();


    }

    private void colocarComboListeners() {
        this.cmbUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: " + position);
                if (position != 0) {
                    idUsuario = listaBackUsuarios.get(position -1).getId();

                } else {
                   idUsuario = 0;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.cmbRazas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: " + position);
                if (position != 0) {
                    idRaza = listaBackRazas.get(position -1).getIdRaza();

                } else {
                    idRaza = 0;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void llenarCombos() {
        this.listaRazas = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();

        this.listaRazas.add("Seleccione Raza");
        for (int i = 0; i < this.listaBackRazas.size(); i++) {
            this.listaRazas.add(this.listaBackRazas.get(i).getIdRaza()
                    + " - " + this.listaBackRazas.get(i).getNombreRaza());
        }

        System.out.println("Lista razas: " + listaRazas.size());
        System.out.println("Lista razas back: " + listaBackRazas.size());
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaRazas);
        this.cmbRazas.setAdapter(adaptador);


        this.listaUsuarios.add("Seleccione Usuario");
        for (int i = 0; i < this.listaBackUsuarios.size(); i++) {
            this.listaUsuarios.add(this.listaBackUsuarios.get(i).getId()
                    + " - " + this.listaBackUsuarios.get(i).getNombre());
        }

        System.out.println("Lista usuarios: " + listaRazas.size());
        System.out.println("Lista usuarios back: " + listaBackRazas.size());
        adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaUsuarios);
        this.cmbUsuarios.setAdapter(adaptador);
    }

    private void consultarListaRazas() {
        this.listaBackRazas = new ArrayList<>();

        SQLiteDatabase db = this.conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesRaza.TABLA_RAZA, null);

        while (cursor.moveToNext()) {
            Raza raza = new Raza();
            raza.setIdRaza(cursor.getInt(0));
            raza.setNombreRaza(cursor.getString(1));


            Log.i("Raza: ", raza.getIdRaza().toString() + " "+raza.getNombreRaza() );


            this.listaBackRazas.add(raza);
        }
        cursor.close();
        db.close();
    }

    private void consultarListaUsuarios() {
        this.listaBackUsuarios = new ArrayList<>();


        SQLiteDatabase db = this.conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesUsuario.TABLA_USUARIO, null);

        while (cursor.moveToNext()) {
            Usuario usuario = new Usuario();

            usuario.setId(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setTelefono(cursor.getString(2));

            Log.i("Usuario: ", usuario.getId().toString() + " "+usuario.getNombre() + " "+usuario.getTelefono());


            this.listaBackUsuarios.add(usuario);
        }
        cursor.close();
        db.close();
    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.mascotaRegistroBtnRegistrar:
                this.registrarMascota();
                break;
        }
    }

    private void registrarMascota() {
        if(this.idRaza == 0 || this.idUsuario == 0 || this.txtNombreMascota.getText().toString().equals("nombre raza")){
            Toast.makeText(getApplicationContext(), "Seleccione los valores", Toast.LENGTH_SHORT).show();
        }else{
            SQLiteDatabase db = this.conn.getWritableDatabase();
            //instruccion sql para insertar
            Mascota mascota = new Mascota();
            mascota.setIdUsuario(this.idUsuario);
            mascota.setNombreMascota(this.txtNombreMascota.getText().toString());
            mascota.setIdRaza(this.idRaza);

            ContentValues values = new ContentValues();

            values.put(UtilidadesMascota.CAMPO_ID_RAZA, mascota.getIdRaza());
            values.put(UtilidadesMascota.CAMPO_ID_USUARIO, mascota.getIdUsuario());
            values.put(UtilidadesMascota.CAMPO_NOMBRE_MASCOTA, mascota.getNombreMascota());

            Long idResultado = db.insert(UtilidadesMascota.TABLA_MASCOTA, UtilidadesMascota.CAMPO_ID_MASCOTA, values);
            //Cerrando la conexion

            //Cerrando la conexion
            db.close();
            Toast.makeText(getApplicationContext(),"Mascota registrada, id: "+idResultado,Toast.LENGTH_SHORT).show();
            this.limpiarCampos();
        }


    }

    private void limpiarCampos() {
        this.txtNombreMascota.setText("");
        this.consultarListaUsuarios();
        this.consultarListaRazas();
        this.llenarCombos();
        this.colocarComboListeners();
    }
}