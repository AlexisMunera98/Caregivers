package com.example.gabriel.caregivers.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gabriel.caregivers.DataBase.DataBaseManager;
import com.example.gabriel.caregivers.Activities.MainActivity;
import com.example.gabriel.caregivers.R;

import java.util.ArrayList;

/**
 * Created by gabriel on 11/12/2016.
 */

public class FragmentPaciente extends Fragment {

    private ListView listaPacientes;
    private SimpleCursorAdapter adapter;
    DataBaseManager dbManager;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_paciente, container, false);


        dbManager = ((MainActivity) getActivity()).getDbManager();
        //adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, items);


        String[] from = new String[]{"_id", DataBaseManager.CN_APELLIDO_CUIDADOR, DataBaseManager.CN_CC_CUIDADOR};
        int[] to = new int[]{R.id.txtNombrePaciente, R.id.txtApellidoPaciente, R.id.edtCedulaPaciente};
        Cursor c = dbManager.cargarCuidadores();
        adapter = new SimpleCursorAdapter(getContext(), R.layout.adapter_lista_pacientes, c, from, to, 0);
        listaPacientes = (ListView) v.findViewById(R.id.listaPacientes);
        listaPacientes.setAdapter(adapter);
        return v;
    }

    public void actualizarLista(String nombrePaciente) {
        Cursor c;
        if (nombrePaciente.isEmpty()) {
            c = dbManager.cargarCuidadores();
        } else {
            c = dbManager.cargarCuidadores(nombrePaciente);
        }
        adapter.changeCursor(c);


    }

    public SimpleCursorAdapter getAdapter() {
        return adapter;
    }

}
