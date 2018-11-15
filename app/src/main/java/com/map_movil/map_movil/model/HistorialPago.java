package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class HistorialPago extends RealmObject {
    @SerializedName("pag_codigo")
    private int pag_codigo;
    @SerializedName("pag_anyo")
    private int pag_anyo;
    @SerializedName("pag_nombre")
    private String pag_nombre;
    @SerializedName("tit_hogar")
    private int tit_hogar;
    @SerializedName("nombre_Titular")
    private String nombre_Titular;
    @SerializedName("estado_Pago")
    private String estado_Pago;
    @SerializedName("tit_fecha_cobro")
    private String tit_fecha_cobro;
    @SerializedName("tit_proy_corta")
    private String tit_proy_corta;
    @SerializedName("tit_planilla")
    private Boolean tit_planilla;
    @SerializedName("monto")
    private int monto;

    public HistorialPago(){}

    public HistorialPago(int pag_codigo, int pag_anyo, String pag_nombre, int tit_hogar, String nombre_Titular, String estado_Pago, String tit_fecha_cobro, String tit_proy_corta, Boolean tit_planilla, int monto) {
        this.pag_codigo = pag_codigo;
        this.pag_anyo = pag_anyo;
        this.pag_nombre = pag_nombre;
        this.tit_hogar = tit_hogar;
        this.nombre_Titular = nombre_Titular;
        this.estado_Pago = estado_Pago;
        this.tit_fecha_cobro = tit_fecha_cobro;
        this.tit_proy_corta = tit_proy_corta;
        this.tit_planilla = tit_planilla;
        this.monto = monto;
    }

    public int getPag_anyo() {
        return pag_anyo;
    }

    public void setPag_anyo(int pag_anyo) {
        this.pag_anyo = pag_anyo;
    }

    public String getPag_nombre() {
        return pag_nombre;
    }

    public void setPag_nombre(String pag_nombre) {
        this.pag_nombre = pag_nombre;
    }

    public int getTit_hogar() {
        return tit_hogar;
    }

    public void setTit_hogar(int tit_hogar) {
        this.tit_hogar = tit_hogar;
    }

    public String getNombre_Titular() {
        return nombre_Titular;
    }

    public void setNombre_Titular(String nombre_Titular) {
        this.nombre_Titular = nombre_Titular;
    }

    public String getEstado_Pago() {
        return estado_Pago;
    }

    public void setEstado_Pago(String estado_Pago) {
        this.estado_Pago = estado_Pago;
    }

    public String getTit_fecha_cobro() {
        String FC= tit_fecha_cobro;
        if (FC==null)
        {
            FC="ND";
        }
        return FC;
    }

    public void setTit_fecha_cobro(String tit_fecha_cobro) {
        this.tit_fecha_cobro = tit_fecha_cobro;
    }

    public String getTit_proy_corta() {
        return tit_proy_corta;
    }

    public void setTit_proy_corta(String tit_proy_corta) {
        this.tit_proy_corta = tit_proy_corta;
    }

    public int getPag_codigo() {
        return pag_codigo;
    }

    public void setPag_codigo(int pag_codigo) {
        this.pag_codigo = pag_codigo;
    }

    public Boolean getTit_planilla() {
        return tit_planilla;
    }

    public void setTit_planilla(Boolean tit_planilla) {
        this.tit_planilla = tit_planilla;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
}
