package com.map_movil.map_movil.interactor.ubicaciones;

import android.content.Context;

import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.repository.ubicaciones.UbicacionesRepository;
import com.map_movil.map_movil.repository.ubicaciones.UbicacionesRepositoryImpl;

public class UbicacionesInteractorImpl implements UbicacionesInteractor  {

    private UbicacionesRepository UbicacionRepository;
    private UbicacionesPresenter ubicacionesPresenter;

    public  UbicacionesInteractorImpl( UbicacionesPresenter ubicacionesPresenter){
        this.ubicacionesPresenter = ubicacionesPresenter;
        this.UbicacionRepository = new UbicacionesRepositoryImpl(this.ubicacionesPresenter);
    }

    @Override
    public void getDepartamentos() {
        this.UbicacionRepository.getDepartamentos();
    }
    @Override
    public void getMunicipios(String depto) {
        this.UbicacionRepository.getMunicipios(depto);
    }

    @Override
    public void getAldeas(String muni) {
        this.UbicacionRepository.getAldeas(muni);
    }


    @Override
    public void getCaserios(String aldea) {
        this.UbicacionRepository.getCaserios(aldea);
    }


}
