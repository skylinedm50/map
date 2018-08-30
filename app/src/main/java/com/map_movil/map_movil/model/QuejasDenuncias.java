package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class QuejasDenuncias
{

    @SerializedName("codigo_solicitud")
    private int codigo_solicitud;
    @SerializedName("fecha_alta")
    private String fecha_alta;
    @SerializedName("observacion")
    private String observacion;
    @SerializedName("tipo_gestion")
    private String tipo_gestion;
    @SerializedName("estado")
    private String estado;
    @SerializedName("identidad")
    private String identidad;
    @SerializedName("codigo_solicitante")
    private String codigo_solicitante;
    @SerializedName("nombre_solicitante")
    private String nombre_solicitante;


    public int getCodigo_solicitud() {
        return codigo_solicitud;
    }
    public void setCodigo_solicitud(int codigo_solicitud) {
        this.codigo_solicitud = codigo_solicitud;
    }
    public String getFecha_alta() {
        return fecha_alta;
    }
    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }
    public String getObservacion() {
        return observacion;
    }
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    public String getTipo_gestion() {
        return tipo_gestion;
    }
    public void setTipo_gestion(String tipo_gestion) {
        this.tipo_gestion = tipo_gestion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getIdentidad() {
        return identidad;
    }
    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }
    public String getCodigo_solicitante() {
        return codigo_solicitante;
    }
    public void setCodigo_solicitante(String codigo_solicitante) {
        this.codigo_solicitante = codigo_solicitante;
    }
    public String getNombre_solicitante() {
        return nombre_solicitante;
    }
    public void setNombre_solicitante(String nombre_solicitante) {
        this.nombre_solicitante = nombre_solicitante;
    }

}
