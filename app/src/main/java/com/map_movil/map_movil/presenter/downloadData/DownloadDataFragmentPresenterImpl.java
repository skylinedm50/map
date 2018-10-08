package com.map_movil.map_movil.presenter.downloadData;

import android.content.Context;

import com.map_movil.map_movil.interactor.downloadData.DownloadDataFragmentInteractor;
import com.map_movil.map_movil.interactor.downloadData.DownloadDataFragmentInteractorImpl;
import com.map_movil.map_movil.view.downloadData.DownloadDataFragmentView;

import java.util.ArrayList;

public class DownloadDataFragmentPresenterImpl implements DownloadDataFragmentPresenter {
    private DownloadDataFragmentView downloadDataFragmentView;
    private DownloadDataFragmentInteractor downloadDataFragmentInteractor;
    private Context context;

    public DownloadDataFragmentPresenterImpl(DownloadDataFragmentView downloadDataFragmentView, Context context){
        this.downloadDataFragmentView = downloadDataFragmentView;
        this.context = context;
        downloadDataFragmentInteractor = new DownloadDataFragmentInteractorImpl(this, context);
    }

    @Override
    public void downloadData(ArrayList<String> arrayListMunicipiosSelect) {
        switch (arrayListMunicipiosSelect.size()) {
            case 0:
                downloadDataFragmentView.showMessage("Favor seleccione por lo menos un municipio.");
                downloadDataFragmentView.findDetailDataLocal();
                break;
            case 1:
            case 2:
            case 3:
                downloadDataFragmentInteractor.downloadData(arrayListMunicipiosSelect);
                break;
            default:
                downloadDataFragmentView.showMessage("Soló se pueden seleccionar 3 municipios como maximo.");
                downloadDataFragmentView.findDetailDataLocal();
                break;
        }
    }

    @Override
    public void findDetailDataLocal() {
        downloadDataFragmentInteractor.findDetailDataLocal();
    }

    @Override
    public void showDetailDataLocal(int[] arrayIntCant) {
        downloadDataFragmentView.showDetailDataLocal(arrayIntCant);
    }

    @Override
    public void showMessage(String strMessage) {
        downloadDataFragmentView.showMessage(strMessage);
    }

    @Override
    public void showProgressBar(boolean bolShow) {
        downloadDataFragmentView.showProgressBar(bolShow);
    }
}
