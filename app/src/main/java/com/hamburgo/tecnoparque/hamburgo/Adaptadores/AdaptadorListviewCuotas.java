package com.hamburgo.tecnoparque.hamburgo.Adaptadores;

import android.content.Context;
import android.graphics.Color;
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
        TextView txtDeuda;
        TextView txtPagado;
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
            holder.txtDeuda = (TextView) convertView.findViewById(R.id.txtDeuda);
            holder.txtValor = (TextView) convertView.findViewById(R.id.txtValor);
            holder.txtPagado = (TextView) convertView.findViewById(R.id.txtPagado);
            convertView.setTag(holder);
        } else

            holder = (ViewHolder) convertView.getTag();
            holder.txtFecha.setText(rowItem.getFechaPago());
            holder.txtDeuda .setText(format.ConvertirMoneda(rowItem.getValorDeuda().toString()));
            holder.txtValor .setText(format.ConvertirMoneda(rowItem.getValorCuota().toString()));
            if (rowItem.getPagada()==1) {
                holder.txtPagado.setText("Pagada");
                holder.txtPagado.setTextColor(Color.parseColor("#009688"));
            }
            else {
                holder.txtPagado.setText("Sin pagar");
                holder.txtPagado.setTextColor(Color.parseColor("#FFFA6175"));
            }


            return convertView;
        }



}


