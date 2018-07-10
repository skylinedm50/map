package com.map_movil.map_movil.interactor.validar_hogares;


public interface ValidarNucleoInteractor {
    void BuscarDatos(String identidad);
    void GuardarValidacion(int per_persona , int hog_hogar , int identidad ,
                           int act_compromiso , int actualizar , int part_nacimiento ,
                           int cons_educacion , int desagregar , int debe_documento );
}
