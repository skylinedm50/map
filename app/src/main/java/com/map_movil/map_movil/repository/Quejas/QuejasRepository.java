package com.map_movil.map_movil.repository.Quejas;

import com.google.gson.JsonObject;

public interface QuejasRepository {
    void SolicitarQuejas(int usuario);
    void RegistrarQueja(JsonObject jsonQuejaDenuncia);
}
