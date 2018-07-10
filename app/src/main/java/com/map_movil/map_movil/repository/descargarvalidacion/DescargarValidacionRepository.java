package com.map_movil.map_movil.repository.descargarvalidacion;

public interface DescargarValidacionRepository {

    void SolicitarDatos(String aldea , int Usuario);
    void SolicitarHistorialPago(String aldea);
}
