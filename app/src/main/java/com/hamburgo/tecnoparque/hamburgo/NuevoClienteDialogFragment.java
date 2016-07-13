
package com.hamburgo.tecnoparque.hamburgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

/**
 * Created by Admin_Sena on 12/07/2016.
 */
public class NuevoClienteDialogFragment extends android.app.DialogFragment {


    EditText edtCedula, edtNombres, edtApellidos, edtCelular, edtDireccion, edtEmpleo, edtEmpresa;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_nuevo_cliente, container,
//                false);
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        // Do something else
//        return rootView;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.fragment_nuevo_cliente,null);

        edtCedula = (EditText)v.findViewById(R.id.edtCedula);
        edtNombres = (EditText)v.findViewById(R.id.edtNombres);
        edtApellidos = (EditText)v.findViewById(R.id.edtApellido);
        edtCelular = (EditText)v.findViewById(R.id.edtCelular);
        edtDireccion = (EditText)v.findViewById(R.id.edtDireccion);
        edtEmpleo = (EditText)v.findViewById(R.id.edtEmpleo);
        edtEmpresa = (EditText)v.findViewById(R.id.edtEmpresa);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setCedula(Long.valueOf(edtCedula.getText().toString()));
                cliente.setCelular(Long.valueOf(edtCelular.getText().toString()));
                cliente.setNombres(edtNombres.getText().toString());
                cliente.setApellidos(edtApellidos.getText().toString());
                cliente.setDireccion(edtDireccion.getText().toString());
                cliente.setEmpresa(edtEmpresa.getText().toString());
                cliente.setEmpleo(edtEmpleo.getText().toString());

                Principal PrincipalActivity = (Principal) getActivity();
                PrincipalActivity.addClienteLista(cliente);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setView(v);
        return builder.create();
    }
}
