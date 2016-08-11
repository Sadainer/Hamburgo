package com.hamburgo.tecnoparque.hamburgo.DTO;

/**
 * Created by YOLIMA on 13/07/2016.
 */
public class ProductoDTO {

    private String Nombre;
    private int Precio;
    private String Tipo;
    private String Descripcion;
    private int imagen;

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public ProductoDTO() {
    }

    public ProductoDTO(ProductoDTO p) {
        this.setNombre(p.getNombre());
        this.setTipo(p.getTipo());
        this.setPrecio(p.getPrecio());
        this.setDescripcion(p.getDescripcion());
        this.setImagen(p.getImagen());
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
