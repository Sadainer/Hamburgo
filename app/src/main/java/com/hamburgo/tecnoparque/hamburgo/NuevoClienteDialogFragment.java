
package com.hamburgo.tecnoparque.hamburgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.List;

/**
 * Created by Admin_Sena on 12/07/2016.
 */
public class NuevoClienteDialogFragment extends android.app.DialogFragment  {


    private EditText edtCedula;
    private EditText edtNombres;
    private EditText edtApellidos;
    private EditText edtCelular;
    private EditText edtDireccion;
    private EditText edtEmpleo;
    private EditText edtEmpresa;

    private ClienteReturn delegate = null;
    private ClienteDTO Cliente;
    private DataBaseManager manager;


    public interface ClienteReturn {
        void processFinish(ClienteDTO cliente);
    }


    public NuevoClienteDialogFragment(Context cnt, ClienteDTO cliente, ClienteReturn delegate) {
        this.delegate = delegate;
        this.Cliente = cliente;
        manager = new DataBaseManager(cnt);
    }

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


        if (Cliente!= null){
            edtNombres.setText(Cliente.getNombres());
            edtApellidos.setText(Cliente.getApellidos());
            edtCelular.setText(Cliente.getCelular());
            edtCedula.setText(Cliente.getCedula());
            edtDireccion.setText(Cliente.getDireccion());
            edtEmpleo.setText(Cliente.getEmpleo());
            edtEmpresa.setText(Cliente.getEmpresa());
        }

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



    @Override
    public void onStart()
    {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        AlertDialog dialog = (AlertDialog)getDialog();
        if(dialog != null)
        {
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean BanderaGuardar=false;
                    if (!TextUtils.isEmpty(edtCedula.getText().toString())
                            && !TextUtils.isEmpty(edtNombres.getText().toString())
                            && !TextUtils.isEmpty(edtApellidos.getText().toString())
                            && !TextUtils.isEmpty(edtCelular.getText().toString()))  {

                        BanderaGuardar = true;
                        ClienteDTO cliente = new ClienteDTO();
                        cliente.setCedula(edtCedula.getText().toString());
                        cliente.setCelular(edtCelular.getText().toString());
                        cliente.setNombres(edtNombres.getText().toString());
                        cliente.setApellidos(edtApellidos.getText().toString());
                        cliente.setDireccion(edtDireccion.getText().toString());
                        cliente.setEmpresa(edtEmpresa.getText().toString());
                        cliente.setEmpleo(edtEmpleo.getText().toString());
                        Log.e("Sadainer",edtCelular.getText().toString());
                        Log.e("Sadainer",String.valueOf(cliente.getCelular()));

                        if (Cliente==null){
                            manager.Insertar(cliente);
                        }else{
                            manager.Actualizar(cliente, Cliente.getCedula().toString());
                        }

                        delegate.processFinish(cliente);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Complete los campos obligatorios",Toast.LENGTH_SHORT).show();
                }
                    if(BanderaGuardar)
                        dismiss();
                }
            });
        }
    }
}
