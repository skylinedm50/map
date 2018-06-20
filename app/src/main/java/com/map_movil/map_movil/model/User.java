package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("codigo")
    private Integer intCodigo;

    @SerializedName("nombre")
    private String strNombre;

    @SerializedName("apellido")
    private String strsApellido;

    @SerializedName("estado")
    private Integer intEstado;

    @SerializedName("cantidad_login")
    private Integer intCantidadLogin;


    public User(Integer intCodigo, String strNombre, String strsApellido, Integer intEstado, Integer intCantidadLogin) {
        this.intCodigo = intCodigo;
        this.strNombre = strNombre;
        this.strsApellido = strsApellido;
        this.intEstado = intEstado;
        this.intCantidadLogin = intCantidadLogin;
    }

    public Integer getIntCodigo() {
        return intCodigo;
    }

    public void setIntCodigo(Integer intCodigo) {
        this.intCodigo = intCodigo;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getStrsApellido() {
        return strsApellido;
    }

    public void setStrsApellido(String strsApellido) {
        this.strsApellido = strsApellido;
    }

    public Integer getIntEstado() {
        return intEstado;
    }

    public void setIntEstado(Integer intEstado) {
        this.intEstado = intEstado;
    }

    public Integer getIntCantidadLogin() {
        return intCantidadLogin;
    }

    public void setIntCantidadLogin(Integer intCantidadLogin) {
        this.intCantidadLogin = intCantidadLogin;
    }
}
