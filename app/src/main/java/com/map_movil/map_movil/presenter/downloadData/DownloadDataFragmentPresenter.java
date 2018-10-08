package com.map_movil.map_movil.presenter.downloadData;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public interface DownloadDataFragmentPresenter {
    void findDetailDataLocal();
    void showDetailDataLocal(int[] arrayIntCant);
    void downloadData(ArrayList<String> arrayListMunicipiosSelect);
    void showMessage(String strMessage);
    void showProgressBar(boolean bolShow);
}
