package com.map_movil.map_movil.repository.local.validar_hogares;

public interface ValidarNucleoRepository {
    void BuscarDatos(String identidad);
    void GuardarValidacion(int per_persona , int hog_hogar , int identidad ,
                           int act_compromiso , int actualizar , int part_nacimiento ,
                           int cons_educacion , int desagregar , int debe_documento  ,
                           int incorporacion , int cambio_titular);
}
