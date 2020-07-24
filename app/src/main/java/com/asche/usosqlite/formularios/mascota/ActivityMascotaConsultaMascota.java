package com.asche.usosqlite.formularios.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Mascota;
import com.asche.usosqlite.entidades.Raza;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.utilidades.Utilidades;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_consulta_mascota);
        this.iniciarComponentes();
        this.cargarCombos();
    }

    private void cargarCombos() {
    }

    private void iniciarComponentes() {
        this.txtNombre = findViewById(R.id.mascotaConsultaIdTxtNombre);
        this.cmbMascotas = findViewById(R.id.mascotaConsultaIdCmbMascotas);
        this.cmbRazas = findViewById(R.id.mascotaConsultaIdCmbRazas);
        this.cmbUsuarios = findViewById(R.id.mascotaConsultaIdCmbUsuarios);

        this.listaRazas = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.listaMascotas = new ArrayList<>();

        this.listaBackMascotas = new ArrayList<>();
        this.listaBackRazas = new ArrayList<>();
        this.listaBackUsuarios = new ArrayList<>();

        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

    }

    public void onClick(View view) {
    }
}