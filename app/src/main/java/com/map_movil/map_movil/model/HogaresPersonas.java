package com.map_movil.map_movil.model;

import io.realm.RealmObject;

public class HogaresPersonas extends RealmObject {
    private int per_persona;
    private String per_identidad;
    private String nombre;
    private String per_estado_descripcion;
    private String sexo;
    private String edad;
    private int per_ciclo;
    private int per_titular;
    private int hog_hogar;
    private String hog_umbral;
    private String cod_departamento;
    private String desc_departamento;
    private String cod_municipio;
    private String desc_municipio;
    private String cod_aldea;
    private String desc_aldea;
    private String cod_caserio;
    private String desc_caserio;
    private String hogar_direccion;
    private String hog_telefono;
    private String hog_estado_descripcion;


    public HogaresPersonas(){}

    public HogaresPersonas(int per_persona        , String nombre            , String per_estado_descripcion ,
                           String hog_umbral      , String sexo              , String edad                   ,
                           int per_ciclo          , int per_titular          , int hog_hogar                 ,
                           String cod_departamento, String desc_departamento , String cod_municipio          ,
                           String desc_municipio  , String cod_aldea         , String desc_aldea             ,
                           String cod_caserio     , String desc_caserio      , String hogar_direccion        ,
                           String hog_telefono    , String per_identidad     , String hog_estado_descripcion )
    {
        this.per_persona = per_persona;
        this.nombre = nombre;
        this.per_estado_descripcion = per_estado_descripcion;
        this.hog_umbral = hog_umbral;
        this.sexo = sexo;
        this.edad = edad;
        this.per_ciclo = per_ciclo;
        this.per_titular = per_titular;
        this.hog_hogar = hog_hogar;
        this.cod_departamento = cod_departamento;
        this.desc_departamento = desc_departamento;
        this.cod_municipio = cod_municipio;
        this.desc_municipio = desc_municipio;
        this.cod_aldea = cod_aldea;
        this.desc_aldea = desc_aldea;
        this.cod_caserio = cod_caserio;
        this.desc_caserio = desc_caserio;
        this.hogar_direccion = hogar_direccion;
        this.hog_telefono = hog_telefono;
        this.per_identidad = per_identidad;
        this.hog_estado_descripcion = hog_estado_descripcion;
    }

    public int getPer_persona() {
        return per_persona;
    }
    public String getNombre() {
        return nombre;
    }
    public String getPer_estado_descripcion() {
        return per_estado_descripcion;
    }
    public String getHog_umbral() {
        return hog_umbral;
    }
    public String getSexo() {
        String sexoM = sexo;
        if (sexoM==null)
        {
            sexoM="ND";
        }
        return sexoM;
    }
    public String getEdad() {
        String EdadM = edad;
        if (EdadM==null)
        {
            EdadM="ND";
        }

        return EdadM;
    }
    public int getPer_ciclo() {
        return per_ciclo;
    }
    public int getPer_titular() {
        return per_titular;
    }
    public int getHog_hogar() {
        return hog_hogar;
    }
    public void setHog_hogar(int hog_hogar) {
        this.hog_hogar = hog_hogar;
    }
    public String getCod_departamento() {
        return cod_departamento;
    }
    public void setCod_departamento(String cod_departamento) {
        this.cod_departamento = cod_departamento;
    }
    public String getDesc_departamento() {
        return desc_departamento;
    }
    public void setDesc_departamento(String desc_departamento) {
        this.desc_departamento = desc_departamento;
    }
    public String getCod_municipio() {
        return cod_municipio;
    }
    public void setCod_municipio(String cod_municipio) {
        this.cod_municipio = cod_municipio;
    }
    public String getDesc_municipio() {
        return desc_municipio;
    }
    public void setDesc_municipio(String desc_municipio) {
        this.desc_municipio = desc_municipio;
    }
    public String getCod_aldea() {
        return cod_aldea;
    }
    public void setCod_aldea(String cod_aldea) {
        this.cod_aldea = cod_aldea;
    }
    public String getDesc_aldea() {
        return desc_aldea;
    }
    public void setDesc_aldea(String desc_aldea) {
        this.desc_aldea = desc_aldea;
    }
    public String getCod_caserio() {
        return cod_caserio;
    }
    public void setCod_caserio(String cod_caserio) {
        this.cod_caserio = cod_caserio;
    }
    public String getDesc_caserio() {
        return desc_caserio;
    }
    public void setDesc_caserio(String desc_caserio) {
        this.desc_caserio = desc_caserio;
    }
    public String getHogar_direccion() {
        return hogar_direccion;
    }
    public void setHogar_direccion(String hogar_direccion) {
        this.hogar_direccion = hogar_direccion;
    }
    public String getHog_telefono() {
        return hog_telefono;
    }
    public void setHog_telefono(String hog_telefono) {
        this.hog_telefono = hog_telefono;
    }
    public String getPer_identidad() {
        return per_identidad;
    }
    public void setPer_identidad(String per_identidad) {
        this.per_identidad = per_identidad;
    }
    public String getHog_estado_descripcion() {
        return hog_estado_descripcion;
    }
    public void setHog_estado_descripcion(String hog_estado_descripcion) {
        this.hog_estado_descripcion = hog_estado_descripcion;
    }
}
