package com.hamburgo.tecnoparque.hamburgo;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosFragment extends Fragment {

    Context cnt;
    public ProductosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Vista = inflater.inflate(R.layout.fragment_productos, container, false);
        cnt= getActivity().getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) Vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


        return Vista;
    }

//    private void ActualizarListaClientes () {
//        datos.clear();
//        datos = manager.getListaClientes();
//        if (datos.size() > 0) {
//            adaptador = new AdaptadorListview(cnt, R.layout.layout_adaptador_listview, datos);
//            listItemClientes.setAdapter(adaptador);
//        }
//    }

    private void MostrarDialog(){
        FragmentManager fm = getFragmentManager();
        NuevoProductoDialogFragment dialogFragment = new NuevoProductoDialogFragment(cnt, new NuevoProductoDialogFragment.ProductoReturn() {
            @Override
            public void processFinish() {

            }
        });
        dialogFragment.show(fm, "Sample Fragment");
    }

}
