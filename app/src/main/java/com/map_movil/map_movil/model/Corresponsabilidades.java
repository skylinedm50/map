package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class Corresponsabilidades {
    @SerializedName("per_persona")
    private String Per_persona;

    @SerializedName("nombre_titular")
    private String strTitular;

    @SerializedName("nombre")
    private String strNombre;

    @SerializedName("per_identidad")
    private String strIdentidad;

    @SerializedName("per_fch_nacimiento")
    private String dateBirth;

    @SerializedName("sexo")
    private String strSexo;

    @SerializedName("per_hogar")
    private String srtHogar;

    @SerializedName("corresponsabilidad")
    private String strCorresponsabilidad;

    @SerializedName("dias_faltados")
    private String strDiasfaltantes;

    @SerializedName("estado_matricula")
    private String strMatricula;

    @SerializedName("centro")
    private String strEdCentro;

    @SerializedName("año_registro")
    private String strYear;

    @SerializedName("fecha_visita_medica")
    private String strVisita;

    @SerializedName("fecha_ingreso_sistema_visita_medica")
    private String strIngresoVIsita;

    @SerializedName("estado_corresponsabilidad")
    private String strEstadoCorr;

    @SerializedName("codigo_visita_medica")
    private String strCodVisita;

    @SerializedName("per_edad")
    private String strEdad;

    public Corresponsabilidades(String per_persona, String strTitular, String strNombre, String strIdentidad, String dateBirth, String strSexo, String srtHogar, String strCorresponsabilidad, String strDiasfaltantes, String strMatricula, String strEdCentro, String strYear, String strVisita, String strIngresoVIsita, String strEstadoCorr, String strCodVisita, String strEdad) {
        Per_persona = per_persona;
        this.strTitular = strTitular;
        this.strNombre = strNombre;
        this.strIdentidad = strIdentidad;
        this.dateBirth = dateBirth;
        this.strSexo = strSexo;
        this.srtHogar = srtHogar;
        this.strCorresponsabilidad = strCorresponsabilidad;
        this.strDiasfaltantes = strDiasfaltantes;
        this.strMatricula = strMatricula;
        this.strEdCentro = strEdCentro;
        this.strYear = strYear;
        this.strVisita = strVisita;
        this.strIngresoVIsita = strIngresoVIsita;
        this.strEstadoCorr = strEstadoCorr;
        this.strCodVisita = strCodVisita;
        this.strEdad = strEdad;
    }

    public String getPer_persona() {
        return Per_persona;
    }

    public void setPer_persona(String per_persona) {
        Per_persona = per_persona;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getStrEdad() { return strEdad; }

    public void setStrEdad(String strEdad) {this.strEdad = strEdad; }

    public String getStrIdentidad() {
        return strIdentidad;
    }

    public void setStrIdentidad(String strIdentidad) {
        this.strIdentidad = strIdentidad;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getStrSexo() {
        return strSexo;
    }

    public void setStrSexo(String strSexo) {
        this.strSexo = strSexo;
    }

    public String getSrtHogar() {
        return srtHogar;
    }

    public void setSrtHogar(String srtHogar) {
        this.srtHogar = srtHogar;
    }

    public String getStrCorresponsabilidad() {
        return strCorresponsabilidad;
    }

    public void setStrCorresponsabilidad(String strCorresponsabilidad) {
        this.strCorresponsabilidad = strCorresponsabilidad;
    }


    public String getStrDiasfaltantes() {
        return strDiasfaltantes;
    }

    public void setStrDiasfaltantes(String strDiasfaltantes) {
        this.strDiasfaltantes = strDiasfaltantes;
    }

    public String getStrMatricula() {
        return strMatricula;
    }

    public void setStrMatricula(String strMatricula) {
        this.strMatricula = strMatricula;
    }

    public String getStrEdCentro() {
        return strEdCentro;
    }

    public void setStrEdCentro(String strEdCentro) {
        this.strEdCentro = strEdCentro;
    }

    public String getStrYear() {
        return strYear;
    }

    public void setStrYear(String strYear) {
        this.strYear = strYear;
    }

    public String getStrVisita() {
        return strVisita;
    }

    public void setStrVisita(String strVisita) {
        this.strVisita = strVisita;
    }

    public String getStrTitular() {
        return strTitular;
    }

    public void setStrTitular(String strTitular) {
        this.strTitular = strTitular;
    }

    public String getStrIngresoVIsita() {
        return strIngresoVIsita;
    }

    public void setStrIngresoVIsita(String strIngresoVIsita) {
        this.strIngresoVIsita = strIngresoVIsita;
    }

    public String getStrEstadoCorr() {
        return strEstadoCorr;
    }

    public void setStrEstadoCorr(String strEstadoCorr) {
        this.strEstadoCorr = strEstadoCorr;
    }

    public String getStrCodVisita() {
        return strCodVisita;
    }

    public void setStrCodVisita(String strCodVisita) {
        this.strCodVisita = strCodVisita;
    }

}
