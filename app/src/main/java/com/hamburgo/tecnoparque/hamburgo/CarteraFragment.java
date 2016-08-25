package com.hamburgo.tecnoparque.hamburgo;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCartera;
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewVentas;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarteraFragment extends Fragment {

    ListView listViewCartera;
    DataBaseManager manager;
    AdaptadorListviewCartera adaptador;
    ArrayList<ClienteDTO> datos;
    Context cnt;

    public CarteraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cartera, container, false);

        cnt = getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        listViewCartera = (ListView)vista.findViewById(R.id.listView);
        datos = manager.getCartera();
        adaptador = new AdaptadorListviewCartera(cnt,R.layout.layout_adaptador_cartera,datos);
        listViewCartera.setAdapter(adaptador);
        return vista;
    }

}
