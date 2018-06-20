package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class HogarLigth {
    @SerializedName("nombre_beneficiario")
    private String strNombreBeneficiario;

    @SerializedName("edad")
    private int intEdad;

    @SerializedName("sexo")
    private String strSexo;

    @SerializedName("identidad")
    private String strIdentidad;

    @SerializedName("titular")
    private boolean bolTitular;

    public HogarLigth(String strNombreBeneficiario, int intEdad, String strSexo, String strIdentidad, boolean bolTitular) {
        this.strNombreBeneficiario = strNombreBeneficiario;
        this.intEdad = intEdad;
        this.strSexo = strSexo;
        this.strIdentidad = strIdentidad;
        this.bolTitular = bolTitular;
    }

    public String getStrNombreBeneficiario() {
        return strNombreBeneficiario;
    }

    public void setStrNombreBeneficiario(String strNombreBeneficiario) {
        this.strNombreBeneficiario = strNombreBeneficiario;
    }

    public int getIntEdad() {
        return intEdad;
    }

    public void setIntEdad(int intEdad) {
        this.intEdad = intEdad;
    }

    public String getStrSexo() {
        return strSexo;
    }

    public void setStrSexo(String strSexo) {
        this.strSexo = strSexo;
    }

    public String getStrIdentidad() {
        return strIdentidad;
    }

    public void setStrIdentidad(String strIdentidad) {
        this.strIdentidad = strIdentidad;
    }

    public boolean isBolTitular() {
        return bolTitular;
    }

    public void setBolTitular(boolean bolTitular) {
        this.bolTitular = bolTitular;
    }
}
