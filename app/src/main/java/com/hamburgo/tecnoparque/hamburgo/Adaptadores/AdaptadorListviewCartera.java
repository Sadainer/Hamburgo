package com.hamburgo.tecnoparque.hamburgo.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.Formatos;
import com.hamburgo.tecnoparque.hamburgo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by YOLIMA on 26/04/2016.
 */
public class AdaptadorListviewCartera extends ArrayAdapter<ClienteDTO> {

    List<ClienteDTO> datos;
    ArrayList<ClienteDTO> datosBackup;
    Context cnt;
    int layout_list;
    Formatos format;

    public AdaptadorListviewCartera(Context context, int resource, List<ClienteDTO> objects) {
        super(context, resource, objects);
        datos=objects;
        cnt=context;
        layout_list=resource;
        format = new Formatos();
        datosBackup = new ArrayList<>();
        datosBackup.addAll(datos);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtNombre;
        TextView txtApellido;
        TextView txtCedula;
        TextView txtDeuda;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        ClienteDTO rowItem = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());


        if (convertView == null) {
            convertView = inflater.inflate(layout_list, null);
            holder = new ViewHolder();
            holder.txtNombre = (TextView) convertView.findViewById(R.id.txtNombre);
            holder.txtApellido = (TextView) convertView.findViewById(R.id.txtApellido);
            holder.txtCedula = (TextView) convertView.findViewById(R.id.txtCedula);
            holder.txtDeuda = (TextView) convertView.findViewById(R.id.txtDeuda);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
            holder.txtNombre.setText(rowItem.getNombres());
            holder.txtApellido.setText(rowItem.getApellidos());
            holder.txtCedula.setText(String.valueOf(rowItem.getCedula()));
            holder.txtDeuda.setText(format.ConvertirMoneda(rowItem.getCelular()));

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
                    for (ClienteDTO client : datosBackup)
                    {
                        if (client.getNombres().toLowerCase(Locale.getDefault()).contains(constraint)
                                || client.getApellidos().toLowerCase(Locale.getDefault()).contains(constraint)
                                || String.valueOf(client.getCedula()).contains(constraint))
                        {
                            datos.add(client);
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
                for (ClienteDTO client : datosBackup)
                {
                    if (client.getNombres().toLowerCase(Locale.getDefault()).contains(charText)
                            || client.getApellidos().toLowerCase(Locale.getDefault()).contains(charText)
                            || String.valueOf(client.getCedula()).contains(charText))
                    {
                        datos.add(client);
                    }
                }
            }
            notifyDataSetChanged();
        }

}


