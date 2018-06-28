package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class Aldeas {

    @SerializedName("cod_aldea")
    private String cod_aldea;
    @SerializedName("desc_aldea")
    private String desc_aldea;
    @SerializedName("cod_municipio")
    private String cod_municipio;


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

    public String getCod_municipio() {
        return cod_municipio;
    }

    public void setCod_municipio(String cod_municipio) {
        this.cod_municipio = cod_municipio;
    }
}
