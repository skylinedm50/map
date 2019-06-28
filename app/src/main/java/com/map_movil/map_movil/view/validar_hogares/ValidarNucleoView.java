package com.map_movil.map_movil.view.validar_hogares;

import com.map_movil.map_movil.model.Hogar_Validaciones_Realizadas;
import java.util.ArrayList;


public interface ValidarNucleoView {

    void BuscarDatos(String identidad);
    void MostarDatos(ArrayList<String[]> Personas , ArrayList<Hogar_Validaciones_Realizadas> hogar_validaciones_realizadas);
    void PopUp(int titular , String Persona , int index , String identidad);

}
