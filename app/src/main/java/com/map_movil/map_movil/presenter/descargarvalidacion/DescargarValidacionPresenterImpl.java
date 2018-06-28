package com.map_movil.map_movil.presenter.descargarvalidacion;

import com.map_movil.map_movil.interactor.DescargarValidacion.DescargarValidacionInteractor;
import com.map_movil.map_movil.interactor.DescargarValidacion.DescargarValidacionInteractorImpl;
import com.map_movil.map_movil.model.HogaresValidar;
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
    public void DescargarDatos(ArrayList<HogaresValidar> hogaresValidars) {
        descargaView.DescargarDatos(hogaresValidars);
    }
}