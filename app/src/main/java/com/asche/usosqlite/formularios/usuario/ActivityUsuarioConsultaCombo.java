package com.asche.usosqlite.formularios.usuario;

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

import com.asche.usosqlite.utilidades.ConexionSQLiteHelper;
import com.asche.usosqlite.R;
import com.asche.usosqlite.entidades.Usuario;
import com.asche.usosqlite.utilidades.Utilidades;
import com.asche.usosqlite.utilidades.UtilidadesUsuario;

import java.util.ArrayList;

public class ActivityUsuarioConsultaCombo extends AppCompatActivity {

    Spinner comboUsuarios;
    TextView txtId, txtNombre, txtTelefono;
    ArrayList<String> listaUsuarios;
    ArrayList<Usuario> listaUsuariosBack;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_consulta_combo);


        this.iniciarComponentes();



    }


    private void iniciarComponentes() {
        this.comboUsuarios = findViewById(R.id.usuarioConsultaComboSpinnerUsuarios);
        this.txtId = findViewById(R.id.usuarioConsultaComboLblId);
        this.txtNombre = findViewById(R.id.usuarioConsultaComboLblNombre);
        this.txtTelefono = findViewById(R.id.usuarioConsultaComboLblTelefono);
        this.listaUsuarios = new ArrayList<>();
        this.listaUsuariosBack = new ArrayList<>();
        this.conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BDD_NAME, null, Utilidades.BDD_VERSION);

        this.consultarListaPersonas();
        this.obtenerLista();
        //Setting listener for the spinner
        this.comboUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position: "+position);
                if (position != 0) {
                    txtId.setText(listaUsuariosBack.get(position - 1).getId().toString());
                    txtNombre.setText(listaUsuariosBack.get(position - 1).getNombre());
                    txtTelefono.setText(listaUsuariosBack.get(position - 1).getTelefono());
                }else{
                    txtId.setText("-----");
                    txtNombre.setText("-----");
                    txtTelefono.setText("-----");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = this.conn.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesUsuario.TABLA_USUARIO, null);

        while (cursor.moveToNext()) {
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setTelefono(cursor.getString(2));

            Log.i("id", usuario.getId().toString());
            Log.i("nombre", usuario.getNombre());
            Log.i("telefono", usuario.getTelefono());

            this.listaUsuariosBack.add(usuario);
        }
        cursor.close();
        db.close();

    }

    private void obtenerLista() {
        this.listaUsuarios = new ArrayList<String>();
        this.listaUsuarios.add("Seleccione");
        for (int i = 0; i < this.listaUsuariosBack.size(); i++) {
            this.listaUsuarios.add(this.listaUsuariosBack.get(i).getId()
                    + " - " + this.listaUsuariosBack.get(i).getNombre());
        }

        System.out.println("Lista usuarios: "+listaUsuarios.size());
        System.out.println("Lista usuarios back: "+listaUsuariosBack.size());
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_item, this.listaUsuarios);
        this.comboUsuarios.setAdapter(adaptador);

    }


}