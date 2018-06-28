package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class Municipios {

    @SerializedName("cod_municipio")
    private String cod_municipio;
    @SerializedName("desc_municipio")
    private String desc_municipio;
    @SerializedName("cod_departamento")
    private String cod_departamento;


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

    public String getCod_departamento() {
        return cod_departamento;
    }

    public void setCod_departamento(String cod_departamento) {
        this.cod_departamento = cod_departamento;
    }
}
