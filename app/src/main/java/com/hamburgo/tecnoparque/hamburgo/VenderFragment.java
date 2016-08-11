package com.hamburgo.tecnoparque.hamburgo;


import android.app.FragmentManager;
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
import android.widget.ListView;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewClientes;
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewProductos;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VenderFragment extends Fragment {

    AutoCompleteTextView AutCompleteClientes,AutCompleteProductos ;
    ListView ListProductos;
    TextView txtTotal;
    Long Total = (long)0;


    Context cnt;
    List<ClienteDTO> datos;
    AdaptadorListviewClientes adaptador;


    AdaptadorListviewProductos adaptadorProductos;
    AdaptadorListviewProductos adaptadorProductosFinal;

    List<ProductoDTO> datosProductos;
    List<ProductoDTO> datosProductosFinal;

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
        datosProductos= new ArrayList<ProductoDTO>();
        datosProductosFinal= new ArrayList<ProductoDTO>();

        cnt= getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);


        txtTotal = (TextView)vista.findViewById(R.id.txtTotal);
        AutCompleteClientes = (AutoCompleteTextView)vista.findViewById(R.id.autoCompleteClientes);
        AutCompleteProductos = (AutoCompleteTextView)vista.findViewById(R.id.autoCompleteproductos);
        ListProductos = (ListView)vista.findViewById(R.id.listViewProductos);

        adaptadorProductosFinal = new AdaptadorListviewProductos(cnt, R.layout.layout_adaptador_productos, datosProductosFinal);
        ListProductos.setAdapter(adaptadorProductosFinal);

        LlenarListaClientes();
        LlenarListaProductos();



        AutCompleteClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView cedula = (TextView)view.findViewById(R.id.txtCedula);
                AutCompleteClientes.setText(cedula.getText().toString());
            }
        });

        AutCompleteProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ProductoDTO p = datosProductos.get(position);
                if (!ProductoContains(p.getNombre())){
                    MostrarDialog(p);
                }
                AutCompleteProductos.setText("");

            }
        });

        return vista;
    }

    private void MostrarDialog(ProductoDTO producto){
        FragmentManager fm = getFragmentManager();
        ProductoDetalleFragment dialogFragment = new ProductoDetalleFragment(producto, new ProductoDetalleFragment.ProductoReturn() {
            @Override
            public void processFinish(ProductoDTO p) {
                datosProductosFinal.add(p);
                Total += p.getPrecio();
                txtTotal.setText("Total = " + String.valueOf(Total));
                adaptadorProductosFinal.notifyDataSetChanged();
            }
        });
        dialogFragment.show(fm, null);
    }


    private void LlenarListaClientes () {
        datos.clear();
        datos = manager.getListaClientes();
        if (datos.size() > 0) {
            adaptador = new AdaptadorListviewClientes(cnt, R.layout.layout_adaptador_listview, datos);
            AutCompleteClientes.setThreshold(3);
            AutCompleteClientes.setAdapter(adaptador);
        }
    }
    private void LlenarListaProductos() {
        datosProductos.clear();
        datosProductos = manager.getListaProductos();
        if (datosProductos.size() > 0) {
            adaptadorProductos = new AdaptadorListviewProductos(cnt, R.layout.layout_adaptador_productos, datosProductos);
            AutCompleteProductos.setThreshold(3);
            AutCompleteProductos.setAdapter(adaptadorProductos);
        }
    }
    private Boolean ProductoContains(String Nombre){

        for (ProductoDTO p :datosProductosFinal) {
            if (p.getNombre().equals(Nombre)){
                return true;
            }
        }
        return false;
    }

}
