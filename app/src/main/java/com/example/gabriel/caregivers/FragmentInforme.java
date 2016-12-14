package com.example.gabriel.caregivers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gabriel on 11/12/2016.
 */

public class FragmentInforme extends Fragment {

    private TextView txt;
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
        Bundle b = getArguments();
        String datos = b.getString("datos");
        txt.setText(datos);
        return v;
    }
}
