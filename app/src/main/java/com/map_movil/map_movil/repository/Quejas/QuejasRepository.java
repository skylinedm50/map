package com.map_movil.map_movil.repository.Quejas;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface QuejasRepository {
    void SolicitarQuejas(int usuario);
    void RegistrarQueja(JsonArray jsonQuejaDenuncia);
    void RegistrarQuejaOffline(JsonArray jsonQuejasDenuncia);
    void DescargarQuejas(int usuario , String aldea);
    void ListarQuejasOffline();
    void SincronizarQuejas(int usuario);
}
