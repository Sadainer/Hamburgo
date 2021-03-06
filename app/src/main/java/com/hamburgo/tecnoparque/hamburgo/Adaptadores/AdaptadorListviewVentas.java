package com.hamburgo.tecnoparque.hamburgo.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;
import com.hamburgo.tecnoparque.hamburgo.Formatos;
import com.hamburgo.tecnoparque.hamburgo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YOLIMA on 26/04/2016.
 */
public class AdaptadorListviewVentas extends ArrayAdapter<VentaDTO> {

    List<VentaDTO> datos;
    ArrayList<VentaDTO> datosBackup;
    DataBaseManager manager;
    Context cnt;
    int layout_list;
    ClienteDTO c = new ClienteDTO();
    Formatos format;

    public AdaptadorListviewVentas(Context context, int resource, List<VentaDTO> objects) {
        super(context, resource, objects);
        datos=objects;
        cnt=context;
        layout_list=resource;
        format = new Formatos();
        datosBackup = new ArrayList<>();
        datosBackup.addAll(datos);
        manager = new DataBaseManager(cnt);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtCedula;
        TextView txtNombre;
        TextView txtApellido;
        TextView txtVenta;
        TextView txtFecha;
        TextView txtValor;
        TextView txtCuotas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        VentaDTO rowItem = getItem(position);
        c = manager.getUsuario(rowItem.getIdCliente().toString());
        LayoutInflater inflater = LayoutInflater.from(getContext());


        if (convertView == null) {
            convertView = inflater.inflate(layout_list, null);
            holder = new ViewHolder();
            holder.txtCedula = (TextView) convertView.findViewById(R.id.txtCedula);
            holder.txtNombre = (TextView) convertView.findViewById(R.id.txtNombre);
            holder.txtApellido = (TextView) convertView.findViewById(R.id.txtApellido);
            holder.txtVenta = (TextView) convertView.findViewById(R.id.txtVenta);
            holder.txtFecha = (TextView) convertView.findViewById(R.id.txtFecha);
            holder.txtValor = (TextView) convertView.findViewById(R.id.txtValor);
            holder.txtCuotas = (TextView) convertView.findViewById(R.id.txtCuotas);
            convertView.setTag(holder);
        } else

            holder = (ViewHolder) convertView.getTag();
            holder.txtCedula.setText(rowItem.getIdCliente());
            holder.txtNombre .setText(c.getNombres().toString());
            holder.txtApellido.setText(c.getApellidos().toString());
            holder.txtVenta.setText(String.valueOf(rowItem.getNumeroVenta()));
            holder.txtFecha .setText(rowItem.getFecha());

            holder.txtValor .setText(format.ConvertirMoneda(rowItem.getValorVenta()));
            holder.txtCuotas .setText("Cuotas: " + String.valueOf(rowItem.getNumeroCuotas()));

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
                    for (VentaDTO vent : datosBackup)
                    {
                        if (vent.getIdCliente().toLowerCase(Locale.getDefault()).contains(constraint)
                                || vent.getFecha().toLowerCase(Locale.getDefault()).contains(constraint))
                        {
                            datos.add(vent);
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
                for (VentaDTO vent : datosBackup)
                {
                    if (vent.getIdCliente().toLowerCase(Locale.getDefault()).contains(charText)
                            || vent.getFecha().toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        datos.add(vent);
                    }
                }
            }
            notifyDataSetChanged();
        }

}


