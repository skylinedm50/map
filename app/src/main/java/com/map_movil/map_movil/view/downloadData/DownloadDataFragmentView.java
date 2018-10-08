package com.map_movil.map_movil.view.downloadData;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public interface DownloadDataFragmentView {
    void downloadData(ArrayList<String> arrayListMunicipiosSelect);

    void findDetailDataLocal();
    void showDetailDataLocal(int[] arrayIntCant);
    void showMessage(String strMessage);
    void showProgressBar(boolean bolShow);
}
