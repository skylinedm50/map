package com.map_movil.map_movil.interactor.downloadData;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public interface DownloadDataFragmentInteractor {
    void downloadData(ArrayList<String> arrayListMunicipiosSelect, String strDepartamento);
    void findDetailDataLocal();
}
