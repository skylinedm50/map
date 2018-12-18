package com.map_movil.map_movil.repository.planilla;

public interface PlanillaRepository {
    void getPagos();
    void getProgramados(String strCodAldea, String strCodpago);
    void getExcluidosGlobal(String strCodAldea, String strCodpago);
    void getExcluidosMancomunidad(String strCodAldea, String strCodpago);
    void getProgramadosOffline(String strCodAldea , String pago);
    void getPagosOffline();
}
