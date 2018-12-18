package com.map_movil.map_movil.presenter.corresponsabilidad;

import com.map_movil.map_movil.model.Corresponsabilidades;

import java.util.ArrayList;

public interface corresponsabilidadpresenter {
    void findCorrByTitular(String strCodIdentidad);
    void showdata(ArrayList<Corresponsabilidades> corresponsabilidades);
    void findDataShowMessage(String error,  Boolean find);
}
