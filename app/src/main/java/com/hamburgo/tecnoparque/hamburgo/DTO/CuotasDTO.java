package com.hamburgo.tecnoparque.hamburgo.DTO;

/**
 * Created by YOLIMA on 13/07/2016.
 */
public class CuotasDTO {

    private Integer NumeroCuota;
    private Integer NumeroVenta;
    private String FechaPago;
    private Integer ValorCuota;
    private Integer Pagada;

    public Integer getNumeroCuota() {
        return NumeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        NumeroCuota = numeroCuota;
    }

    public Integer getNumeroVenta() {
        return NumeroVenta;
    }

    public void setNumeroVenta(Integer numeroVenta) {
        NumeroVenta = numeroVenta;
    }

    public String getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(String fechaPago) {
        FechaPago = fechaPago;
    }

    public Integer getValorCuota() {
        return ValorCuota;
    }

    public void setValorCuota(Integer valorCuota) {
        ValorCuota = valorCuota;
    }

    public Integer getPagada() {
        return Pagada;
    }

    public void setPagada(Integer pagada) {
        Pagada = pagada;
    }
}
