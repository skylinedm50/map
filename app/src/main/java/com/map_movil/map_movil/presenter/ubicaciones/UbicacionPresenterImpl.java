package com.map_movil.map_movil.presenter.ubicaciones;

import android.content.Context;

import com.map_movil.map_movil.interactor.ubicaciones.UbicacionesInteractor;
import com.map_movil.map_movil.interactor.ubicaciones.UbicacionesInteractorImpl;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Departamentos;
import com.map_movil.map_movil.model.Municipios;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;

import java.util.ArrayList;
import java.util.List;

public class UbicacionPresenterImpl implements UbicacionesPresenter {

    private UbicacionesInteractor ubicacionesInteractor;
    private UbicacionView ubicacionView;
    private Context context;

    public UbicacionPresenterImpl( UbicacionView ubicacionView, Context context){
        this.context = context;
        this.ubicacionView = ubicacionView;
        this.ubicacionesInteractor = new UbicacionesInteractorImpl(this, this.context);
    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesInteractor.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {
        this.ubicacionView.cargarDepartamentos(departamentos);
    }

    @Override
    public void getMunicipios(String depto) {
        String[] arrayItem = depto.toString().split("-");
        this.ubicacionesInteractor.getMunicipios(arrayItem[0]);
    }

    @Override
    public void cargarMunicipios(ArrayList<String> municipios) {
        this.ubicacionView.cargarMunicipios(municipios);
    }

    @Override
    public void getAldeas(String muni) {
        String[] arrayItem = muni.toString().split("-");
        this.ubicacionesInteractor.getAldeas(arrayItem[0]);
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
