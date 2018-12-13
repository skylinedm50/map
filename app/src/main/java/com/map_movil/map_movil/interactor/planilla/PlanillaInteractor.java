package com.map_movil.map_movil.interactor.planilla;

public interface PlanillaInteractor {
    void getPagos();
    void getProgramados(String strCodAldea, String strCodpago);
    void getExcluidos(String strCodAldea, String strCodpago,String tipoGM);
}
