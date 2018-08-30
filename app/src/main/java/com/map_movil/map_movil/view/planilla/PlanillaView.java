package com.map_movil.map_movil.view.planilla;

import com.map_movil.map_movil.model.Pagos;

import java.util.List;

public interface PlanillaView {
    void getPagos();
    void cargarPagos(List<Pagos> pagos);
}
