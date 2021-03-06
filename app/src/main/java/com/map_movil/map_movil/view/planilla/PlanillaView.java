package com.map_movil.map_movil.view.planilla;

import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosExcluido;
import com.map_movil.map_movil.model.PagosProgramados;
import java.util.ArrayList;
import java.util.List;

public interface PlanillaView {
    void getPagos();
    void cargarPagos(List<Pagos> pagos);
    void SolicitarDatosProgramados(String strCodAldea, String strCodpago, int Usuario);
    void MostarDatosProgramados(ArrayList<PagosProgramados> pagosProgramados);
    void MostarExcluidos(ArrayList<PagosExcluido> listexcluidos);
    void SolicitarDatosProgramadosPorID(String strIdentidad, String strCodpago, int Usuario);
}
