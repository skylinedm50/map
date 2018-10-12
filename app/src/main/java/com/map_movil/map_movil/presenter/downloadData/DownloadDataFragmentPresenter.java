package com.map_movil.map_movil.presenter.downloadData;

import java.util.ArrayList;
import java.util.List;

public interface DownloadDataFragmentPresenter {
    void findDetailDataLocal();
    void showDetailDataLocal(int[] arrayIntCant, ArrayList<String> arrayListMunicipio);
    void downloadData(ArrayList<String> arrayListMunicipiosSelect);
    void showMessage(String strMessage);
}
