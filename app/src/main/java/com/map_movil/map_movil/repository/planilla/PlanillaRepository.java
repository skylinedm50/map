package com.map_movil.map_movil.repository.planilla;

public interface PlanillaRepository {
    void getPagos();
    void getProgramados(String strCodAldea, String strCodpago, int Usuario);
    void getExcluidosGlobal(String strCodAldea, String strCodpago, int Usuario);
    void getExcluidosMancomunidad(String strCodAldea, String strCodpago, int Usuario);
    void getProgramadosOffline(String strCodAldea , String pago);
    void getProgramado_By_ID(String strIdentidad, String strCodpago, int Usuario);
    void getProgramado_By_ID_Offline(String strIdentidad, String strCodpago);
    void getPagosOffline();
}
