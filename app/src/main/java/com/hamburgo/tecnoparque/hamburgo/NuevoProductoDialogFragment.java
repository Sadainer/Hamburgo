
package com.hamburgo.tecnoparque.hamburgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
    private ProductoDTO Producto;
    private Context cnt;
    private DataBaseManager manager;



    public interface ProductoReturn {
        void processFinish();
    }


    public NuevoProductoDialogFragment(Context cnt, ProductoDTO producto, ProductoReturn delegate) {
        this.delegate = delegate;
        this.Producto = producto;
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

        edtPrecio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("Sadainer",s.toString());
//                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//                DecimalFormat decimalFormat = new DecimalFormat("$ #,###", symbols);
//                decimalFormat.format(Integer.valueOf(s.toString()));
            }
        });

        final String[] datos =new String[]{"Relojes","Bolsos","Collares","Televisores","Celulares","Crédito"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(cnt,R.layout.layout_adaptador_spinner,R.id.textItem, datos);
        spiTipo.setAdapter(adaptador);

        if (Producto!= null){
            edtNombre.setText(Producto.getNombre().toString());
            edtPrecio.setText(String.valueOf(Producto.getPrecio()));
            edtDescripcion.setText(Producto.getDescripcion().toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nuevo Producto");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("Sadainer","No funciona");
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
                    Log.e("Sadainer","Funciona");
                    Boolean BanderaGuardar=false;
                    if (!TextUtils.isEmpty(edtNombre.getText().toString())
                            && !TextUtils.isEmpty(spiTipo.getSelectedItem().toString())
                            && !TextUtils.isEmpty(edtPrecio.getText().toString()))  {

                        BanderaGuardar = true;
                        ProductoDTO producto = new ProductoDTO();
                        producto.setNombre(edtNombre.getText().toString());
                        producto.setDescripcion(edtDescripcion.getText().toString());
                        producto.setTipo(spiTipo.getSelectedItem().toString());
                        producto.setPrecio(Integer.valueOf(edtPrecio.getText().toString()));
                        producto.setImagen(R.drawable.commerce);

                        if (Producto==null){
                            manager.InsertarProductos(producto);
                        }else{
                            manager.ActualizarProductos(producto, Producto.getNombre().toString());
                        }

                        delegate.processFinish();
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
