package com.example.gabriel.caregivers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabriel.caregivers.DataBase.DataBaseManager;
import com.example.gabriel.caregivers.R;

public class loginActivity extends AppCompatActivity {

    private Button btnInicarSesion;
    private DataBaseManager dbManager;
    private Button btnRegistrarse;
    private EditText edtCedula;
    private EditText edtClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        importarComponentes();

        dbManager = new DataBaseManager(getApplicationContext());

        declararAccionComponentes();
    }

    public static final String CEDULA = "CEDULA";

    private void declararAccionComponentes() {
        btnInicarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbManager.serLoginCorrecto(edtCedula.getText().toString(), edtClave.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(CEDULA, edtCedula.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();

                }


            }
        });
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IngresarCuidadorActivity.class);
                startActivity(intent);

            }
        });
    }

    private void importarComponentes() {
        btnInicarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        edtCedula = (EditText) findViewById(R.id.edtCedula);
        edtClave = (EditText) findViewById(R.id.edtClave);

    }

}
