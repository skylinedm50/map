package com.map_movil.map_movil.presenter.ubicaciones;

import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Departamentos;
import com.map_movil.map_movil.model.Municipios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface UbicacionesPresenter {

    void getDepartamentos();
    void cargarDepartamentos(ArrayList<String> departamentos);

    void getMunicipios(String depto);
    void cargarMunicipios(ArrayList<String> municipios);


    void getAldeas(String muni);
    void cargarAldeas(List<Aldeas> aldeas);

    void getCaserios(String aldea);
    void cargarCaserios(List<Caserios> caserios);
}
