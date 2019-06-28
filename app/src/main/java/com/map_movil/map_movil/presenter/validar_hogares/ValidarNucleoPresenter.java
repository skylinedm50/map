package com.map_movil.map_movil.presenter.validar_hogares;

import com.map_movil.map_movil.model.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.model.Hogar_Validar;
import java.util.ArrayList;


public interface ValidarNucleoPresenter {
    void BuscarDatos(String identidad);
    void MostarDatos(ArrayList<Hogar_Validar> Personas , ArrayList<Hogar_Validaciones_Realizadas> validaciones_realizadas);
    void GuardarValidacion(int per_persona    , int hog_hogar       , int    identidad       ,
                           int act_compromiso , int actualizar      , int    part_nacimiento ,
                           int cons_educacion , int desagregar      , int    debe_documento  ,
                           int incorporacion  , int cambio_titular  , String observacion      );


}
