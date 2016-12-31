package com.example.gabriel.caregivers;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by gabriel on 11/12/2016.
 */

public class FragmentPaciente extends Fragment {

    private ListView listaPacientes;

    private SimpleCursorAdapter adapter;

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
        ArrayList<String> items = new ArrayList<>();
        items.add("Opcion1");
        items.add("Opcion2");
        items.add("Opcion3");
        items.add("Opcion4");
        items.add("Opcion5");
        DataBaseManager dbManager = ((MainActivity) getActivity()).dbManager;
        //adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);
        String[] from = new String[]{"_id",DataBaseManager.CN_APELLIDO_CUIDADOR};
        int[] to = new int[]{R.id.txtNombrePaciente, R.id.txtApellidoPaciente};
        Cursor c = dbManager.cargarCuidadores();
        adapter = new SimpleCursorAdapter(getContext(), R.layout.adapter_lista_pacientes, c, from, to, 0);

        listaPacientes = (ListView) v.findViewById(R.id.listaPacientes);
        listaPacientes.setAdapter(adapter);
        return v;
    }

    public SimpleCursorAdapter getAdapter() {
        return adapter;
    }

}
