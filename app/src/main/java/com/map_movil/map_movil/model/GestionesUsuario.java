package com.map_movil.map_movil.model;

import com.google.gson.annotations.SerializedName;

public class GestionesUsuario {

    @SerializedName("codigo_usuario")
    private int codigo_usuario;
    @SerializedName("actualizar_datos")
    private int actualizar_datos;
    @SerializedName("cambio_titular")
    private int cambio_titular;
    @SerializedName("agregar_miembro")
    private int agregar_miembro;
    @SerializedName("desagregar_miembros")
    private int desagregar_miembros;
    @SerializedName("cambio_domicilio")
    private int cambio_domicilio;
    @SerializedName("baja_programa")
    private int baja_programa;
    @SerializedName("reactivar_programa")
    private int reactivar_programa;
    @SerializedName("correccion_sancion")
    private int correccion_sancion;

    public GestionesUsuario(){}

    public int getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(int codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public int getActualizar_datos() {
        return actualizar_datos;
    }

    public void setActualizar_datos(int actualizar_datos) {
        this.actualizar_datos = actualizar_datos;
    }

    public int getCambio_titular() {
        return cambio_titular;
    }

    public void setCambio_titular(int cambio_titular) {
        this.cambio_titular = cambio_titular;
    }

    public int getAgregar_miembro() {
        return agregar_miembro;
    }

    public void setAgregar_miembro(int agregar_miembro) {
        this.agregar_miembro = agregar_miembro;
    }

    public int getDesagregar_miembros() {
        return desagregar_miembros;
    }

    public void setDesagregar_miembros(int desagregar_miembros) {
        this.desagregar_miembros = desagregar_miembros;
    }

    public int getCambio_domicilio() {
        return cambio_domicilio;
    }

    public void setCambio_domicilio(int cambio_domicilio) {
        this.cambio_domicilio = cambio_domicilio;
    }

    public int getBaja_programa() {
        return baja_programa;
    }

    public void setBaja_programa(int baja_programa) {
        this.baja_programa = baja_programa;
    }

    public int getReactivar_programa() {
        return reactivar_programa;
    }

    public void setReactivar_programa(int reactivar_programa) {
        this.reactivar_programa = reactivar_programa;
    }

    public int getCorreccion_sancion() {
        return correccion_sancion;
    }

    public void setCorreccion_sancion(int correccion_sancion) {
        this.correccion_sancion = correccion_sancion;
    }
}
