package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.DTO.itemLista;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YOLIMA on 26/04/2016.
 */
public class AdaptadorListview extends ArrayAdapter<itemLista> {

    itemLista[] datos;
    Context cnt;
    int layout_list;
    public AdaptadorListview(Context context, int resource, itemLista[] objects) {
        super(context, resource, objects);
        datos=objects;
        cnt=context;
        layout_list=resource;
    }

    /*private view holder class*/
    private class ViewHolder {
        CircleImageView imageView;
        TextView txtTitle;
        TextView txtSubTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        itemLista rowItem = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());


        if (convertView == null) {
            convertView = inflater.inflate(layout_list, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textTitulo);
            holder.txtSubTitle = (TextView) convertView.findViewById(R.id.textSubTitulo);
            holder.imageView = (CircleImageView) convertView.findViewById(R.id.itemImage);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

            holder.txtTitle.setText(rowItem.getTitulo());
            holder.txtSubTitle.setText(rowItem.getSubtitulo());
            holder.imageView.setImageResource(rowItem.getImagen());

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


