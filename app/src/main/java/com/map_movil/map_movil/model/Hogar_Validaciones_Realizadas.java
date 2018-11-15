package com.map_movil.map_movil.model;

import io.realm.RealmObject;

public class Hogar_Validaciones_Realizadas extends RealmObject {

    private int hog_hogar;
    private int per_persona;
    private int tarjeta_identidad;
    private int acta_compromiso;
    private int actualizar_datos;
    private int partidad_nacimiento;
    private int constancia_educacion;
    private int desagregar;
    private int debe_documentos;
    private int incorporacion;
    private int cambio_titular;


    public Hogar_Validaciones_Realizadas(){}

    public Hogar_Validaciones_Realizadas( int hog_hogar , int per_persona , int tarjeta_identidad ,
                                          int acta_compromiso , int actualizar_datos , int partidad_nacimiento ,
                                          int constancia_educacion , int desagregar , int debe_documentos ,
                                          int incorporacion , int cambio_titular){
        this.hog_hogar = hog_hogar;
        this.per_persona = per_persona;
        this.tarjeta_identidad = tarjeta_identidad;
        this.acta_compromiso = acta_compromiso;
        this.acta_compromiso = actualizar_datos;
        this.partidad_nacimiento = partidad_nacimiento;
        this.constancia_educacion = constancia_educacion;
        this.desagregar = desagregar;
        this.debe_documentos = debe_documentos;
        this.incorporacion = incorporacion;
        this.cambio_titular = cambio_titular;
    }

    public int getHog_hogar() {
        return hog_hogar;
    }
    public void setHog_hogar(int hog_hogar) {
        this.hog_hogar = hog_hogar;
    }
    public int getPer_persona() {
        return per_persona;
    }
    public void setPer_persona(int per_persona) {
        this.per_persona = per_persona;
    }
    public int getTarjeta_identidad() {
        return tarjeta_identidad;
    }
    public void setTarjeta_identidad(int tarjeta_identidad) {
        this.tarjeta_identidad = tarjeta_identidad;
    }
    public int getActa_compromiso() {
        return acta_compromiso;
    }
    public void setActa_compromiso(int acta_compromiso) {
        this.acta_compromiso = acta_compromiso;
    }
    public int getActualizar_datos() {
        return actualizar_datos;
    }
    public void setActualizar_datos(int actualizar_datos) {
        this.actualizar_datos = actualizar_datos;
    }
    public int getPartidad_nacimiento() {
        return partidad_nacimiento;
    }
    public void setPartidad_nacimiento(int partidad_nacimiento) {
        this.partidad_nacimiento = partidad_nacimiento;
    }
    public int getConstancia_educacion() {
        return constancia_educacion;
    }
    public void setConstancia_educacion(int constancia_educacion) {
        this.constancia_educacion = constancia_educacion;
    }
    public int getDesagregar() {
        return desagregar;
    }
    public void setDesagregar(int desagregar) {
        this.desagregar = desagregar;
    }
    public int getDebe_documentos() {
        return debe_documentos;
    }
    public void setDebe_documentos(int debe_documentos) {
        this.debe_documentos = debe_documentos;
    }
    public int getIncorporacion() {
        return incorporacion;
    }
    public void setIncorporacion(int incorporacion) {
        this.incorporacion = incorporacion;
    }
    public int getCambio_titular() {
        return cambio_titular;
    }
    public void setCambio_titular(int cambio_titular) {
        this.cambio_titular = cambio_titular;
    }
}
