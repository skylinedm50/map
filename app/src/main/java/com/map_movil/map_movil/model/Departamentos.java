package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class Departamentos {

    @SerializedName("cod_departamento")
    private String cod_departamento;
    @SerializedName("desc_departamento")
    private String desc_departamento;

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
}
