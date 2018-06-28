package com.map_movil.map_movil.repository.ubicaciones;

public interface UbicacionesRepository {
    void getDepartamentos();
    void getMunicipios(String depto);
    void getAldeas(String muni);
    void getCaserios(String aldea);

}
