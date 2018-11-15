package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import io.realm.RealmObject;

public class QuejasDenuncias extends RealmObject
{

    @SerializedName("codigo_solicitud")
    private int codigo_solicitud;
    @SerializedName("fecha_alta")
    private Date fecha_alta;
    @SerializedName("observacion")
    private String observacion;
    @SerializedName("tipo_gestion")
    private String tipo_gestion;
    @SerializedName("estado")
    private String estado;
    @SerializedName("identidad")
    private String identidad;
    @SerializedName("codigo_solicitante")
    private int codigo_solicitante;
    @SerializedName("nombre_solicitante")
    private String nombre_solicitante;
    @SerializedName("cod_caserio")
    private String cod_caserio;
    /*----------- REGISTRO DE QUEJAS OFFLINE ----------*/
    @SerializedName("Telefono_np")
    private String telefono;
    @SerializedName("ANONIMO")
    private int  anonimo;
    private int  Offline;
    private int  codigo_gestion;


    public int getCodigo_gestion() {
        return codigo_gestion;
    }
    public void setCodigo_gestion(int codigo_gestion) {
        this.codigo_gestion = codigo_gestion;
    }
    public int getOffline() {
        return Offline;
    }
    public void setOffline(int offline) {
        Offline = offline;
    }
    public int getAnonimo() {
        return anonimo;
    }
    public void setAnonimo(int anonimo) {
        this.anonimo = anonimo;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCaserio() {
        return cod_caserio;
    }
    public void setCaserio(String caserio) {
        this.cod_caserio = caserio;
    }
    public int getCodigo_solicitud() {
        return codigo_solicitud;
    }
    public void setCodigo_solicitud(int codigo_solicitud) {
        this.codigo_solicitud = codigo_solicitud;
    }
    public Date getFecha_alta() {
        return fecha_alta;
    }
    public void setFecha_alta(Date fecha_alta) {
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
    public int getCodigo_solicitante() {
        return codigo_solicitante;
    }
    public void setCodigo_solicitante(int codigo_solicitante) {
        this.codigo_solicitante = codigo_solicitante;
    }
    public String getNombre_solicitante() {
        return nombre_solicitante;
    }
    public void setNombre_solicitante(String nombre_solicitante) {
        this.nombre_solicitante = nombre_solicitante;
    }

}
