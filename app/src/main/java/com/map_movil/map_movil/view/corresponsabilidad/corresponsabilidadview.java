package com.map_movil.map_movil.view.corresponsabilidad;


import com.map_movil.map_movil.model.CorresponsabilidadesClearByMenor;
import java.util.ArrayList;

public interface corresponsabilidadview {
    void findCorrByTitular(String strCodIdentidad);
    void showdata( ArrayList <CorresponsabilidadesClearByMenor>   corresponsabilidades , String NombreTitular , String CodHogar);
    void findDataShowMessage(String error,  Boolean find);
}
