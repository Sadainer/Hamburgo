package com.hamburgo.tecnoparque.hamburgo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeleccionFechaFragment extends DialogFragment {

    private FechaReturn delegate=null;
    DatePicker dateFecha;
    Formatos formatos;
    Calendar FechaInicial;

    public SeleccionFechaFragment(Calendar Fecha, FechaReturn delegate) {
        formatos = new Formatos();
        this.delegate=delegate;
        this.FechaInicial = Fecha;
    }

    public interface FechaReturn {
        void processFinish(String fecha);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View vista = i.inflate(R.layout.fragment_seleccion_fecha,null);
        dateFecha = (DatePicker)vista.findViewById(R.id.datePicker);

        Integer Year, Mes, Dia;
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mesFormat = new SimpleDateFormat("MM");
        SimpleDateFormat diaFormat = new SimpleDateFormat("dd");

        Mes = Integer.valueOf(mesFormat.format(FechaInicial.getTime()));
        Year = Integer.valueOf(yearFormat.format(FechaInicial.getTime()));
        Dia = Integer.valueOf(diaFormat.format(FechaInicial.getTime()));

        dateFecha.updateDate(Year, Mes,Dia);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccione la fecha");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fecha= dateFecha.getYear() + "-" + formatos.MesString(dateFecha.getMonth() + 1) + "-" + formatos.MesString(dateFecha.getDayOfMonth());
                delegate.processFinish(fecha);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setView(vista);
        return builder.create();
    }

}
