package com.asche.usosqlite.formularios.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asche.usosqlite.R;
import com.asche.usosqlite.formularios.usuario.ActivityUsuarioPrincipal;

public class ActivityMascotaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_principal);
    }

    public void onClick(View view) {
        Intent myIntent;
        switch(view.getId()){
            case R.id.mascotaPrincipalBtnRegistrar:
                myIntent = new Intent(getApplicationContext(), ActivityMascotaRegistro.class);
                startActivity(myIntent);
                break;

            case R.id.mascotaPrincipalBtnConsultarListView:
                myIntent = new Intent(getApplicationContext(), ActivityMascotaConsultaLista.class);
                startActivity(myIntent);
                break;

            case R.id.mascotaPrincipalBtnConsultarSpinner:
                myIntent = new Intent(getApplicationContext(), ActivityMascotaConsultaCombo.class);
                startActivity(myIntent);
                break;
        }
    }
}