package com.hamburgo.tecnoparque.hamburgo;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.ClasesAsincronas.GetAsyncrona;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    Button boton ;
    Context cnt;
    String URI = "http://190.109.185.138:8024/api/arboles";

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        cnt = getActivity();
        boton = (Button)vista.findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAsyncrona getAsyncrona = (GetAsyncrona) new GetAsyncrona(cnt, new GetAsyncrona.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {

                        if (!output.equals("")){
                            Toast.makeText(cnt,output.toString(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(cnt,"Respuesta no contiene datos",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute(URI);

            }
        });

        return vista;
    }



}
