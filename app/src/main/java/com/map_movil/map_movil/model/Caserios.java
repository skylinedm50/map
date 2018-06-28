package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class Caserios {

    @SerializedName("cod_caserio")
    private String cod_caserio;
    @SerializedName("desc_caserio")
    private String desc_caserio;
    @SerializedName("cod_aldea")
    private String cod_aldea;

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

    public String getCod_aldea() {
        return cod_aldea;
    }

    public void setCod_aldea(String cod_aldea) {
        this.cod_aldea = cod_aldea;
    }
}
