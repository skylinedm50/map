package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("codigo")
    private Number intCodigo;

    @SerializedName("nombre")
    private String strNombre;

    @SerializedName("apellido")
    private String strsApellido;

    @SerializedName("estado")
    private Number intEstado;

    @SerializedName("cantidad_login")
    private Number intCantidadLogin;


    public User(Number intCodigo, String strNombre, String strsApellido, Number intEstado, Number intCantidadLogin) {
        this.intCodigo = intCodigo;
        this.strNombre = strNombre;
        this.strsApellido = strsApellido;
        this.intEstado = intEstado;
        this.intCantidadLogin = intCantidadLogin;
    }

    public Number getIntCodigo() {
        return intCodigo;
    }

    public void setIntCodigo(Number intCodigo) {
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

    public Number getIntEstado() {
        return intEstado;
    }

    public void setIntEstado(Number intEstado) {
        this.intEstado = intEstado;
    }

    public Number getIntCantidadLogin() {
        return intCantidadLogin;
    }

    public void setIntCantidadLogin(Number intCantidadLogin) {
        this.intCantidadLogin = intCantidadLogin;
    }
}
