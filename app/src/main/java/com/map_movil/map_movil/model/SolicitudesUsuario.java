package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SolicitudesUsuario {
    @SerializedName("codigo_solicitud")
    private Integer intCodSolicitud;

    @SerializedName("nombre_solicitante")
    private String strNombreSolicitante;

    @SerializedName("observacion")
    private String strObservacion;

    @SerializedName("fecha_alta_solicitud")
    private Date dateFechaAltaSolicitud;

    @SerializedName("fecha_baja_solicitud")
    private Date dateFechaBajaSolicitud;

    @SerializedName("codigo_usuario_atiende")
    private Integer intCodUsuario;

    @SerializedName("codigo_estado")
    private Integer intCodEstado;

    @SerializedName("estado_solicitud")
    private String strEstado;

    public SolicitudesUsuario(){}

    public SolicitudesUsuario(Integer intCodSolicitud, String strNombreSolicitante, String strObservacion, Date dateFechaAltaSolicitud, Date dateFechaBajaSolicitud, Integer intCodUsuario, Integer intCodEstado, String strEstado) {
        this.intCodSolicitud = intCodSolicitud;
        this.strNombreSolicitante = strNombreSolicitante;
        this.strObservacion = strObservacion;
        this.dateFechaAltaSolicitud = dateFechaAltaSolicitud;
        this.dateFechaBajaSolicitud = dateFechaBajaSolicitud;
        this.intCodUsuario = intCodUsuario;
        this.intCodEstado = intCodEstado;
        this.strEstado = strEstado;
    }

    public Integer getIntCodSolicitud() {
        return intCodSolicitud;
    }

    public void setIntCodSolicitud(Integer intCodSolicitud) {
        this.intCodSolicitud = intCodSolicitud;
    }

    public String getStrNombreSolicitante() {
        return strNombreSolicitante;
    }

    public void setStrNombreSolicitante(String strNombreSolicitante) {
        this.strNombreSolicitante = strNombreSolicitante;
    }

    public String getStrObservacion() {
        return strObservacion;
    }

    public void setStrObservacion(String strObservacion) {
        this.strObservacion = strObservacion;
    }

    public Date getDateFechaAltaSolicitud() {
        return dateFechaAltaSolicitud;
    }

    public void setDateFechaAltaSolicitud(Date dateFechaAltaSolicitud) {
        this.dateFechaAltaSolicitud = dateFechaAltaSolicitud;
    }

    public Date getDateFechaBajaSolicitud() {
        return dateFechaBajaSolicitud;
    }

    public void setDateFechaBajaSolicitud(Date dateFechaBajaSolicitud) {
        this.dateFechaBajaSolicitud = dateFechaBajaSolicitud;
    }

    public Integer getIntCodUsuario() {
        return intCodUsuario;
    }

    public void setIntCodUsuario(Integer intCodUsuario) {
        this.intCodUsuario = intCodUsuario;
    }

    public Integer getIntCodEstado() {
        return intCodEstado;
    }

    public void setIntCodEstado(Integer intCodEstado) {
        this.intCodEstado = intCodEstado;
    }


    public String getStrEstado() {
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }
}
