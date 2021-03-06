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
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewProductos;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.CuotasDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.DetalleVentaDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CuotasVentaFragment extends DialogFragment {

    ListView listViewCuotas;
    ListView listViewDetalle;
    DataBaseManager manager;
    AdaptadorListviewCuotas adaptador;
    AdaptadorListviewProductos adaptadorProductos;
    ArrayList<CuotasDTO> datos;
    ArrayList<ProductoDTO> datosVenta;
    Context cnt;
    Integer NumeroVenta;


    public CuotasVentaFragment(Integer Numero_Venta) {
        this.NumeroVenta=Numero_Venta;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater i = getActivity().getLayoutInflater();
        View vista = i.inflate(R.layout.fragment_cuotas,null);

        cnt = getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);

        listViewCuotas = (ListView)vista.findViewById(R.id.listView);
        datos = manager.getCuotasVenta(NumeroVenta);
        adaptador = new AdaptadorListviewCuotas(cnt,R.layout.layout_adaptador_cuotas,datos);
        listViewCuotas.setAdapter(adaptador);

        listViewDetalle = (ListView)vista.findViewById(R.id.listViewDetalle);
        datosVenta = manager.getDetalleVenta(NumeroVenta);
        adaptadorProductos = new AdaptadorListviewProductos(cnt,R.layout.layout_adaptador_productos,datosVenta);
        listViewDetalle.setAdapter(adaptadorProductos);


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
