package com.hamburgo.tecnoparque.hamburgo;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewVentas;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VentasFragment extends Fragment {

    ListView listViewVentas;
    DataBaseManager manager;
    AdaptadorListviewVentas adaptador;
    ArrayList<VentaDTO> datos;
    Context cnt;

    public VentasFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_ventas, container, false);

        cnt = getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        listViewVentas = (ListView)vista.findViewById(R.id.listView);
        datos = manager.getListadoVentas();
        adaptador = new AdaptadorListviewVentas(cnt,R.layout.layout_adaptador_ventas,datos);
        listViewVentas.setAdapter(adaptador);



        FloatingActionButton fab = (FloatingActionButton) vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmento= new NuevaVentaFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragmento)
                        .commit();
            }
        });

        return vista;
    }

}
