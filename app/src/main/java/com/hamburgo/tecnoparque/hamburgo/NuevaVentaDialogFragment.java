package com.hamburgo.tecnoparque.hamburgo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.CarteraDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.DetalleVentaDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevaVentaDialogFragment extends DialogFragment {

    EditText edtCInicial, edtCuotas;
    RadioButton radMensual, radQuincenal,radDiario;
    RadioGroup radGroup;
    VentaDTO Venta;
    ArrayList<DetalleVentaDTO> Detalle;
    Formatos formato;
    Context cnt;
    Integer NumeroCuotas;
    Integer Base;
    Integer PeriodoPago=1;
    DataBaseManager manager;


    public NuevaVentaDialogFragment(VentaDTO venta, ArrayList<DetalleVentaDTO> detalle) {
        this.Venta = venta;
        this.Detalle = detalle;

        formato = new Formatos();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.fragment_nueva_venta_dialog,null);

        edtCInicial = (EditText)v.findViewById(R.id.edtCInicial);
        edtCuotas = (EditText)v.findViewById(R.id.edtCuotas);

        radGroup= (RadioGroup)v.findViewById(R.id.radGroup);
//        radMensual = (RadioButton)v.findViewById(R.id.radMensual);
//        radQuincenal = (RadioButton)v.findViewById(R.id.radQuincenal);
//        radDiario = (RadioButton)v.findViewById(R.id.radDiario);

        cnt = getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);

        if (Venta.getValorVenta() >= 700000){
            Base=3;
        }else{
            Base=2;
        }

        NumeroCuotas=Base;
        edtCuotas.setText(NumeroCuotas.toString());
        edtCInicial.setText(formato.RedondearDouble(Venta.getValorVenta() * 0.20));

        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.radMensual){
                    NumeroCuotas = Base;
                    PeriodoPago=1;
                }else if (checkedId==R.id.radQuincenal){
                    NumeroCuotas = Base * 2;
                    PeriodoPago=2;
                }else if (checkedId==R.id.radDiario){
                    NumeroCuotas = 45;
                    PeriodoPago=3;
                }
                edtCuotas.setText(NumeroCuotas.toString());
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (!TextUtils.isEmpty(edtCInicial.getText().toString()) && !TextUtils.isEmpty(edtCuotas.getText().toString()) ){
                    Venta.setNumeroCuotas(Integer.valueOf(edtCuotas.getText().toString()));

                    VentaDTO vta = manager.RegistrarVenta(Venta, Detalle, edtCInicial.getText().toString(), PeriodoPago);

                    if (vta != null) {

                        Toast.makeText(cnt, "Venta N° " + vta.getNumeroVenta().toString() + " Registrada", Toast.LENGTH_SHORT).show();
                        Fragment fragmento = new VentasFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, fragmento)
                                .commit();

                    } else {
                        Toast.makeText(cnt, "Error al registrar venta", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(cnt, "Datos incompletos", Toast.LENGTH_SHORT).show();
                }

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
