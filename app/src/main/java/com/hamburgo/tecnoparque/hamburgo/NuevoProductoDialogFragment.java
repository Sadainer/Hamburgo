
package com.hamburgo.tecnoparque.hamburgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.ArrayList;

/**
 * Created by Admin_Sena on 12/07/2016.
 */
public class NuevoProductoDialogFragment extends android.app.DialogFragment  {


    private EditText edtNombre;
    private EditText edtPrecio;
    private Spinner spiTipo;
    private EditText edtDescripcion;

    private ProductoReturn delegate = null;

    private Context cnt;
    private DataBaseManager manager;



    public interface ProductoReturn {
        void processFinish();
    }


    public NuevoProductoDialogFragment(Context cnt, ProductoReturn delegate) {
        this.delegate = delegate;
        manager = new DataBaseManager(cnt);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.fragment_nuevo_producto,null);

        cnt= getActivity().getApplicationContext();

        edtNombre = (EditText)v.findViewById(R.id.edtNombre);
        edtPrecio = (EditText)v.findViewById(R.id.edtPrecio);
        spiTipo = (Spinner) v.findViewById(R.id.spiTipo);
        edtDescripcion = (EditText)v.findViewById(R.id.edtDescripcion);

        final String[] datos =new String[]{"Relojes","Bolsos","Collares","Televisores","Celulares","Crédito"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(cnt,R.layout.layout_adaptador_spinner,R.id.textItem, datos);
        spiTipo.setAdapter(adaptador);

//        if (Cliente!= null){
//            edtNombres.setText(Cliente.getNombres());
//            edtApellidos.setText(Cliente.getApellidos());
//            edtCelular.setText(Cliente.getCelular());
//            edtCedula.setText(Cliente.getCedula());
//            edtDireccion.setText(Cliente.getDireccion());
//            edtEmpleo.setText(Cliente.getEmpleo());
//            edtEmpresa.setText(Cliente.getEmpresa());
//        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nuevo Cliente");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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


//    @Override
//    public void onStart()
//    {
//        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
//        AlertDialog dialog = (AlertDialog)getDialog();
//        if(dialog != null)
//        {
//            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
//            positiveButton.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    Boolean BanderaGuardar=false;
//                    if (!TextUtils.isEmpty(edtCedula.getText().toString())
//                            && !TextUtils.isEmpty(edtNombres.getText().toString())
//                            && !TextUtils.isEmpty(edtApellidos.getText().toString())
//                            && !TextUtils.isEmpty(edtCelular.getText().toString()))  {
//
//                        BanderaGuardar = true;
//                        ClienteDTO cliente = new ClienteDTO();
//                        cliente.setCedula(edtCedula.getText().toString());
//                        cliente.setCelular(edtCelular.getText().toString());
//                        cliente.setNombres(edtNombres.getText().toString());
//                        cliente.setApellidos(edtApellidos.getText().toString());
//                        cliente.setDireccion(edtDireccion.getText().toString());
//                        cliente.setEmpresa(edtEmpresa.getText().toString());
//                        cliente.setEmpleo(edtEmpleo.getText().toString());
//                        Log.e("Sadainer",edtCelular.getText().toString());
//                        Log.e("Sadainer",String.valueOf(cliente.getCelular()));
//
//                        if (Cliente==null){
//                            manager.Insertar(cliente);
//                        }else{
//                            manager.Actualizar(cliente, Cliente.getCedula().toString());
//                        }
//
//                        delegate.processFinish();
//                }else{
//                    Toast.makeText(getActivity().getApplicationContext(),"Complete los campos obligatorios",Toast.LENGTH_SHORT).show();
//                }
//                    if(BanderaGuardar)
//                        dismiss();
//                }
//            });
//        }
//    }
}
