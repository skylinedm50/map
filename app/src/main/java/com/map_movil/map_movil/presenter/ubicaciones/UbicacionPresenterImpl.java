package com.map_movil.map_movil.presenter.ubicaciones;

import android.content.Context;

import com.map_movil.map_movil.interactor.ubicaciones.UbicacionesInteractor;
import com.map_movil.map_movil.interactor.ubicaciones.UbicacionesInteractorImpl;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Departamentos;
import com.map_movil.map_movil.model.Municipios;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;

import java.util.List;

public class UbicacionPresenterImpl implements UbicacionesPresenter {

    private UbicacionesInteractor ubicacionesInteractor;
    private UbicacionView ubicacionView;

    public UbicacionPresenterImpl( UbicacionView ubicacionView){
        this.ubicacionView = ubicacionView;
        this.ubicacionesInteractor = new UbicacionesInteractorImpl(this);
    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesInteractor.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(List<Departamentos> departamentos) {
        this.ubicacionView.cargarDepartamentos(departamentos);
    }

    @Override
    public void getMunicipios(String depto) {
        this.ubicacionesInteractor.getMunicipios(depto);
    }

    @Override
    public void cargarMunicipios(List<Municipios> municipios) {
        this.ubicacionView.cargarMunicipios(municipios);
    }

    @Override
    public void getAldeas(String muni) {
        this.ubicacionesInteractor.getAldeas(muni);
    }

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {
        this.ubicacionView.cargarAldeas(aldeas);
    }


    @Override
    public void getCaserios(String aldea) {
        this.ubicacionesInteractor.getCaserios(aldea);
    }

    @Override
    public void cargarCaserios(List<Caserios> caserios) {
        this.ubicacionView.cargarCaserios(caserios);
    }

}
