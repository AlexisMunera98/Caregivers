package com.example.gabriel.caregivers.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gabriel.caregivers.R;

/**
 * Created by gabriel on 11/12/2016.
 */

public class FragmentInforme extends Fragment {

    private TextView txt;
    private Button btnPrueba;
    public FragmentInforme() {}

    public static FragmentInforme newInstance(Bundle arg) {
        FragmentInforme fi = new FragmentInforme();
        if (arg != null) {
            fi.setArguments(arg);
        }
        return fi;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_informe, container, false);
        txt = (TextView) v.findViewById(R.id.textView6);
        btnPrueba = (Button) v.findViewById(R.id.btnPrueba);
        Bundle b = getArguments();
        String datos = b.getString("datos");
        txt.setText(datos);

        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Esto es una prueba para Git", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
