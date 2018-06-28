package com.map_movil.map_movil.presenter.ubicaciones;

import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Departamentos;
import com.map_movil.map_movil.model.Municipios;

import java.util.List;

public interface UbicacionesPresenter {

    void getDepartamentos();
    void cargarDepartamentos(List<Departamentos> departamentos);

    void getMunicipios(String depto);
    void cargarMunicipios(List<Municipios> municipios);


    void getAldeas(String muni);
    void cargarAldeas(List<Aldeas> aldeas);

    void getCaserios(String aldea);
    void cargarCaserios(List<Caserios> caserios);
}
