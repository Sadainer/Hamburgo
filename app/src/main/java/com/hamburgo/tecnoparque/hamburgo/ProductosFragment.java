package com.hamburgo.tecnoparque.hamburgo;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
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
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewProductos;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosFragment extends Fragment {

    ListView listItemProductos;
    Context cnt;
    ArrayList<ProductoDTO> datos;
    AdaptadorListviewProductos adaptador;
    private DataBaseManager manager;

    public ProductosFragment() {
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
        View Vista = inflater.inflate(R.layout.fragment_productos, container, false);

        datos= new ArrayList<ProductoDTO>();
        cnt= getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        adaptador = new AdaptadorListviewProductos(cnt, R.layout.layout_adaptador_productos, datos);

        listItemProductos = (ListView)Vista.findViewById(R.id.listView);
        listItemProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView Nombre = (TextView)view.findViewById(R.id.txtNombre);
                ProductoDTO producto = manager.getProducto(Nombre.getText().toString());
                MostrarDialog(producto);
            }
        });

        ActualizarListaProductos();

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

    private void ActualizarListaProductos () {
        datos.clear();
        datos = manager.getListaProductos();
        if (datos.size() > 0) {
            adaptador = new AdaptadorListviewProductos(cnt, R.layout.layout_adaptador_productos, datos);
            listItemProductos.setAdapter(adaptador);
        }
    }

    private void MostrarDialog(ProductoDTO producto){
        FragmentManager fm = getFragmentManager();
        NuevoProductoDialogFragment dialogFragment = new NuevoProductoDialogFragment(cnt,producto, new NuevoProductoDialogFragment.ProductoReturn() {
            @Override
            public void processFinish() {
                ActualizarListaProductos();
            }
        });
        dialogFragment.show(fm, "Sample Fragment");
    }

}
