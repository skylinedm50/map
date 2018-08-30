package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class Pagos {
    @SerializedName("pag_codigo")
    private String pag_codigo;

    @SerializedName("pag_nombre")
    private String pag_nombre;

    public Pagos(String pag_codigo, String pag_nombre) {
        this.pag_codigo = pag_codigo;
        this.pag_nombre = pag_nombre;
    }

    public String getPag_codigo() {
        return pag_codigo;
    }

    public String getPag_nombre() {
        return pag_nombre;
    }
}
