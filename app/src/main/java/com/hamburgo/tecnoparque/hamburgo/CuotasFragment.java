package com.hamburgo.tecnoparque.hamburgo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCartera;
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCuotas;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.CuotasDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CuotasFragment extends DialogFragment {

    ListView listViewCuotas;
    DataBaseManager manager;
    AdaptadorListviewCuotas adaptador;
    ArrayList<CuotasDTO> datos;
    Context cnt;
    Integer NumeroCuota;


    public CuotasFragment(Integer NumeroCuota) {
        this.NumeroCuota=NumeroCuota;
    }

//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View vista = inflater.inflate(R.layout.fragment_cuotas, container, false);
//
//        cnt = getActivity().getApplicationContext();
//        manager = new DataBaseManager(cnt);
//        listViewCuotas = (ListView)vista.findViewById(R.id.listView);
//        datos = manager.getCuotasVenta(NumeroCuota);
//
//        adaptador = new AdaptadorListviewCuotas(cnt,R.layout.layout_adaptador_cartera,datos);
//        listViewCuotas.setAdapter(adaptador);
//        return vista;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater i = getActivity().getLayoutInflater();
        View vista = i.inflate(R.layout.fragment_cuotas,null);

        cnt = getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        listViewCuotas = (ListView)vista.findViewById(R.id.listView);
        datos = manager.getCuotasVenta(NumeroCuota);

        adaptador = new AdaptadorListviewCuotas(cnt,R.layout.layout_adaptador_cartera,datos);
        listViewCuotas.setAdapter(adaptador);


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setView(vista);
        return builder.create();
    }
}
