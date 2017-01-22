package com.example.gabriel.caregivers.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gabriel.caregivers.R;

/**
 * Created by gabriel on 11/12/2016.
 */

public class FragmentEnfermedad extends Fragment {

    private TextView txt;
    public FragmentEnfermedad() {
    }

    public static FragmentEnfermedad newInstance(Bundle arg) {
        FragmentEnfermedad fe = new FragmentEnfermedad();
        if (arg != null) {
            fe.setArguments(arg);
        }
        return fe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_enfermedad, container, false);
        txt = (TextView) v.findViewById(R.id.textView5);
        Bundle b = getArguments();
        String datos = b.getString("datos");
        txt.setText(datos);
        return v;
    }
}
