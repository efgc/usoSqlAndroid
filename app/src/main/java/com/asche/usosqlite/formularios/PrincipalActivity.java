package com.asche.usosqlite.formularios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.asche.usosqlite.R;
import com.asche.usosqlite.formularios.raza.ActivityRazaPrincipal;
import com.asche.usosqlite.formularios.usuario.ActivityUsuarioPrincipal;

public class PrincipalActivity extends AppCompatActivity {

    Button btnRaza, btnUsuario, btnMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        this.iniciarComponentes();
    }

    private void iniciarComponentes() {
        this.btnRaza = findViewById(R.id.principalBtnRaza);
        this.btnUsuario = findViewById(R.id.principalBtnUsuario);
        this.btnMascota = findViewById(R.id.principalBtnMascota);
    }

    public void onClick(View view) {
        Intent myIntent;
        switch(view.getId()){
            case R.id.principalBtnRaza:
                myIntent = new Intent(getApplicationContext(), ActivityRazaPrincipal.class);
                startActivity(myIntent);
                break;

            case R.id.principalBtnUsuario:
                myIntent = new Intent(getApplicationContext(), ActivityUsuarioPrincipal.class);
                startActivity(myIntent);
                break;

//            case R.id.principalBtnMascota:
//                myIntent = new Intent(getApplicationContext(),PrincipalMascota.class);
//                startActivity(myIntent);
//                break;
        }
    }
}