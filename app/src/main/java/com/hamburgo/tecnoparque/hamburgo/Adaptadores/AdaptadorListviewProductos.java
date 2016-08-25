package com.hamburgo.tecnoparque.hamburgo.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.hamburgo.tecnoparque.hamburgo.Formatos;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;
import com.hamburgo.tecnoparque.hamburgo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YOLIMA on 26/04/2016.
 */
public class AdaptadorListviewProductos extends ArrayAdapter<ProductoDTO> {

    List<ProductoDTO> datos;
    ArrayList<ProductoDTO> datosBackup;
    Context cnt;
    Formatos format;
    int layout_list;
    public AdaptadorListviewProductos(Context context, int resource, List<ProductoDTO> objects) {
        super(context, resource, objects);
        datos=objects;
        cnt=context;
        format = new Formatos();
        layout_list=resource;
        datosBackup = new ArrayList<>();
        datosBackup.addAll(datos);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtNombre;
        TextView txtPrecio;
        TextView txtTipo;
        CircleImageView Imagen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        ProductoDTO rowItem = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());


        if (convertView == null) {
            convertView = inflater.inflate(layout_list, null);
            holder = new ViewHolder();
            holder.txtNombre = (TextView) convertView.findViewById(R.id.txtNombre);
            holder.txtPrecio = (TextView) convertView.findViewById(R.id.txtPrecio);
            holder.txtTipo = (TextView) convertView.findViewById(R.id.txtTipo);
            holder.Imagen = (CircleImageView) convertView.findViewById(R.id.itemImage);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
            holder.txtNombre.setText(rowItem.getNombre());

            holder.txtPrecio.setText(format.ConvertirMoneda(rowItem.getPrecio()));
            holder.txtTipo.setText(rowItem.getTipo());
            holder.Imagen.setImageResource(R.drawable.commerce);

            return convertView;
        }

    @Override
    public Filter getFilter() {
        return new Filter() {
//
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if(constraint != null) {
                    constraint = constraint.toString().toLowerCase(Locale.getDefault());
                    Log.e("performFiltering",constraint.toString());
                    datos.clear();
                    for (ProductoDTO product : datosBackup)
                    {
                        if (product.getNombre().toLowerCase(Locale.getDefault()).contains(constraint)
                                || product.getTipo().toLowerCase(Locale.getDefault()).contains(constraint))
                        {
                            datos.add(product);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = datos;
                    filterResults.count = datos.size();
                    Log.e("filterResults",String.valueOf(filterResults.count));
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                   notifyDataSetChanged();
            }
        };
    }

    public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            datos.clear();
            if (charText.length() == 0) {
                datos.addAll(datosBackup);
            }
            else
            {
                for (ProductoDTO product : datosBackup)
                {
                    if (product.getNombre().toLowerCase(Locale.getDefault()).contains(charText)
                            || product.getTipo().toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        datos.add(product);
                    }
                }
            }
            notifyDataSetChanged();
        }

}


