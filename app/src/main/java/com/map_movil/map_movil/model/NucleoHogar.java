package com.map_movil.map_movil.model;

public class NucleoHogar {
    private String per_identidad;
    private String nombre_completo;
    private String per_fch_nacimiento;
    private String sexoD;
    private String per_estado_descripcion;
    private int per_titular;

    public String getPer_identidad() {
        return per_identidad;
    }

    public void setPer_identidad(String per_identidad) {
        this.per_identidad = per_identidad;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getPer_fch_nacimiento() {
        return per_fch_nacimiento;
    }

    public void setPer_fch_nacimiento(String per_fch_nacimiento) {
        this.per_fch_nacimiento = per_fch_nacimiento;
    }

    public String getSexoD() {
        return sexoD;
    }

    public void setSexoD(String sexoD) {
        this.sexoD = sexoD;
    }

    public String getPer_estado_descripcion() {
        return per_estado_descripcion;
    }

    public void setPer_estado_descripcion(String per_estado_descripcion) {
        this.per_estado_descripcion = per_estado_descripcion;
    }

    public String getPer_titular() {
        if(per_titular==1){
            return "Sí";
        }
        else if (per_titular==0){
            return "No";
        }
        return "";
    }

    public void setPer_titular(int per_titular) {
        this.per_titular = per_titular;
    }
}
