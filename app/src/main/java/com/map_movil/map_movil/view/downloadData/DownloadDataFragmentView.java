package com.map_movil.map_movil.view.downloadData;

import java.util.ArrayList;

public interface DownloadDataFragmentView {
    void downloadData(ArrayList<String> arrayListMunicipiosSelect);

    void findDetailDataLocal();
    void showDetailDataLocal(int[] arrayIntCant, ArrayList<String> arrayListMunicipio);
    void showMessage(String strMessage);
    void showProgressBar(boolean bolShow);
}
