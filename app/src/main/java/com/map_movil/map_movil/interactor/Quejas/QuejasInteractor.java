package com.map_movil.map_movil.interactor.Quejas;

import com.google.gson.JsonObject;

public interface QuejasInteractor {
    void SolicitarQuejas(int usuario);
    void RegistrarQueja(JsonObject JsonQuejaDenuncia);
}
