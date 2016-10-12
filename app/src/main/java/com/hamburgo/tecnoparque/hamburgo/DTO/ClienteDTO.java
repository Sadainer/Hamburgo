package com.hamburgo.tecnoparque.hamburgo.DTO;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by YOLIMA on 13/07/2016.
 */

@IgnoreExtraProperties
public class ClienteDTO {

    private String Cedula;
    private String Nombres;
    private String Apellidos;
    private String Direccion;
    private String Empleo;
    private String Empresa;
    private String Celular;

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getEmpleo() {
        return Empleo;
    }

    public void setEmpleo(String empleo) {
        Empleo = empleo;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }
}
