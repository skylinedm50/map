package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Hogares_Programados extends RealmObject {
    @SerializedName("monto")
    private int monto;
    @SerializedName("hogar")
    private int hogar;
    @SerializedName("pago")
    private String pago_nombre;



    public int getMonto() {
        return monto;
    }
    public void setMonto(int monto) {
        this.monto = monto;
    }
    public int getHogar() {
        return hogar;
    }
    public void setHogar(int hogar) {
        this.hogar = hogar;
    }
    public String getPago_nombre() {
        return pago_nombre;
    }
    public void setPago_nombre(String pago_nombre) {
        this.pago_nombre = pago_nombre;
    }
}
