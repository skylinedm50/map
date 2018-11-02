package com.map_movil.map_movil.model;

import java.util.Date;

import io.realm.RealmObject;

public class SolicitudesDownload extends RealmObject {
    private int codigo_hogar;
    private String estado_hogar;
    private String umbral_hogar;
    private String departamento;
    private String municipio;
    private String aldea;
    private String caserio;

    private int hog_hogar;
    private int per_persona;
    private String nombre_solicitante;
    private boolean titular_cambio_hogar;

    private int codigo_solicitud;
    private Date fecha_alta;
    private Date fecha_baja;
    private boolean actualizacion_datos;
    private boolean cambio_titular;
    private boolean nuevo_integrante;
    private boolean baja_integrante;
    private boolean cambio_domicilio;
    private boolean baja_programa;
    private boolean reactiva_programa;
    private boolean correccion_sancion;
    private String observacion;
    private int codigo_estado;
    private String estado_solicitud;
    private boolean isLocal;


    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public int getCodigo_hogar() {
        return codigo_hogar;
    }

    public void setCodigo_hogar(int codigo_hogar) {
        this.codigo_hogar = codigo_hogar;
    }

    public String getEstado_hogar() {
        return estado_hogar;
    }

    public void setEstado_hogar(String estado_hogar) {
        this.estado_hogar = estado_hogar;
    }

    public String getUmbral_hogar() {
        return umbral_hogar;
    }

    public void setUmbral_hogar(String umbral_hogar) {
        this.umbral_hogar = umbral_hogar;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getAldea() {
        return aldea;
    }

    public void setAldea(String aldea) {
        this.aldea = aldea;
    }

    public String getCaserio() {
        return caserio;
    }

    public void setCaserio(String caserio) {
        this.caserio = caserio;
    }

    public int getHog_hogar() {
        return hog_hogar;
    }

    public void setHog_hogar(int hog_hogar) {
        this.hog_hogar = hog_hogar;
    }

    public int getPer_persona() {
        return per_persona;
    }

    public void setPer_persona(int per_persona) {
        this.per_persona = per_persona;
    }

    public String getNombre_solicitante() {
        return nombre_solicitante;
    }

    public void setNombre_solicitante(String nombre_solicitante) {
        this.nombre_solicitante = nombre_solicitante;
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

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public boolean isActualizacion_datos() {
        return actualizacion_datos;
    }

    public void setActualizacion_datos(boolean actualizacion_datos) {
        this.actualizacion_datos = actualizacion_datos;
    }

    public boolean isCambio_titular() {
        return cambio_titular;
    }

    public void setCambio_titular(boolean cambio_titular) {
        this.cambio_titular = cambio_titular;
    }

    public boolean isNuevo_integrante() {
        return nuevo_integrante;
    }

    public void setNuevo_integrante(boolean nuevo_integrante) {
        this.nuevo_integrante = nuevo_integrante;
    }

    public boolean isBaja_integrante() {
        return baja_integrante;
    }

    public void setBaja_integrante(boolean baja_integrante) {
        this.baja_integrante = baja_integrante;
    }

    public boolean isCambio_domicilio() {
        return cambio_domicilio;
    }

    public void setCambio_domicilio(boolean cambio_domicilio) {
        this.cambio_domicilio = cambio_domicilio;
    }

    public boolean isBaja_programa() {
        return baja_programa;
    }

    public void setBaja_programa(boolean baja_programa) {
        this.baja_programa = baja_programa;
    }

    public boolean isReactiva_programa() {
        return reactiva_programa;
    }

    public void setReactiva_programa(boolean reactiva_programa) {
        this.reactiva_programa = reactiva_programa;
    }

    public boolean isCorreccion_sancion() {
        return correccion_sancion;
    }

    public void setCorreccion_sancion(boolean correccion_sancion) {
        this.correccion_sancion = correccion_sancion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getCodigo_estado() {
        return codigo_estado;
    }

    public void setCodigo_estado(int codigo_estado) {
        this.codigo_estado = codigo_estado;
    }

    public String getEstado_solicitud() {
        return estado_solicitud;
    }

    public void setEstado_solicitud(String estado_solicitud) {
        this.estado_solicitud = estado_solicitud;
    }

    public boolean isTitular_cambio_hogar() {
        return titular_cambio_hogar;
    }

    public void setTitular_cambio_hogar(boolean titular_cambio_hogar) {
        this.titular_cambio_hogar = titular_cambio_hogar;
    }
}

