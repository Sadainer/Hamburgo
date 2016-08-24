package com.hamburgo.tecnoparque.hamburgo;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewVentas;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarteraFragment extends Fragment {

    ListView listViewCartera;
    DataBaseManager manager;
    AdaptadorListviewVentas adaptador;
    ArrayList<VentaDTO> datos;
    Context cnt;

    public CarteraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_cartera, menu);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_cartera, container, false);

        cnt = getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        listViewCartera = (ListView)vista.findViewById(R.id.listView);
        datos = manager.getListadoVentas();
        adaptador = new AdaptadorListviewVentas(cnt,R.layout.layout_adaptador_cartera,datos);
        listViewCartera.setAdapter(adaptador);
        registerForContextMenu(listViewCartera);

        return vista;
    }

}
