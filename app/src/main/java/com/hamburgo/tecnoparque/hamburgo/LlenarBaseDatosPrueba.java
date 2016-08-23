package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;

import java.util.ArrayList;

/**
 * Created by YOLIMA on 22/08/2016.
 */
public class LlenarBaseDatosPrueba {
   DataBaseManager manager;


    public void Llenar(Context cnt){
        manager = new DataBaseManager(cnt);

        ProductoDTO a = new ProductoDTO();
        a.setNombre("TV Samsung 48");
        a.setTipo("Televisor");
        a.setPrecio(1200000);
        a.setDescripcion("Smart TV");
        manager.InsertarProductos(a);

        ProductoDTO b = new ProductoDTO();
        b.setNombre("TV Samsung 46");
        b.setTipo("Televisor");
        b.setPrecio(1000000);
        b.setDescripcion("Smart TV");
        manager.InsertarProductos(b);

        ProductoDTO c = new ProductoDTO();
        c.setNombre("TV LG 40");
        c.setTipo("Televisor");
        c.setPrecio(800000);
        c.setDescripcion("Smart TV");
        manager.InsertarProductos(c);

        ProductoDTO d = new ProductoDTO();
        d.setNombre("Reloj Tissot");
        d.setTipo("Reloj");
        d.setPrecio(1800000);
        d.setDescripcion("Reloj");
        manager.InsertarProductos(d);

        ProductoDTO e = new ProductoDTO();
        e.setNombre("Bolso Nicol Lee");
        e.setTipo("Bolsos");
        e.setPrecio(200000);
        e.setDescripcion("Bolsos");
        manager.InsertarProductos(e);


        ClienteDTO f = new ClienteDTO();
        f.setNombres("Sadainer");
        f.setApellidos("Hernández Chacón");
        f.setCedula("1065582510");
        f.setCelular("3002598071");
        manager.Insertar(f);

        ClienteDTO g = new ClienteDTO();
        g.setNombres("Diana");
        g.setApellidos("Chinchi Vence");
        g.setCedula("1065629942");
        g.setCelular("3126262888");
        manager.Insertar(g);

        ClienteDTO h = new ClienteDTO();
        h.setNombres("Pedro");
        h.setApellidos("Perez Pineda");
        h.setCedula("12345");
        h.setCelular("12345");
        manager.Insertar(h);

        ClienteDTO i = new ClienteDTO();
        i.setNombres("Perensejo");
        i.setApellidos("Sutanito Lopez");
        i.setCedula("54321");
        i.setCelular("54321");
        manager.Insertar(i);



    }
}
