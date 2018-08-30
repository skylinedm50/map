package com.map_movil.map_movil.model;

public class SolicitudesCantidadPorEstado {
    private String Fecha_Alta_Solicitud;
    private String Fecha_Baja_Solicitud;
    private int Actualizar_Datos;
    private int Cambio_Titular;
    private int Agregar_Miembro;
    private int Desagregar_Miembros;
    private int Cambio_Domicilio;
    private int Baja_Programa;
    private int Reactivar_Programa;
    private int Correccion_Sancion;

    public String getFecha_Alta_Solicitud() {
        return Fecha_Alta_Solicitud;
    }

    public String getFecha_Baja_Solicitud() {
        return Fecha_Baja_Solicitud;
    }

    public int getActualizar_Datos() {
        return Actualizar_Datos;
    }

    public int getCambio_Titular() {
        return Cambio_Titular;
    }

    public int getAgregar_Miembro() {
        return Agregar_Miembro;
    }

    public int getDesagregar_Miembros() {
        return Desagregar_Miembros;
    }

    public int getCambio_Domicilio() {
        return Cambio_Domicilio;
    }

    public int getBaja_Programa() {
        return Baja_Programa;
    }

    public int getReactivar_Programa() {
        return Reactivar_Programa;
    }

    public int getCorreccion_Sancion() {
        return Correccion_Sancion;
    }

    public void setFecha_Alta_Solicitud(String fecha_Alta_Solicitud) {
        Fecha_Alta_Solicitud = fecha_Alta_Solicitud;
    }

    public void setFecha_Baja_Solicitud(String fecha_Baja_Solicitud) {
        Fecha_Baja_Solicitud = fecha_Baja_Solicitud;
    }

    public void setActualizar_Datos(int actualizar_Datos) {
        Actualizar_Datos = actualizar_Datos;
    }

    public void setCambio_Titular(int cambio_Titular) {
        Cambio_Titular = cambio_Titular;
    }

    public void setAgregar_Miembro(int agregar_Miembro) {
        Agregar_Miembro = agregar_Miembro;
    }


    public void setDesagregar_Miembros(int desagregar_Miembros) {
        Desagregar_Miembros = desagregar_Miembros;
    }

    public void setCambio_Domicilio(int cambio_Domicilio) {
        Cambio_Domicilio = cambio_Domicilio;
    }

    public void setBaja_Programa(int baja_Programa) {
        Baja_Programa = baja_Programa;
    }

    public void setReactivar_Programa(int reactivar_Programa) {
        Reactivar_Programa = reactivar_Programa;
    }

    public void setCorreccion_Sancion(int correccion_Sancion) {
        Correccion_Sancion = correccion_Sancion;
    }
}
