package com.hamburgo.tecnoparque.hamburgo;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCuotas;
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewCuotasPago;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.CuotasDTO;

import java.net.URI;
import java.util.ArrayList;

public class PagarCarteraFragment extends Fragment {

    TextView txtNombre;
    TextView txtApellido;
    TextView txtCedula;
    Button btnPagar;
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
        btnPagar = (Button)v.findViewById(R.id.btnRegistrar);

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
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Validarlista() && !TextUtils.isEmpty(edtValor.getText().toString())){





                    if(manager.PagarCuotasVenta(Cliente,Integer.valueOf(edtValor.getText().toString()))){
                        Toast.makeText(cnt,"Registrado con exito",Toast.LENGTH_SHORT).show();
                        Fragment fragmento = new CarteraFragment();
                        FragmentManager fragmentManager = getFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, fragmento)
                                .commit();
                    }else{
                        Toast.makeText(cnt,"Ocurrio un error al registrar la información",Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Toast.makeText(cnt,"Datos incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private boolean Validarlista(){
        Boolean[] Bandera = new Boolean[datos.size()];
        Boolean BusquedaCorrecta = true;
        Boolean Activado= false;
        Boolean Bloqueado = false;
        for (int i=0; i < listViewCuotas.getCount();i++){
            View row = listViewCuotas.getAdapter().getView(i, null, null);
            CheckBox tvTest = (CheckBox) row.findViewById(R.id.checkBox);
            if (tvTest.isChecked()) {
                Bandera[i] = true;
                Activado =true;
                if(Bloqueado==true){
                    BusquedaCorrecta=false;
                }
            }
            else{
                Bandera[i]=false;
                if (Activado==true){
                    Bloqueado=true;
                }
            }
        }
        return BusquedaCorrecta;
    }

}
