package com.map_movil.map_movil.view.downloadData;

import java.util.ArrayList;

public interface DownloadDataFragmentView {
    void downloadData(ArrayList<String> arrayListMunicipiosSelect, String strDepartamento, int Usuario);

    void findDetailDataLocal();
    void showDetailDataLocal(int[] arrayIntCant, ArrayList<String> arrayListMunicipio, String strDepartamentoSelected);
    void showMessage(String strMessage);
    void showProgressBar(boolean bolShow);
}
