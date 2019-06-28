package com.map_movil.map_movil.interactor.planilla;

public interface PlanillaInteractor {
    void getPagos();
    void getProgramados(String strCodAldea, String strCodpago, int Usuario);
    void getExcluidos(String strCodAldea, String strCodpago,String tipoGM, int Usuario);
    void getProgramado_By_ID(String strIdentidad, String strCodpago, int Usuario);
}
