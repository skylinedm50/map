package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class HogaresValidar {

    @SerializedName("per_persona")
    private int per_persona;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("per_estado_descripcion")
    private String per_estado_descripcion;
    @SerializedName("sexo")
    private String sexo;
    @SerializedName("edad")
    private String edad;
    @SerializedName("per_ciclo")
    private int per_ciclo;
    @SerializedName("per_titular")
    private int per_titular;
    @SerializedName("hog_hogar")
    private int hog_hogar;
    @SerializedName("hog_umbral")
    private String hog_umbral;
    @SerializedName("cod_departamento")
    private String cod_departamento;
    @SerializedName("desc_departamento")
    private String desc_departamento;
    @SerializedName("cod_municipio")
    private String cod_municipio;
    @SerializedName("desc_municipio")
    private String desc_municipio;
    @SerializedName("cod_aldea")
    private String cod_aldea;
    @SerializedName("desc_aldea")
    private String desc_aldea;
    @SerializedName("cod_caserio")
    private String cod_caserio;
    @SerializedName("desc_caserio")
    private String desc_caserio;
    @SerializedName("hogar_direccion")
    private String hogar_direccion;
    @SerializedName("hog_telefono")
    private String hog_telefono;



    public int getPer_persona() {
        return per_persona;
    }

    public void setPer_persona(int per_persona) {
        this.per_persona = per_persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPer_estado_descripcion() {
        return per_estado_descripcion;
    }

    public void setPer_estado_descripcion(String per_estado_descripcion) {
        this.per_estado_descripcion = per_estado_descripcion;
    }

    public String getHog_umbral() {
        return hog_umbral;
    }

    public void setHog_umbral(String hog_umbral) {
        this.hog_umbral = hog_umbral;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public int getPer_ciclo() {
        return per_ciclo;
    }

    public void setPer_ciclo(int per_ciclo) {
        this.per_ciclo = per_ciclo;
    }

    public int getPer_titular() {
        return per_titular;
    }

    public void setPer_titular(int per_titular) {
        this.per_titular = per_titular;
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
}
