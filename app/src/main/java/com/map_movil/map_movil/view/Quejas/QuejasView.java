package com.map_movil.map_movil.view.Quejas;


import com.map_movil.map_movil.model.Realm.QuejasDenuncias;

import java.util.ArrayList;

public interface QuejasView {
    void MostarQuejas(ArrayList<QuejasDenuncias> respuesta , int RealizadosLenght , int noRealizadosLenght);
    void SolicitarQuejas();
    void RegistrarQueja();
    void ActualizarDatos();
}
