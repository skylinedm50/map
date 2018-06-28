package com.map_movil.map_movil.interactor.ubicaciones;

public interface UbicacionesInteractor {

    void getDepartamentos();
    void getMunicipios(String depto);
    void getAldeas(String muni);
    void getCaserios(String aldea);
}
