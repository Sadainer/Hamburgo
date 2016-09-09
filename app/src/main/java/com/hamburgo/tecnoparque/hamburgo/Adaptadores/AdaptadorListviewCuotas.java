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
import com.hamburgo.tecnoparque.hamburgo.DTO.CuotasDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;
import com.hamburgo.tecnoparque.hamburgo.Formatos;
import com.hamburgo.tecnoparque.hamburgo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by YOLIMA on 26/04/2016.
 */
public class AdaptadorListviewCuotas extends ArrayAdapter<CuotasDTO> {

    List<CuotasDTO> datos;
    DataBaseManager manager;
    Context cnt;
    int layout_list;
    Formatos format;

    public AdaptadorListviewCuotas(Context context, int resource, List<CuotasDTO> objects) {
        super(context, resource, objects);
        datos=objects;
        cnt=context;
        layout_list=resource;
        format = new Formatos();
        manager = new DataBaseManager(cnt);
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtFecha;
        TextView txtValor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        CuotasDTO rowItem = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());


        if (convertView == null) {
            convertView = inflater.inflate(layout_list, null);
            holder = new ViewHolder();
            holder.txtFecha = (TextView) convertView.findViewById(R.id.txtNombre);
            holder.txtValor = (TextView) convertView.findViewById(R.id.txtDeuda);
            convertView.setTag(holder);
        } else

            holder = (ViewHolder) convertView.getTag();
            holder.txtFecha.setText(rowItem.getFechaPago());
            holder.txtValor .setText(rowItem.getValorDeuda().toString());
            Log.e("Sadainer",rowItem.getFechaPago());

            return convertView;
        }



}


