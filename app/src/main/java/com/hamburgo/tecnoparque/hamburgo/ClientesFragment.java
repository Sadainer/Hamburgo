package com.hamburgo.tecnoparque.hamburgo;


import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewClientes;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientesFragment extends Fragment {

    ListView listItemClientes;
    Context cnt;
    ArrayList<ClienteDTO> datos;
    AdaptadorListviewClientes adaptador;
    private DataBaseManager manager;

    public ClientesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.principal, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptador.filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Vista = inflater.inflate(R.layout.fragment_clientes, container, false);

        datos= new ArrayList<ClienteDTO>();
        cnt= getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        listItemClientes = (ListView)Vista.findViewById(R.id.listView);
        listItemClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView cedula = (TextView)view.findViewById(R.id.txtCedula);
                Log.e("Sadainer", cedula.getText().toString());
                ClienteDTO cliente = manager.getUsuario(cedula.getText().toString());
                MostrarDialog(cliente);
            }
        });

        ActualizarListaClientes();
        FloatingActionButton fab = (FloatingActionButton) Vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MostrarDialog(null);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        return Vista;
    }

    private void ActualizarListaClientes () {
        datos.clear();
        datos = manager.getListaClientes();
        if (datos.size() > 0) {
            adaptador = new AdaptadorListviewClientes(cnt, R.layout.layout_adaptador_listview, datos);
            listItemClientes.setAdapter(adaptador);
        }
    }

    private void MostrarDialog(ClienteDTO cliente){
        FragmentManager fm = getFragmentManager();
        NuevoClienteDialogFragment dialogFragment = new NuevoClienteDialogFragment(cnt,cliente,new NuevoClienteDialogFragment.ClienteReturn() {
            @Override
            public void processFinish() {
                ActualizarListaClientes();
            }
        });
        dialogFragment.show(fm, "Sample Fragment");
    }


}
