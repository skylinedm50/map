package com.map_movil.map_movil.repository.sincronizar;

public interface SincronizarRepository {
    void SincronizarQuejas(int usuario);
    void synchronizeRequestWithServer(int intCodUser);
}
