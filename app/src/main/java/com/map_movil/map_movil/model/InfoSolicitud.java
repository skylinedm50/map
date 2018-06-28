package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class InfoSolicitud {
    @SerializedName("codigo_solicitud")
    private int intCodSolicitud;
    @SerializedName("codigo_hogar")
    private int intCodHogar;
    @SerializedName("estado_hogar")
    private String strEstadoHogar;
    @SerializedName("umbral_hogar")
    private String strUmbral;
    @SerializedName("departamento")
    private String strDepartamento;
    @SerializedName("municipio")
    private String strMunicipio;
    @SerializedName("aldea")
    private String strAldea;
    @SerializedName("caserio")
    private String strCaserio;
    @SerializedName("fecha_alta")
    private Date dateFechaAlta;
    @SerializedName("fecha_baja")
    private Date dateFechaBaja;
    @SerializedName("actualizacion_datos")
    private boolean bolActualizacionDatos;
    @SerializedName("cambio_titular")
    private boolean bolCambioTitular;
    @SerializedName("nuevo_integrante")
    private boolean bolNuevoIntegrante;
    @SerializedName("baja_integrante")
    private boolean bolBajaIntegrante;
    @SerializedName("cambio_domicilio")
    private boolean bolCambioDomicilio;
    @SerializedName("baja_programa")
    private boolean bolBajaPrograma;
    @SerializedName("reactiva_programa")
    private boolean bolReactivaPrograma;
    @SerializedName("correccion_sancion")
    private boolean bolCorreccionSancion;
    @SerializedName("observacion")
    private String strObservacion;
    @SerializedName("estado_solicitud")
    private String strEstadoSolicitud;


    public InfoSolicitud(int intCodSolicitud, int intCodHogar, String strEstadoHogar, String strUmbral,
                         String strDepartamento, String strMunicipio, String strAldea, String strCaserio,
                         Date dateFechaAlta, Date dateFechaBaja, boolean bolActualizacionDatos, boolean bolCambioTitular,
                         boolean bolNuevoIntegrante, boolean bolBajaIntegrante, boolean bolCambioDomicilio,
                         boolean bolBajaPrograma,boolean bolReactivaPrograma, boolean bolCorreccionSancion, String strObservacion, String strEstadoSolicitud) {
        this.intCodSolicitud = intCodSolicitud;
        this.intCodHogar = intCodHogar;
        this.strEstadoHogar = strEstadoHogar;
        this.strUmbral = strUmbral;
        this.strDepartamento = strDepartamento;
        this.strMunicipio = strMunicipio;
        this.strAldea = strAldea;
        this.strCaserio = strCaserio;
        this.dateFechaAlta = dateFechaAlta;
        this.dateFechaBaja = dateFechaBaja;
        this.bolActualizacionDatos = bolActualizacionDatos;
        this.bolCambioTitular = bolCambioTitular;
        this.bolNuevoIntegrante = bolNuevoIntegrante;
        this.bolBajaIntegrante = bolBajaIntegrante;
        this.bolCambioDomicilio = bolCambioDomicilio;
        this.bolBajaPrograma = bolBajaPrograma;
        this.bolReactivaPrograma = bolReactivaPrograma;
        this.bolCorreccionSancion = bolCorreccionSancion;
        this.strObservacion = strObservacion;
        this.strEstadoSolicitud = strEstadoSolicitud;
    }

    public int getIntCodSolicitud() {
        return intCodSolicitud;
    }

    public void setIntCodSolicitud(int intCodSolicitud) {
        this.intCodSolicitud = intCodSolicitud;
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

    public String getStrUmbral() {
        return strUmbral;
    }

    public void setStrUmbral(String strUmbral) {
        this.strUmbral = strUmbral;
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

    public Date getDateFechaAlta() {
        return dateFechaAlta;
    }

    public void setDateFechaAlta(Date dateFechaAlta) {
        this.dateFechaAlta = dateFechaAlta;
    }

    public Date getDateFechaBaja() {
        return dateFechaBaja;
    }

    public void setDateFechaBaja(Date dateFechaBaja) {
        this.dateFechaBaja = dateFechaBaja;
    }

    public boolean isBolActualizacionDatos() {
        return bolActualizacionDatos;
    }

    public void setBolActualizacionDatos(boolean bolActualizacionDatos) {
        this.bolActualizacionDatos = bolActualizacionDatos;
    }

    public boolean isBolCambioTitular() {
        return bolCambioTitular;
    }

    public void setBolCambioTitular(boolean bolCambioTitular) {
        this.bolCambioTitular = bolCambioTitular;
    }

    public boolean isBolNuevoIntegrante() {
        return bolNuevoIntegrante;
    }

    public void setBolNuevoIntegrante(boolean bolNuevoIntegrante) {
        this.bolNuevoIntegrante = bolNuevoIntegrante;
    }

    public boolean isBolBajaIntegrante() {
        return bolBajaIntegrante;
    }

    public void setBolBajaIntegrante(boolean bolBajaIntegrante) {
        this.bolBajaIntegrante = bolBajaIntegrante;
    }

    public boolean isBolCambioDomicilio() {
        return bolCambioDomicilio;
    }

    public void setBolCambioDomicilio(boolean bolCambioDomicilio) {
        this.bolCambioDomicilio = bolCambioDomicilio;
    }

    public boolean isBolBajaPrograma() {
        return bolBajaPrograma;
    }

    public void setBolBajaPrograma(boolean bolBajaPrograma) {
        this.bolBajaPrograma = bolBajaPrograma;
    }

    public boolean isBolCorreccionSancion() {
        return bolCorreccionSancion;
    }

    public void setBolCorreccionSancion(boolean bolCorreccionSancion) {
        this.bolCorreccionSancion = bolCorreccionSancion;
    }

    public String getStrObservacion() {
        return strObservacion;
    }

    public void setStrObservacion(String strObservacion) {
        this.strObservacion = strObservacion;
    }

    public String getStrEstadoSolicitud() {
        return strEstadoSolicitud;
    }

    public void setStrEstadoSolicitud(String strEstadoSolicitud) {
        this.strEstadoSolicitud = strEstadoSolicitud;
    }

    public boolean isBolReactivaPrograma() {
        return bolReactivaPrograma;
    }

    public void setBolReactivaPrograma(boolean bolReactivaPrograma) {
        this.bolReactivaPrograma = bolReactivaPrograma;
    }
}
