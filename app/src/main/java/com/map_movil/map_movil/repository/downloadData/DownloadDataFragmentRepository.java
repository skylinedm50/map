package com.map_movil.map_movil.repository.downloadData;

import java.util.ArrayList;

public interface DownloadDataFragmentRepository {
    boolean existLocalData();
    void downloadData(ArrayList<String> arrayListMunicipiosSelect, String strDepartamento, int Usuario);
    void findDetailDataLocal();
    void deleteAllData();

    void downloadSolictudes();
    void downloadQuejasDenuncias();
    void downloadHogares(int Usuario);
    void downloadHistorialPago(int Usuario);
}
