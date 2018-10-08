package com.map_movil.map_movil.repository.downloadData;

import java.util.ArrayList;

public interface DownloadDataFragmentRepository {
    boolean existLocalData();
    void downloadData(ArrayList<String> arrayListMunicipiosSelect);
    void findDetailDataLocal();
    void deleteAllData();

    void downloadSolictudes();
    void downloadQuejasDenuncias();
    void downloadHoagres();
    void downloadHistorialPago();
}
