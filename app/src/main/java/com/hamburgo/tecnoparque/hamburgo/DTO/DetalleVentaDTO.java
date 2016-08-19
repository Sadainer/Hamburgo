package com.hamburgo.tecnoparque.hamburgo.DTO;

/**
 * Created by YOLIMA on 13/07/2016.
 */
public class DetalleVentaDTO {



    private Integer NumeroDetalleVenta;
    private Integer NumeroVenta;
    private String Nombre;
    private Integer Valor;
    private Integer Cantidad;
    private Integer ValorTotal;

    public Integer getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Integer cantidad) {
        Cantidad = cantidad;
    }

    public Integer getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(Integer valorTotal) {
        ValorTotal = valorTotal;
    }

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

    public Integer getValor() {
        return Valor;
    }

    public void setValor(Integer valor) {
        Valor = valor;
    }
}
