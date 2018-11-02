package com.map_movil.map_movil.interactor.downloadData;

import android.content.Context;

import com.google.gson.JsonArray;
import com.map_movil.map_movil.presenter.downloadData.DownloadDataFragmentPresenter;
import com.map_movil.map_movil.repository.downloadData.DownloadDataFragmentRepository;
import com.map_movil.map_movil.repository.downloadData.DownloadDataFragmentRepositoryImpl;

import java.util.ArrayList;

public class DownloadDataFragmentInteractorImpl implements  DownloadDataFragmentInteractor{
    private DownloadDataFragmentPresenter downloadDataFragmentPresenter;
    private DownloadDataFragmentRepository downloadDataFragmentRepository;

    public DownloadDataFragmentInteractorImpl(DownloadDataFragmentPresenter downloadDataFragmentPresenter, Context context){
        this.downloadDataFragmentPresenter = downloadDataFragmentPresenter;
        downloadDataFragmentRepository = new DownloadDataFragmentRepositoryImpl(downloadDataFragmentPresenter, context);
    }

    @Override
    public void downloadData(ArrayList<String> arrayListMunicipiosSelect, String strDepartamento) {
        downloadDataFragmentRepository.downloadData(arrayListMunicipiosSelect, strDepartamento);
    }

    @Override
    public void findDetailDataLocal() {
        downloadDataFragmentRepository.findDetailDataLocal();
    }
}
