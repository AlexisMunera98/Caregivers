package com.example.gabriel.caregivers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by gabriel on 11/12/2016.
 */

public class FragmentPaciente extends Fragment {

    private TextView txt;


    public FragmentPaciente() {
    }

    public static FragmentPaciente newInstance(Bundle arg) {
        FragmentPaciente fp = new FragmentPaciente();
        if (arg != null) {
            fp.setArguments(arg);
        }
        return fp;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_paciente, container, false);
        txt = (TextView) v.findViewById(R.id.textView3);
        Bundle b = getArguments();
        String datos = b.getString("datos");
        txt.setText(datos);
        return v;
    }
}
