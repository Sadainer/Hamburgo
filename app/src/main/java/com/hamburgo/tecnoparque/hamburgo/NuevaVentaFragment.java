package com.hamburgo.tecnoparque.hamburgo;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewClientes;
import com.hamburgo.tecnoparque.hamburgo.Adaptadores.AdaptadorListviewProductos;
import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.DetalleVentaDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevaVentaFragment extends Fragment {

    AutoCompleteTextView AutCompleteClientes,AutCompleteProductos ;
    ListView ListProductos;
    TextView txtTotal;
    EditText edtCuotas;
    EditText edtCInicial;

    Button btnRegistrar;

    Integer Total = 0;

    Formatos formato;
    Context cnt;
    List<ClienteDTO> datos;
    AdaptadorListviewClientes adaptador;


    AdaptadorListviewProductos adaptadorProductos;
    AdaptadorListviewProductos adaptadorProductosFinal;

    List<ProductoDTO> datosProductos;
    List<ProductoDTO> datosProductosFinal;

    private DataBaseManager manager;

    public NuevaVentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista =  inflater.inflate(R.layout.fragment_nueva_venta, container, false);

        datos= new ArrayList<ClienteDTO>();
        datosProductos= new ArrayList<ProductoDTO>();
        datosProductosFinal= new ArrayList<ProductoDTO>();

        cnt= getActivity().getApplicationContext();
        manager = new DataBaseManager(cnt);
        formato = new Formatos();

        txtTotal = (TextView)vista.findViewById(R.id.txtTotal);
        edtCuotas = (EditText) vista.findViewById(R.id.edtCuota);
        AutCompleteClientes = (AutoCompleteTextView)vista.findViewById(R.id.autoCompleteClientes);
        AutCompleteProductos = (AutoCompleteTextView)vista.findViewById(R.id.autoCompleteproductos);
        ListProductos = (ListView)vista.findViewById(R.id.listViewProductos);
        btnRegistrar = (Button)vista.findViewById(R.id.btnRegistrar);
        edtCInicial = new EditText(getActivity());


        adaptadorProductosFinal = new AdaptadorListviewProductos(cnt, R.layout.layout_adaptador_productos, datosProductosFinal);
        ListProductos.setAdapter(adaptadorProductosFinal);

        LlenarListaClientes();
        LlenarListaProductos();



        AutCompleteClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView cedula = (TextView)view.findViewById(R.id.txtCedula);
                AutCompleteClientes.setText(cedula.getText().toString());
            }
        });

        AutCompleteProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ProductoDTO p = datosProductos.get(position);
                if (!ProductoContains(p.getNombre())){
                    MostrarDialog(p);
                }
                AutCompleteProductos.setText("");

            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!AutCompleteClientes.getText().toString().equals("") && edtCuotas.getText() != null && ListProductos.getCount() > 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Cuota Inicial");
                    builder.setTitle("Desea guardar la venta");
                    edtCInicial.setText(String.valueOf((int)Total * 0.2));
                    edtCInicial.setGravity(Gravity.RIGHT);
                    edtCInicial.setInputType(InputType.TYPE_CLASS_NUMBER);

                    edtCInicial.requestFocus();
                    builder.setView(edtCInicial);

                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                    builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(cnt,edtCInicial.getText().toString(),Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(cnt,"Cancel",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();

                }else{
                    Toast.makeText(cnt,"Registre Campos necesarios",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return vista;
    }

    private void GuardarVenta(){
        VentaDTO venta = new VentaDTO();

        venta.setIdVenderor("1065582510");
        venta.setIdCliente(AutCompleteClientes.getText().toString());
        venta.setNumeroCuotas(Integer.valueOf(edtCuotas.getText().toString()));
        venta.setObservacion("Ninguna");
        venta.setValorVenta(Total);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        venta.setFecha(df.format(c.getTime()));

        ArrayList<DetalleVentaDTO> detalle = new ArrayList<DetalleVentaDTO>();
        for (ProductoDTO p : datosProductosFinal) {
            DetalleVentaDTO d = new DetalleVentaDTO();
            d.setNombre(p.getNombre());
            d.setValor(p.getPrecio());
            d.setCantidad(Integer.valueOf(p.getTipo()));
            d.setValorTotal(p.getPrecio());
            detalle.add(d);
        }
        VentaDTO vta = manager.RegistrarVenta(venta, detalle);
        if (vta != null) {
            Toast.makeText(cnt, "Venta N° " + vta.getNumeroVenta().toString() + " Registrada", Toast.LENGTH_SHORT).show();
            Fragment fragmento = new VentasFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragmento)
                    .commit();

        } else {
            Toast.makeText(cnt, "Error al registrar venta", Toast.LENGTH_SHORT).show();
        }
    }

    private void MostrarDialog(ProductoDTO producto){
        FragmentManager fm = getFragmentManager();
        ProductoDetalleFragment dialogFragment = new ProductoDetalleFragment(producto, new ProductoDetalleFragment.ProductoReturn() {
            @Override
            public void processFinish(ProductoDTO p) {
                datosProductosFinal.add(p);
                Total += p.getPrecio() * Integer.valueOf(p.getTipo());
                txtTotal.setText("Total = " + formato.ConvertirMoneda(Total));
                if (Total>=700000){
                    edtCuotas.setText("3");
                }else{
                    edtCuotas.setText("2");
                }
                adaptadorProductosFinal.notifyDataSetChanged();
            }
        });
        dialogFragment.show(fm, null);
    }


    private void LlenarListaClientes () {
        datos.clear();
        datos = manager.getListaClientes();
        if (datos.size() > 0) {
            adaptador = new AdaptadorListviewClientes(cnt, R.layout.layout_adaptador_listview, datos);
            AutCompleteClientes.setThreshold(3);
            AutCompleteClientes.setAdapter(adaptador);
        }
    }
    private void LlenarListaProductos() {
        datosProductos.clear();
        datosProductos = manager.getListaProductos();
        if (datosProductos.size() > 0) {
            adaptadorProductos = new AdaptadorListviewProductos(cnt, R.layout.layout_adaptador_productos, datosProductos);
            AutCompleteProductos.setThreshold(2);
            AutCompleteProductos.setAdapter(adaptadorProductos);
        }
    }
    private Boolean ProductoContains(String Nombre){

        for (ProductoDTO p :datosProductosFinal) {
            if (p.getNombre().equals(Nombre)){
                return true;
            }
        }
        return false;
    }

}
