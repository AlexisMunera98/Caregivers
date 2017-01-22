package com.example.gabriel.caregivers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabriel.caregivers.DataBase.DataBaseManager;
import com.example.gabriel.caregivers.R;

public class IngresarCuidadorActivity extends AppCompatActivity {
    private Button btnAgregarCuidador;
    private EditText edtConfirmarClave;
    private EditText edtClave;
    private EditText edtApellido;
    private EditText edtNombre;
    private EditText edtCedula;
    private DataBaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_cuidador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DataBaseManager(getApplicationContext());

        btnAgregarCuidador = (Button) findViewById(R.id.btnAgregarCuidador);
        edtConfirmarClave = (EditText) findViewById(R.id.edtConfirmarClave);
        edtClave = (EditText) findViewById(R.id.edtClave);
        edtApellido = (EditText) findViewById(R.id.edtApellido);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtCedula = (EditText) findViewById(R.id.edtCedula);

        btnAgregarCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clave = edtClave.getText().toString();
                String nombre = edtNombre.getText().toString();
                String apellido = edtApellido.getText().toString();
                String cedula = edtCedula.getText().toString();
                String confirmarClave = edtConfirmarClave.getText().toString();
                if (clave.equals(confirmarClave)) {
                    dbManager.insertarCuidador(cedula, nombre, apellido, clave, null);
                    Toast.makeText(getApplicationContext(), "Registrado con exito", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Contraseñas incorrectas", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

}
