package com.hamburgo.tecnoparque.hamburgo.DTO;

/**
 * Created by YOLIMA on 13/07/2016.
 */
public class VentaDTO {

    private Integer NumeroVenta;
    private String Fecha;
    private String IdCliente;
    private String IdVenderor;
    private String ValorVenta;
    private Integer NumeroCuotas;
    private String Observacion;

    public Integer getNumeroVenta() {
        return NumeroVenta;
    }

    public void setNumeroVenta(Integer numeroVenta) {
        NumeroVenta = numeroVenta;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(String idCliente) {
        IdCliente = idCliente;
    }

    public String getIdVenderor() {
        return IdVenderor;
    }

    public void setIdVenderor(String idVenderor) {
        IdVenderor = idVenderor;
    }

    public String getValorVenta() {
        return ValorVenta;
    }

    public void setValorVenta(String valorVenta) {
        ValorVenta = valorVenta;
    }

    public Integer getNumeroCuotas() {
        return NumeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        NumeroCuotas = numeroCuotas;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }
}
