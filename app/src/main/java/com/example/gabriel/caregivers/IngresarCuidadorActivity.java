package com.example.gabriel.caregivers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IngresarCuidadorActivity extends AppCompatActivity {
    private Button btnAgragarCuidador;
    private EditText edtConfirmarClave;
    private EditText edtClave;
    private EditText edtApellido;
    private EditText edtNombre;
    private EditText edtCedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_cuidador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnAgragarCuidador = (Button) findViewById(R.id.btnAgragarCuidador);
        edtConfirmarClave = (EditText) findViewById(R.id.edtConfirmarClave);
        edtClave = (EditText) findViewById(R.id.edtClave);
        edtApellido = (EditText) findViewById(R.id.edtApellido);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtCedula = (EditText) findViewById(R.id.edtCedula);

        btnAgragarCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
