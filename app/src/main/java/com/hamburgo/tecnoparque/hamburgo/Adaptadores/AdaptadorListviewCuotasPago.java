package com.hamburgo.tecnoparque.hamburgo.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.CuotasDTO;
import com.hamburgo.tecnoparque.hamburgo.Formatos;
import com.hamburgo.tecnoparque.hamburgo.R;

import java.util.List;

/**
 * Created by YOLIMA on 26/04/2016.
 */
public class AdaptadorListviewCuotasPago extends ArrayAdapter<CuotasDTO> {

    List<CuotasDTO> datos;
    DataBaseManager manager;
    Context cnt;
    int layout_list;
    Formatos format;
    final Boolean[] CPagado;

    public AdaptadorListviewCuotasPago(Context context, int resource, List<CuotasDTO> objects) {
        super(context, resource, objects);
        datos=objects;
        cnt=context;
        layout_list=resource;
        format = new Formatos();
        manager = new DataBaseManager(cnt);
        CPagado = new Boolean[datos.size()];
        for (int i=0;i<datos.size();i++){
            CPagado[i]=false;
        }
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtFecha;
        TextView txtValor;
        TextView txtDeuda;
        TextView txtPagado;
        CheckBox chkPagar;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

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
            holder.chkPagar = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else

            holder = (ViewHolder) convertView.getTag();
            holder.txtFecha.setText(rowItem.getFechaPago());
            holder.txtDeuda .setText(format.ConvertirMoneda(rowItem.getValorDeuda().toString()));
            holder.txtValor .setText(format.ConvertirMoneda(rowItem.getValorCuota().toString()));
            holder.chkPagar.setChecked(CPagado[position]);
            if (rowItem.getPagada()==1) {
                holder.txtPagado.setText("Pagada");
                holder.txtPagado.setTextColor(Color.parseColor("#009688"));
            }
            else {
                holder.txtPagado.setText("Sin pagar");
                holder.txtPagado.setTextColor(Color.parseColor("#FFFA6175"));
            }
            holder.chkPagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    CPagado[position]= cb.isChecked();
                }
            });
            return convertView;
        }



}


