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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductoDetalleFragment extends DialogFragment {


    private ProductoReturn delegate = null;
    ProductoDTO Producto;
    TextView txtNombre, txtTipo;
    EditText edtCantidad,edtPrecio;
    CircleImageView Imagen;


    public interface ProductoReturn{
        void processFinish(ProductoDTO p);
    }

    public ProductoDetalleFragment(ProductoDTO producto, ProductoReturn delegate) {
        this.delegate = delegate;
        this.Producto = new ProductoDTO(producto);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater i = getActivity().getLayoutInflater();
        View vista = i.inflate(R.layout.fragment_producto_detalle,null);

        txtNombre = (TextView)vista.findViewById(R.id.txtNombre);
        txtTipo = (TextView)vista.findViewById(R.id.txtTipo);
        edtPrecio = (EditText) vista.findViewById(R.id.edtPrecio);
        edtCantidad = (EditText) vista.findViewById(R.id.edtCantidad);

        txtNombre.setText(Producto.getNombre());
        txtTipo.setText(Producto.getTipo());
        edtPrecio.setText(String.valueOf(Producto.getPrecio()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Detalle Producto");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Producto.setPrecio(Integer.valueOf(edtPrecio.getText().toString()));
                Producto.setTipo("Cantidad: " + edtCantidad.getText().toString());
                delegate.processFinish(Producto);
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
