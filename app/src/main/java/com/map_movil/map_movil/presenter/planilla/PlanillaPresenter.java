package com.map_movil.map_movil.presenter.planilla;

import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosExcluido;
import com.map_movil.map_movil.model.PagosProgramados;

import java.util.ArrayList;
import java.util.List;

public interface PlanillaPresenter  {
    void getPagos();
    void cargarPagos(List<Pagos> pagos);
    void getProgramados(String strCodAldea, String strCodpago);
    void getExcluidos(String strCodAldea, String strCodpago, String tipoGM);
    void MostarDatosProgramados(ArrayList<PagosProgramados> pagosProgramados);
    void MostarExcluidos(ArrayList<PagosExcluido> listexcluidos);
}
