package com.hamburgo.tecnoparque.hamburgo;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCartera;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarteraFragment extends Fragment {

    ListView listViewCartera;
    DataBaseManager manager;
    AdaptadorListviewCartera adaptador;
    ArrayList<ClienteDTO> datos;
    Context cnt;
    ClienteDTO itemMenu;

    public CarteraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        itemMenu = adaptador.getItem(info.position);
        if (v.getId()==R.id.listView) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_cartera, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.ver:
                FragmentManager fm = getFragmentManager();
                CuotasCarteraFragment dialogFragment = new CuotasCarteraFragment(itemMenu.getCedula().toString());
                dialogFragment.show(fm, null);
                return true;
            case R.id.pagar:

//                Toast.makeText(cnt,itemMenu.getCedula().toString(),Toast.LENGTH_SHORT).show();
                Fragment fragmento= new PagarCarteraFragment(itemMenu);
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragmento)
                        .commit();

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cartera, container, false);

        cnt = getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        listViewCartera = (ListView)vista.findViewById(R.id.listView);
        datos = manager.getCartera();
//

        adaptador = new AdaptadorListviewCartera(cnt,R.layout.layout_adaptador_cartera,datos);
        listViewCartera.setAdapter(adaptador);
        registerForContextMenu(listViewCartera);

        listViewCartera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        TextView txtCedula = (TextView)view.findViewById(R.id.txtCedula) ;

                        FragmentManager fm = getFragmentManager();
                        CuotasCarteraFragment dialogFragment = new CuotasCarteraFragment(txtCedula.getText().toString());
                        dialogFragment.show(fm, null);
            }
        });

        return vista;
    }

}
