package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCuotas;
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCuotasPago;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.CuotasDTO;

import java.util.ArrayList;

public class PagarCarteraFragment extends Fragment {

    TextView txtNombre;
    TextView txtApellido;
    TextView txtCedula;
    EditText edtValor;
    ListView listViewCuotas;
    ArrayList<CuotasDTO> datos;
    DataBaseManager manager;
    Context cnt;
    AdaptadorListviewCuotasPago adaptador;

    ClienteDTO Cliente;
    public PagarCarteraFragment(ClienteDTO cliente) {
        this.Cliente = cliente;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pagar_cartera, container, false);

        cnt = getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);

        txtNombre = (TextView) v.findViewById(R.id.txtNombre);
        txtApellido = (TextView) v.findViewById(R.id.txtApellido);
        txtCedula = (TextView) v.findViewById(R.id.txtCedula);
        edtValor = (EditText)v.findViewById(R.id.edtValorPagar);

        txtNombre.setText(Cliente.getNombres());
        txtApellido.setText(Cliente.getApellidos());
        txtCedula.setText(Cliente.getCedula());

        listViewCuotas = (ListView)v.findViewById(R.id.listView);
        datos = manager.getCuotasCartera(Cliente.getCedula());
        adaptador = new AdaptadorListviewCuotasPago(cnt, R.layout.layout_adaptador_cuotas_pago, datos, new AdaptadorListviewCuotasPago.ValorReturn() {
            @Override
            public void processFinish(Integer valor) {
                edtValor.setText(valor.toString());
            }
        });
        listViewCuotas.setAdapter(adaptador);
        return v;
    }


}
