package com.hamburgo.tecnoparque.hamburgo.DTO;

/**
 * Created by YOLIMA on 13/07/2016.
 */
public class DetalleVentaDTO {



    private Integer NumeroDetalleVenta;
    private Integer NumeroVenta;
    private String Nombre;
    private String Valor;

    public Integer getNumeroDetalleVenta() {
        return NumeroDetalleVenta;
    }

    public void setNumeroDetalleVenta(Integer numeroDetalleVenta) {
        NumeroDetalleVenta = numeroDetalleVenta;
    }

    public Integer getNumeroVenta() {
        return NumeroVenta;
    }

    public void setNumeroVenta(Integer numeroVenta) {
        NumeroVenta = numeroVenta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }
}
