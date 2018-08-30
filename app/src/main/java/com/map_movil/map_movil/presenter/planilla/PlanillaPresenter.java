package com.map_movil.map_movil.presenter.planilla;

import com.map_movil.map_movil.model.Pagos;

import java.util.List;

public interface PlanillaPresenter  {
    void getPagos();
    void cargarPagos(List<Pagos> pagos);
}
