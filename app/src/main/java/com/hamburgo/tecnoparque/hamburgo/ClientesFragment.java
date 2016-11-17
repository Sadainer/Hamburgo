package com.hamburgo.tecnoparque.hamburgo;


import android.app.*;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewClientes;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.sql.SQLClientInfoException;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientesFragment extends Fragment {

    ListView listItemClientes;
    Context cnt;
    ArrayList<ClienteDTO> datos;
    AdaptadorListviewClientes adaptador;
    private DataBaseManager manager;
    ProgressDialog progressDialog;

    private DatabaseReference mDatabase ;

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


        mDatabase = FirebaseDatabase.getInstance().getReference();

        datos= new ArrayList<ClienteDTO>();
        cnt= getActivity();
        manager = new DataBaseManager(cnt);
        adaptador = new AdaptadorListviewClientes(cnt, R.layout.layout_adaptador_listview, datos);

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

        progressDialog = new ProgressDialog(cnt,R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

//        mDatabase.child("Clientes").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                datos.clear();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    ClienteDTO cm = postSnapshot.getValue(ClienteDTO.class);
//                    datos.add(cm);
//                }
//                if (datos.size() > 0) {
//                    adaptador = new AdaptadorListviewClientes(cnt, R.layout.layout_adaptador_listview, datos);
//                    listItemClientes.setAdapter(adaptador);
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        datos = manager.getListaClientes();
        if (datos.size() > 0) {
            adaptador = new AdaptadorListviewClientes(cnt, R.layout.layout_adaptador_listview, datos);
            listItemClientes.setAdapter(adaptador);
        }
        progressDialog.dismiss();
    }

    private void MostrarDialog(ClienteDTO cliente){
        FragmentManager fm = getFragmentManager();
        NuevoClienteDialogFragment dialogFragment = new NuevoClienteDialogFragment(cnt,cliente,new NuevoClienteDialogFragment.ClienteReturn() {
            @Override
            public void processFinish(ClienteDTO cliente) {
                ActualizarListaClientes();
            }
        });
        dialogFragment.show(fm, "Sample Fragment");
    }


}
