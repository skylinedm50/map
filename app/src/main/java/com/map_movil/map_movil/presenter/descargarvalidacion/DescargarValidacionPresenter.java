package com.map_movil.map_movil.presenter.descargarvalidacion;

import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.HogaresValidar;

import java.util.ArrayList;

public interface DescargarValidacionPresenter {

    void SolicitarDatos(String aldea , int Usuario);
    void DescargarDatos(ArrayList<HogaresValidar> hogaresValidars);
    void SolicitarHistorialPago(String aldea);
    void DescargarHistorial(ArrayList<HistorialPago> historialPagos);
}
