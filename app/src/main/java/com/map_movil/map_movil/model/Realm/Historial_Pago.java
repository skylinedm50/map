package com.map_movil.map_movil.model.Realm;

import io.realm.RealmObject;

public class Historial_Pago extends RealmObject {

    private int pag_anyo;
    private String pag_nombre;
    private int tit_hogar;
    private String nombre_Titular;
    private String estado_Pago;
    private String tit_fecha_cobro;
    private String tit_proy_corta;

    public Historial_Pago(){}

    public Historial_Pago(int pag_anyo          , String pag_nombre         ,
                          int tit_hogar         , String nombre_Titular     ,
                          String estado_Pago    , String tit_fecha_cobro    ,
                          String tit_proy_corta){

        this.pag_anyo = pag_anyo;
        this.pag_nombre = pag_nombre;
        this.tit_hogar = tit_hogar;
        this.nombre_Titular = nombre_Titular;
        this.estado_Pago = estado_Pago;
        this.tit_fecha_cobro = tit_fecha_cobro;
        this.tit_proy_corta = tit_proy_corta;
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
        return tit_fecha_cobro;
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
}
