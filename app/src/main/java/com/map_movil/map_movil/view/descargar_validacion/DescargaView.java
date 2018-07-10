package com.map_movil.map_movil.view.descargar_validacion;

import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.HogaresValidar;

import java.util.ArrayList;

public interface DescargaView {

    void SolicitarDatos(String aldea , int Usuario);
    void DescargarDatos(ArrayList<HogaresValidar> hogaresValidars);
    void SolicitarHistorialPago(String aldea);
    void DescargarHistorial(ArrayList<HistorialPago> historialPagos);

}
