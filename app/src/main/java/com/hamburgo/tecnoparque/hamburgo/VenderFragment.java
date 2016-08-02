package com.hamburgo.tecnoparque.hamburgo;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewClientes;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class VenderFragment extends Fragment {

    AutoCompleteTextView textView;
    Context cnt;
    List<ClienteDTO> datos;
    ArrayList<ClienteDTO> datosBackup;
    AdaptadorListviewClientes adaptador;
    private DataBaseManager manager;

    public VenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista =  inflater.inflate(R.layout.fragment_vender, container, false);

        datos= new ArrayList<ClienteDTO>();
        cnt= getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        datosBackup = new ArrayList<>();


        textView = (AutoCompleteTextView)vista.findViewById(R.id.autoCompleteTextView);
        LlenarLista();
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView cedula = (TextView)view.findViewById(R.id.txtCedula);
                textView.setText(cedula.getText().toString());
            }
        });

        return vista;
    }

    private void LlenarLista () {
        datos.clear();
        datos = manager.getListaClientes();
        datosBackup.addAll(datos);
        if (datos.size() > 0) {
            adaptador = new AdaptadorListviewClientes(cnt, R.layout.layout_adaptador_listview, datos);
            textView.setThreshold(3);
            textView.setAdapter(adaptador);
        }
    }

}
