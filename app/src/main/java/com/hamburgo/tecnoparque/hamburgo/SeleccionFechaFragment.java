package com.hamburgo.tecnoparque.hamburgo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    Integer Year, Mes, Dia;

    public SeleccionFechaFragment(Integer year, Integer mes, Integer dia, FechaReturn delegate) {
        formatos = new Formatos();
        this.delegate=delegate;
        this.Year=year;
        this.Mes=mes;
        this.Dia=dia;
        Mes--;
    }

    public interface FechaReturn {
        void processFinish(Integer Year,Integer Mes, Integer Dia );
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View vista = i.inflate(R.layout.fragment_seleccion_fecha,null);
        dateFecha = (DatePicker)vista.findViewById(R.id.datePicker);

        dateFecha.updateDate(Year, Mes,Dia);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccione la fecha");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Year = dateFecha.getYear();
                Mes = dateFecha.getMonth() + 1;
                Dia = dateFecha.getDayOfMonth();
                delegate.processFinish(Year, Mes, Dia);
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
