package com.map_movil.map_movil.view.Quejas;


public interface QuejasOfflineView {
    void DescargarQuejas(int usuario , String aldea);
    void SincronizarQuejas();
    void FinalizarSincronizacion();
    void CambiarPorcentaje(int porcentaje);

}