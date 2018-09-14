package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class HogarByTitular {
    @SerializedName("cod_hogar")
    private int intCodHogar;
    @SerializedName("estado_hogar")
    private String strEstadoHogar;
    @SerializedName("umbral_hogar")
    private String strUmbralHogar;
    @SerializedName("departamento")
    private String strDepartamento;
    @SerializedName("municipio")
    private String strMunicipio;
    @SerializedName("aldea")
    private String strAldea;
    @SerializedName("caserio")
    private String strCaserio;

    public HogarByTitular(){}

    public HogarByTitular(int intCodHogar, String strEstadoHogar, String strUmbralHogar, String strDepartamento, String strMunicipio, String strAldea, String strCaserio) {
        this.intCodHogar = intCodHogar;
        this.strEstadoHogar = strEstadoHogar;
        this.strUmbralHogar = strUmbralHogar;
        this.strDepartamento = strDepartamento;
        this.strMunicipio = strMunicipio;
        this.strAldea = strAldea;
        this.strCaserio = strCaserio;
    }

    public int getIntCodHogar() {
        return intCodHogar;
    }

    public void setIntCodHogar(int intCodHogar) {
        this.intCodHogar = intCodHogar;
    }

    public String getStrEstadoHogar() {
        return strEstadoHogar;
    }

    public void setStrEstadoHogar(String strEstadoHogar) {
        this.strEstadoHogar = strEstadoHogar;
    }

    public String getStrUmbralHogar() {
        return strUmbralHogar;
    }

    public void setStrUmbralHogar(String strUmbralHogar) {
        this.strUmbralHogar = strUmbralHogar;
    }

    public String getStrDepartamento() {
        return strDepartamento;
    }

    public void setStrDepartamento(String strDepartamento) {
        this.strDepartamento = strDepartamento;
    }

    public String getStrMunicipio() {
        return strMunicipio;
    }

    public void setStrMunicipio(String strMunicipio) {
        this.strMunicipio = strMunicipio;
    }

    public String getStrAldea() {
        return strAldea;
    }

    public void setStrAldea(String strAldea) {
        this.strAldea = strAldea;
    }

    public String getStrCaserio() {
        return strCaserio;
    }

    public void setStrCaserio(String strCaserio) {
        this.strCaserio = strCaserio;
    }
}
