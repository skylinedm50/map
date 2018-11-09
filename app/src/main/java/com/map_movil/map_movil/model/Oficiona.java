package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class Oficiona {
    @SerializedName("codigo_oficina")
    private int intCodigo;

    @SerializedName("oficina")
    private String strNombre;

    public Oficiona() {
    }

    public Oficiona(int intCodigo, String strNombre) {
        this.intCodigo = intCodigo;
        this.strNombre = strNombre;
    }

    public int getIntCodigo() {
        return intCodigo;
    }

    public void setIntCodigo(int intCodigo) {
        this.intCodigo = intCodigo;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }
}
