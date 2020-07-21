package com.asche.usosqlite.formularios.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.R;

public class ActivityUsuarioPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_principal);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "db_usuarios", null, 1);
    }

    public void onClick(View view) {
        Intent myIntent;
        switch (view.getId()) {
            case R.id.usuarioPrincipalBtnRegistrar:
                myIntent = new Intent(getApplicationContext(), ActivityUsuarioRegistro.class);
                startActivity(myIntent);
                break;

            case R.id.usuarioPrincipalBtnConsultar:
                myIntent = new Intent(getApplicationContext(), ActivityUsuarioConsultaId.class);
                startActivity(myIntent);
                break;

            case R.id.usuarioPrincipalBtnConsultarSpinner:
                myIntent = new Intent(getApplicationContext(), ActivityUsuarioConsultaCombo.class);
                startActivity(myIntent);

                break;
            case R.id.usuarioPrincipalBtnConsultarListView:
                myIntent = new Intent(getApplicationContext(), ActivityUsuarioConsultaLista.class);
                startActivity(myIntent);

                break;

        }
    }
}