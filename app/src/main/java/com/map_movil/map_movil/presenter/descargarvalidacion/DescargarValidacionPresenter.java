package com.map_movil.map_movil.presenter.descargarvalidacion;

import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;

import java.util.ArrayList;

public interface DescargarValidacionPresenter {

    void SolicitarDatos(String aldea , int Usuario);
    void DescargarDatos(ArrayList<Hogar_Validar> hogaresValidars);
    void SolicitarHistorialPago(String aldea);
    void DescargarHistorial(ArrayList<HistorialPago> historialPagos);
}
