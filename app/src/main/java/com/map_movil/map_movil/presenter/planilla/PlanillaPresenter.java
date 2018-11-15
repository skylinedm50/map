package com.map_movil.map_movil.presenter.planilla;

import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosProgramados;

import java.util.ArrayList;
import java.util.List;

public interface PlanillaPresenter  {
    void getPagos();
    void cargarPagos(List<Pagos> pagos);
    void getProgramados(String strCodAldea, String strCodpago);
    void MostarDatosProgramados(ArrayList<PagosProgramados> pagosProgramados);
}
