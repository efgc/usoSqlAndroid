package com.asche.usosqlite.formularios.raza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asche.usosqlite.R;

public class ActivityRazaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raza_principal);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.razaPrincipalBtnRegistrar:
                intent = new Intent(getApplicationContext(), ActivityRazaRegistro.class);
                startActivity(intent);
                break;

            case R.id.razaPrincipalBtnConsultarListView:
                intent = new Intent(getApplicationContext(), ActivityRazaConsultaLista.class);
                startActivity(intent);
                break;

            case R.id.razaPrincipalBtnConsultarSpinner:
                intent = new Intent(getApplicationContext(), ActivityRazaConsultaCombo.class);
                startActivity(intent);
                break;

            case R.id.razaPrincipalBtnConsultarId:
                intent = new Intent(getApplicationContext(), ActivityConsultaRaza.class);
                startActivity(intent);
                break;

        }
    }
}