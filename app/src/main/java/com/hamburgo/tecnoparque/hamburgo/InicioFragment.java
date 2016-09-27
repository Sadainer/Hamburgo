package com.hamburgo.tecnoparque.hamburgo;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCartera;
import com.hamburgo.tecnoparque.hamburgo.ClasesAsincronas.GetAsyncrona;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    Spinner spiLista ;
    Context cnt;
    DataBaseManager manager;
    ListView listViewRecaudo;
    AdaptadorListviewCartera adaptador;
    ArrayList<ClienteDTO> datos;
    String URI = "http://190.109.185.138:8024/api/arboles";

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        cnt= getActivity().getApplicationContext();
        spiLista = (Spinner)vista.findViewById(R.id.spiLista);
        manager = new DataBaseManager(cnt);
        listViewRecaudo = (ListView)vista.findViewById(R.id.listView);

        final String[] datos =new String[]{"Recaudo mensual","Recaudo quincenal","Recaudo diario"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(cnt,R.layout.layout_adaptador_spinner,R.id.textItem, datos);
        spiLista.setAdapter(adaptador);


        datos = manager.getCartera();
//

        adaptador = new AdaptadorListviewCartera(cnt,R.layout.layout_adaptador_cartera,datos);
        listViewCartera.setAdapter(adaptador);

//        cnt = getActivity();
//        boton = (Button)vista.findViewById(R.id.button);
//        boton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GetAsyncrona getAsyncrona = (GetAsyncrona) new GetAsyncrona(cnt, new GetAsyncrona.AsyncResponse() {
//                    @Override
//                    public void processFinish(String output) {
//
//                        if (!output.equals("")){
//                            Toast.makeText(cnt,output.toString(),Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(cnt,"Respuesta no contiene datos",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).execute(URI);
//
//            }
//        });

        return vista;
    }



}
