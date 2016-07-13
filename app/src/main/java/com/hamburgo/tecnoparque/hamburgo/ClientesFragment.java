package com.hamburgo.tecnoparque.hamburgo;


import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientesFragment extends Fragment {

    ListView listItemClientes;
    EditText edtBusqueda;
    Context cnt;
    ArrayList<ClienteDTO> datos;
    AdaptadorListview adaptador;

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
        edtBusqueda = (EditText)Vista.findViewById(R.id.editText);

        FloatingActionButton fab = (FloatingActionButton) Vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                NuevoClienteDialogFragment dialogFragment = new NuevoClienteDialogFragment(new NuevoClienteDialogFragment.ClienteReturn() {
                    @Override
                    public void processFinish(ClienteDTO output) {
                        datos.add(output);
                        adaptador = new AdaptadorListview(cnt,R.layout.layout_adaptador_listview,datos);
                        listItemClientes.setAdapter(adaptador);
                        edtBusqueda.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                String text = edtBusqueda.getText().toString();
                                adaptador.filter(text);
                            }
                        });
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
