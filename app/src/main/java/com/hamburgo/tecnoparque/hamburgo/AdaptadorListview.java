package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.itemLista;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YOLIMA on 26/04/2016.
 */
public class AdaptadorListview extends ArrayAdapter<ClienteDTO> {

    ArrayList<ClienteDTO> datos;
    Context cnt;
    int layout_list;
    public AdaptadorListview(Context context, int resource, ArrayList<ClienteDTO> objects) {
        super(context, resource, objects);
        datos=objects;
        cnt=context;
        layout_list=resource;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtNombre;
        TextView txtApellido;
        TextView txtCedula;
        TextView txtCelular;
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
            holder.txtCelular = (TextView) convertView.findViewById(R.id.txtCelular);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
            holder.txtNombre.setText(rowItem.getNombres());
            holder.txtApellido.setText(rowItem.getApellidos());
            holder.txtCedula.setText(String.valueOf(rowItem.getCedula()));
            holder.txtCelular.setText(String.valueOf(rowItem.getCelular()));

            return convertView;
        }

//
//
//        View item = inflater.inflate(layout_list, null);
//
//        TextView lblTitulo = (TextView)item.findViewById(R.id.textTitulo);
//        lblTitulo.setText(datos[position].getTitulo());
//
//        TextView lblSubtitulo = (TextView)item.findViewById(R.id.textSubTitulo);
//        lblSubtitulo.setText(datos[position].getSubtitulo());
//
//        ImageView imageItem = (ImageView)item.findViewById(R.id.imageView);
//        imageItem.setImageResource(datos[position].getImagen());
//
//        return(item);



}


