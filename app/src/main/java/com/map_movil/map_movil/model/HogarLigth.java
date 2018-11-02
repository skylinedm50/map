package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class HogarLigth {
    @SerializedName("hog_hogar")
    private int intHogHogar;

    @SerializedName("per_persona")
    private int intPerPersona;

    @SerializedName("nombre_beneficiario")
    private String strNombreBeneficiario;

    @SerializedName("edad")
    private String strEdad;

    @SerializedName("sexo")
    private String strSexo;

    @SerializedName("identidad")
    private String strIdentidad;

    @SerializedName("titular")
    private boolean bolTitular;

    public HogarLigth(String strNombreBeneficiario, String strEdad, String strSexo, String strIdentidad, boolean bolTitular) {
        this.strNombreBeneficiario = strNombreBeneficiario;
        this.strEdad = strEdad;
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

    public String getStrEdad() {
        return strEdad;
    }

    public void setStrEdad(String strEdad) {
        this.strEdad = strEdad;
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

    public int getIntHogHogar() {
        return intHogHogar;
    }

    public void setIntHogHogar(int intHogHogar) {
        this.intHogHogar = intHogHogar;
    }

    public int getIntPerPersona() {
        return intPerPersona;
    }

    public void setIntPerPersona(int intPerPersona) {
        this.intPerPersona = intPerPersona;
    }
}
