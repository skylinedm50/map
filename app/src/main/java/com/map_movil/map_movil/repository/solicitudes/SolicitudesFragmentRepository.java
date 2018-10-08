package com.map_movil.map_movil.repository.solicitudes;

public interface SolicitudesFragmentRepository {
    void getSolicitudesGestionadas(int intCodUser, String strSimbolo);
    void getSolicitudesGestionadasLocalDB(int intCodUser, String strSimbolo);
}
