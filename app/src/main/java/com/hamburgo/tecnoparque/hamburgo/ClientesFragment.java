package com.hamburgo.tecnoparque.hamburgo;


import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientesFragment extends Fragment {

    ListView listItemClientes;
    Context cnt;
    ArrayList<ClienteDTO> datos;

    public ClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Vista = inflater.inflate(R.layout.fragment_clientes, container, false);

        datos= new ArrayList<ClienteDTO>();
        cnt= getActivity().getApplicationContext();
        listItemClientes = (ListView)Vista.findViewById(R.id.listView);


        FloatingActionButton fab = (FloatingActionButton) Vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                NuevoClienteDialogFragment dialogFragment = new NuevoClienteDialogFragment(new NuevoClienteDialogFragment.ClienteReturn() {
                    @Override
                    public void processFinish(ClienteDTO output) {
                        datos.add(output);
                        AdaptadorListview adaptador =
                                new AdaptadorListview(cnt,R.layout.layout_adaptador_listview,datos);
                        listItemClientes.setAdapter(adaptador);
                    }
                });
                dialogFragment.show(fm, "Sample Fragment");
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        return Vista;
    }


}
