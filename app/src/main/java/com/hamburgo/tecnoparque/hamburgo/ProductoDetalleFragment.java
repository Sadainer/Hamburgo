package com.hamburgo.tecnoparque.hamburgo;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    TextView txtNombre, txtPrecio, txtTipo;
    EditText edtCantidad;
    CircleImageView Imagen;


    public interface ProductoReturn{
        void processFinish();
    }

    public ProductoDetalleFragment(ProductoDTO producto, ProductoReturn delegate) {
        this.delegate = delegate;
        this.Producto = producto;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_producto_detalle, container, false);

        txtNombre = (TextView)vista.findViewById(R.id.txtNombre);
        txtPrecio = (TextView)vista.findViewById(R.id.txtPrecio);
        txtNombre = (TextView)vista.findViewById(R.id.txtNombre);
        txtNombre = (TextView)vista.findViewById(R.id.txtNombre);


        return vista;
    }

}
