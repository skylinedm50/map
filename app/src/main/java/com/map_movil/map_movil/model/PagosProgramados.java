package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class PagosProgramados {
    @SerializedName("pag_nombre")
    private String strPago_nombre;

    @SerializedName("tit_hogar")
    private String strcodigo_hogar;

    @SerializedName("cod_departamento")
    private String strcod_departamento;

    @SerializedName("desc_departamento")
    private String strdesc_departamento;

    @SerializedName("cod_municipio")
    private String strcod_municipio;

    @SerializedName("desc_municipio")
    private String strdesc_municipio;

    @SerializedName("desc_caserio")
    private String strdesc_caserio;

    @SerializedName("desc_aldea")
    private String strdesc_aldea;

    @SerializedName("Nombre_Titular")
    private String strnombre_titular;

    @SerializedName("tit_identidad")
    private String stridentidad_titular;

    @SerializedName("tit_monto_total_red")
    private String intmonto_pagar;

    public PagosProgramados(String strPago_nombre, String strcodigo_hogar, String strcod_departamento, String strdesc_departamento, String strcod_municipio, String strdesc_municipio,
                            String strdesc_caserio, String strdesc_aldea, String strnombre_titular , String stridentidad_titular , String intmonto_pagar) {
        this.strPago_nombre = strPago_nombre;
        this.strcodigo_hogar = strcodigo_hogar;
        this.strcod_departamento = strcod_departamento;
        this.strdesc_departamento = strdesc_departamento;
        this.strcod_municipio = strcod_municipio;
        this.strdesc_municipio = strdesc_municipio;
        this.strdesc_caserio = strdesc_caserio;
        this.strdesc_aldea = strdesc_aldea;
        this.strnombre_titular = strnombre_titular;
        this.stridentidad_titular = stridentidad_titular;
        this.intmonto_pagar = intmonto_pagar;
    }

    public String getStrPago_nombre() {
        return strPago_nombre;
    }

    public String getStrcodigo_hogar() {
        return strcodigo_hogar;
    }

    public String getStrcod_departamento() {
        return strcod_departamento;
    }

    public String getStrdesc_departamento() {
        return strdesc_departamento;
    }

    public String getStrcod_municipio() {
        return strcod_municipio;
    }

    public String getStrdesc_municipio() {
        return strdesc_municipio;
    }

    public String getStrdesc_caserio() {
        return strdesc_caserio;
    }

    public String getStrdesc_aldea() {
        return strdesc_aldea;
    }

    public String getStrnombre_titular() {
        return strnombre_titular;
    }

    public String getStridentidad_titular() {
        return stridentidad_titular;
    }

    public void setStridentidad_titular(String stridentidad_titular) {
        this.stridentidad_titular = stridentidad_titular;
    }

    public String getIntmonto_pagar() {
        return intmonto_pagar;
    }

    public void setIntmonto_pagar(String intmonto_pagar) {
        this.intmonto_pagar = intmonto_pagar;
    }

}
