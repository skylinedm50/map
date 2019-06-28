package com.map_movil.map_movil.presenter.downloadData;

import java.util.ArrayList;

public interface DownloadDataFragmentPresenter {
    void findDetailDataLocal();
    void showDetailDataLocal(int[] arrayIntCant, ArrayList<String> arrayListMunicipio,String strDepartamentoSelected);
    void downloadData(ArrayList<String> arrayListMunicipiosSelect, String strDepartamento,int Usuario);
    void showMessage(String strMessage);
}
