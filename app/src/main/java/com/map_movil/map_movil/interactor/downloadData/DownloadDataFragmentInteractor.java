package com.map_movil.map_movil.interactor.downloadData;


import java.util.ArrayList;

public interface DownloadDataFragmentInteractor {
    void downloadData(ArrayList<String> arrayListMunicipiosSelect, String strDepartamento, int Usuario);
    void findDetailDataLocal();
}
