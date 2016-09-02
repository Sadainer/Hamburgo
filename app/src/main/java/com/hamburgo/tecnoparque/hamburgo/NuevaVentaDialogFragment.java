package com.hamburgo.tecnoparque.hamburgo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.hamburgo.tecnoparque.hamburgo.DTO.DetalleVentaDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevaVentaDialogFragment extends DialogFragment {

    EditText edtCInicial, edtCuotas;
    RadioButton radMensual, radQuincenal;
    VentaDTO Venta;
    ArrayList<DetalleVentaDTO> Detalle;


    public NuevaVentaDialogFragment(VentaDTO venta, ArrayList<DetalleVentaDTO> detalle) {
        this.Venta = venta;
        this.Detalle = detalle;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.fragment_nueva_venta_dialog,null);

        edtCInicial = (EditText)v.findViewById(R.id.edtCInicial);
        edtCuotas = (EditText)v.findViewById(R.id.edtCuotas);
        radMensual = (RadioButton)v.findViewById(R.id.radMensual);
        radQuincenal = (RadioButton)v.findViewById(R.id.radQuincenal);

        if (Venta.getValorVenta() >= 700000){
            edtCuotas.setText("3");
        }else{
            edtCuotas.setText("2");
        }




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setView(v);
        return builder.create();
    }



}
