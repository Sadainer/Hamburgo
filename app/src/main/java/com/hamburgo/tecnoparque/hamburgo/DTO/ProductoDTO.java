package com.hamburgo.tecnoparque.hamburgo.DTO;

/**
 * Created by YOLIMA on 13/07/2016.
 */
public class ProductoDTO {

    private String Nombre;
    private int Precio;
    private String Tipo;
    private String Descripcion;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
