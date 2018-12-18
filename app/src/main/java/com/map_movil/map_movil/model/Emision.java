package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Emision{
    @SerializedName("codigo")
    private int intCodEmision;
    @SerializedName("fecha")
    private String strFecha;
    @SerializedName("mensaje")
    private String strMensaje;
    @SerializedName("tipo")
    private String strTipoEmision;


    public Emision() {
    }



    public int getIntCodEmision() {
        return intCodEmision;
    }

    public void setIntCodEmision(int intCodEmision) {
        this.intCodEmision = intCodEmision;
    }

    public String getStrFecha() {
        return strFecha;
    }

    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
    }

    public String getStrMensaje() {
        return strMensaje;
    }

    public void setStrMensaje(String strMensaje) {
        this.strMensaje = strMensaje;
    }

    public String getStrTipoEmision() {
        return strTipoEmision;
    }

    public void setStrTipoEmision(String strTipoEmision) {
        this.strTipoEmision = strTipoEmision;
    }
}
