package com.map_movil.map_movil.interactor.Quejas;

import com.google.gson.JsonArray;

public interface QuejasInteractor {
    void SolicitarQuejas(int usuario);
    void RegistrarQueja(JsonArray JsonQuejaDenuncia);
    void DescargarQuejas(int usuario , String aldea);
    void SincronizarQuejas(int usuario);
}
