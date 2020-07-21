package com.asche.usosqlite.formularios.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Usuario;

public class ActivityUsuarioDetalle extends AppCompatActivity {

    TextView txtId, txtNombre, txtTelefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle);

        this.iniciarComponentes();
        this.recivirDatos();
    }

    private void recivirDatos() {
        Bundle objetoRecibido = getIntent().getExtras();
        Usuario usuario = null;
        if(objetoRecibido != null){
            usuario = (Usuario)objetoRecibido.getSerializable("usuario");
        }
        this.modificarCampos(usuario);

    }

    private void modificarCampos(Usuario usuario) {
        this.txtId.setText(usuario.getId().toString());
        this.txtTelefono.setText(usuario.getTelefono());
        this.txtNombre.setText(usuario.getNombre());

    }

    private void iniciarComponentes() {

        this.txtId = findViewById(R.id.usuarioDetalleTxtId);
        this.txtNombre = findViewById(R.id.usuarioDetalleTxtNombre);
        this.txtTelefono = findViewById(R.id.usuarioDetalleTxtTelefono);


    }
}