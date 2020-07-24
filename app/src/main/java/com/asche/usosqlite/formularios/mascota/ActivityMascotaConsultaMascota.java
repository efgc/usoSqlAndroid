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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ActivityMascotaConsultaMascota extends AppCompatActivity {

    Spinner cmbMascotas, cmbRazas, cmbUsuarios;
    TextView txtNombre;
    List<String> listaRazas, listaUsuarios, listaMascotas;
    List<Usuario> listaBackUsuarios;
    List<Mascota> listaBackMascotas;
    List<Raza> listaBackRazas;
    ConexionSQLiteHelper conn;
    Integer idMascota, idRaza, idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_consulta_mascota);
        this.iniciarComponentes();
        this.cargarCombos();
        this.colocarListeners();
    }

    private void colocarListeners() {
        this.colocarListenerMascota();
    }

    private void colocarListenerMascota() {
        //Setting listener for the spinner
        this.cmbMascotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: " + position);
                if (position != 0) {
                    habilitarCombos();
                    txtNombre.setText(listaBackMascotas.get(position - 1).getNombreMascota());
                    cmbRazas.setSelection(obtienePosicionRaza(listaBackMascotas.get(position - 1).getIdRaza()));
                    cmbUsuarios.setSelection(obtienePosicionUsuario(listaBackMascotas.get(position - 1).getIdUsuario()));
                    idMascota = listaBackMascotas.get(position - 1).getIdMascota();
                } else {
                    deshabilitarCombos();
                    cmbRazas.setEnabled(false);
                    cmbUsuarios.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void deshabilitarCombos() {
        this.listaRazas = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();

        this.listaRazas.add("Seleccione Mascota");
        this.listaUsuarios.add("Seleccione Mascota");

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaRazas);
        this.cmbRazas.setAdapter(adaptador);

        adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaUsuarios);
        this.cmbUsuarios.setAdapter(adaptador);


    }

    private void habilitarCombos() {
        this.cmbUsuarios.setEnabled(true);
        this.cmbRazas.setEnabled(true);

        this.listaRazas = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.llenarComboRazas();
        this.llenarComboUsuarios();

        this.colocarListenerRaza();
        this.colocarListenerUsuario();
    }

    private void colocarListenerUsuario() {
        //Setting listener for the spinner
        this.cmbUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: Usuario: " + position);

                idUsuario = listaBackUsuarios.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void colocarListenerRaza() {
        //Setting listener for the spinner
        this.cmbRazas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: Raza: " + position);

                idRaza = listaBackRazas.get(position).getIdRaza();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private int obtienePosicionUsuario(Integer idUsuario) {
        int position = 0;
        for (int i = 0; i < listaBackUsuarios.size(); i++) {
            if (listaBackUsuarios.get(i).getId().equals(idUsuario)) {
                position = i;
                return position;
            }
        }
        return position;
    }

    private int obtienePosicionRaza(Integer idRaza) {
        int position = 0;
        for (int i = 0; i < listaBackRazas.size(); i++) {
            if (listaBackRazas.get(i).getIdRaza().equals(idRaza)) {
                position = i;
                return position;
            }
        }
        return position;
    }

    private void cargarCombos() {
        this.cargarMascotas();
        this.cargarUsuarios();
        this.cargarRazas();
    }

    private void cargarMascotas() {
        this.listaBackMascotas = new ArrayList<>();

        SQLiteDatabase db = this.conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(UtilidadesMascota.getSqlBuscarTodos(), null);

        while (cursor.moveToNext()) {
            Mascota mascota = new Mascota();
            mascota.setIdMascota(cursor.getInt(0));
            mascota.setIdRaza(cursor.getInt(1));
            mascota.setIdUsuario(cursor.getInt(2));
            mascota.setNombreMascota(cursor.getString(3));
            this.listaBackMascotas.add(mascota);
        }
        cursor.close();
        db.close();

        this.listaMascotas = new ArrayList<>();
        this.llenarComboMascotas();


    }

    private void llenarComboMascotas() {
        this.listaMascotas.add("Seleccione Mascota");
        for (int i = 0; i < this.listaBackMascotas.size(); i++) {
            this.listaMascotas.add(this.listaBackMascotas.get(i).getIdMascota()
                    + " - " + this.listaBackMascotas.get(i).getNombreMascota());
        }

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaMascotas);
        this.cmbMascotas.setAdapter(adaptador);
    }

    private void cargarUsuarios() {

        this.listaBackUsuarios = new ArrayList<>();

        SQLiteDatabase db = this.conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesUsuario.TABLA_USUARIO, null);

        while (cursor.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setTelefono(cursor.getString(2));


            this.listaBackUsuarios.add(usuario);
        }
        cursor.close();
        db.close();

        this.llenarComboUsuarios();

    }

    private void llenarComboUsuarios() {

        for (int i = 0; i < this.listaBackUsuarios.size(); i++) {
            this.listaUsuarios.add(this.listaBackUsuarios.get(i).getId()
                    + " - " + this.listaBackUsuarios.get(i).getNombre());
        }

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaUsuarios);
        this.cmbUsuarios.setAdapter(adaptador);
    }

    private void cargarRazas() {
        this.listaBackRazas = new ArrayList<>();
        SQLiteDatabase db = this.conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesRaza.TABLA_RAZA, null);

        while (cursor.moveToNext()) {
            Raza raza = new Raza();
            raza.setIdRaza(cursor.getInt(0));
            raza.setNombreRaza(cursor.getString(1));


            this.listaBackRazas.add(raza);
        }
        cursor.close();
        db.close();

        this.llenarComboRazas();

    }

    private void llenarComboRazas() {
        for (int i = 0; i < this.listaBackRazas.size(); i++) {
            this.listaRazas.add(this.listaBackRazas.get(i).getIdRaza()
                    + " - " + this.listaBackRazas.get(i).getNombreRaza());
        }

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaRazas);
        this.cmbRazas.setAdapter(adaptador);
    }

    private void iniciarComponentes() {
        this.txtNombre = findViewById(R.id.mascotaConsultaIdTxtNombre);
        this.cmbMascotas = findViewById(R.id.mascotaConsultaIdCmbMascotas);
        this.cmbRazas = findViewById(R.id.mascotaConsultaIdCmbRazas);
        this.cmbRazas.setEnabled(false);
        this.cmbUsuarios = findViewById(R.id.mascotaConsultaIdCmbUsuarios);
        this.cmbUsuarios.setEnabled(false);
        this.listaRazas = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.listaMascotas = new ArrayList<>();

        this.listaBackMascotas = new ArrayList<>();
        this.listaBackRazas = new ArrayList<>();
        this.listaBackUsuarios = new ArrayList<>();

        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

        this.idMascota = 0;
        this.idRaza = 0;
        this.idUsuario = 0;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mascotaConsultaIdBtnActualizar:
                this.actualizarMascota();
                break;
            case R.id.mascotaConsultaIdBtnEliminar:
                this.eliminarMascota();
                break;
        }
    }

    private void eliminarMascota() {
        if (this.cmbMascotas.getSelectedItem().toString().equals("Seleccione Mascota")){
            Toast.makeText(getApplicationContext(), "Seleccione una mascota", Toast.LENGTH_SHORT).show();
        }
        else{
            SQLiteDatabase db = this.conn.getWritableDatabase();
            String[] parametros = {this.idMascota.toString()};


            db.execSQL(UtilidadesMascota.getSqlEliminar(),parametros);
            Toast.makeText(getApplicationContext(), "Eliminado, con SQL: ", Toast.LENGTH_SHORT).show();
            db.close();
            this.txtNombre.setText("");
            this.iniciarComponentes();
            this.cargarCombos();
            this.deshabilitarCombos();
        }
    }

    private void actualizarMascota() {
        if (this.cmbMascotas.getSelectedItem().toString().equals("Seleccione Mascota") ||
                this.cmbUsuarios.getSelectedItem().toString().equals("Seleccione Mascota") ||
                this.cmbRazas.getSelectedItem().toString().equals("Seleccione Mascota")) {
            Toast.makeText(getApplicationContext(), "Seleccione una mascota", Toast.LENGTH_SHORT).show();
        } else if (this.txtNombre.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese nombre de mascota", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase db = this.conn.getWritableDatabase();
            String[] parametros = {this.idUsuario.toString(), this.idRaza.toString(),
                    this.txtNombre.getText().toString(), this.idMascota.toString()};

            db.execSQL(UtilidadesMascota.getSqlActualizar(), parametros);
            Toast.makeText(getApplicationContext(), "Actualizado, con SQL: ", Toast.LENGTH_SHORT).show();
            db.close();
            this.txtNombre.setText("");
            this.iniciarComponentes();
            this.cargarCombos();
            this.deshabilitarCombos();

        }
    }
}
