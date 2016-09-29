package com.hamburgo.tecnoparque.hamburgo;


import android.app.ActivityOptions;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCartera;
import com.hamburgo.tecnoparque.hamburgo.ClasesAsincronas.GetAsyncrona;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

//    Spinner spiLista ;
    TextView txtFecha;
    Context cnt;
    DataBaseManager manager;
    ListView listViewRecaudo;
    ClienteDTO itemMenu;
    AdaptadorListviewCartera adaptadorLista;
    ArrayList<ClienteDTO> datosRecaudo;
    static String FechaCorte;
    String URI = "http://190.109.185.138:8024/api/arboles";

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        itemMenu = adaptadorLista.getItem(info.position);
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
        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        cnt= getActivity().getApplicationContext();
//        spiLista = (Spinner)vista.findViewById(R.id.spiLista);
        manager = new DataBaseManager(cnt);
        listViewRecaudo = (ListView)vista.findViewById(R.id.listView);
        txtFecha = (TextView)vista.findViewById(R.id.txtFechaCorte);

        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fechaCompleta = new SimpleDateFormat("yyyy-MM-dd");

        if(TextUtils.isEmpty(FechaCorte))
        FechaCorte = fechaCompleta.format(calendar.getTime());

        txtFecha.setText(FechaCorte);
        CargarLista(FechaCorte);

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                SeleccionFechaFragment dialogFragment = new SeleccionFechaFragment(calendar, new SeleccionFechaFragment.FechaReturn() {
                    @Override
                    public void processFinish(String fecha) {
                        FechaCorte=fecha;
                        txtFecha.setText(FechaCorte);
                        CargarLista(FechaCorte);
                    }
                });
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
//        cnt = getActivity();
//        boton = (Button)vista.findViewById(R.id.button);
//        boton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GetAsyncrona getAsyncrona = (GetAsyncrona) new GetAsyncrona(cnt, new GetAsyncrona.AsyncResponse() {
//                    @Override
//                    public void processFinish(String output) {
//
//                        if (!output.equals("")){
//                            Toast.makeText(cnt,output.toString(),Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(cnt,"Respuesta no contiene datos",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).execute(URI);
//
//            }
//        });

        return vista;
    }

    private void CargarLista(String fecha){
        datosRecaudo = manager.getCarteraDia(fecha);

        adaptadorLista = new AdaptadorListviewCartera(cnt,R.layout.layout_adaptador_cartera,datosRecaudo);
        listViewRecaudo.setAdapter(adaptadorLista);
        registerForContextMenu(listViewRecaudo);
    }

}
