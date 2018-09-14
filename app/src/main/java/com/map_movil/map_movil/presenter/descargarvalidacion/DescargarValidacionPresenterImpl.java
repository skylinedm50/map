package com.map_movil.map_movil.presenter.descargarvalidacion;

import com.map_movil.map_movil.interactor.DescargarValidacion.DescargarValidacionInteractor;
import com.map_movil.map_movil.interactor.DescargarValidacion.DescargarValidacionInteractorImpl;
import com.map_movil.map_movil.model.HistorialPago;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.view.descargar_validacion.DescargaView;

import java.util.ArrayList;

public class DescargarValidacionPresenterImpl implements DescargarValidacionPresenter {

    private DescargaView descargaView;
    private DescargarValidacionInteractor descargarValidacionInteractor;

    public DescargarValidacionPresenterImpl(DescargaView  descargaView){
        this.descargaView = descargaView;
        this.descargarValidacionInteractor = new DescargarValidacionInteractorImpl(this);
    }

    @Override
    public void SolicitarDatos(String aldea, int Usuario) {
        this.descargarValidacionInteractor.SolicitarDatos(aldea , Usuario);
    }

    @Override
    public void DescargarDatos(ArrayList<Hogar_Validar> hogaresValidars) {
        descargaView.DescargarDatos(hogaresValidars);
    }

    @Override
    public void SolicitarHistorialPago(String aldea) {
        this.descargarValidacionInteractor.SolicitarHistorialPago(aldea);
    }

    @Override
    public void DescargarHistorial(ArrayList<HistorialPago> historialPagos) {
        descargaView.DescargarHistorial(historialPagos);
    }
}
