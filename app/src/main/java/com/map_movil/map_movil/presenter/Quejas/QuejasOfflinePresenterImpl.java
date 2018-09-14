package com.map_movil.map_movil.presenter.Quejas;

import com.map_movil.map_movil.interactor.Quejas.QuejasOfflineInteractor;
import com.map_movil.map_movil.interactor.Quejas.QuejasOfflineInteractorImpl;
import com.map_movil.map_movil.view.Quejas.QuejasOfflineView;

public class QuejasOfflinePresenterImpl implements QuejasOfflinePresenter {

    private QuejasOfflineView quejasOfflineView;
    private QuejasOfflineInteractor quejasOfflineInteractor;

    public void QuejasOfflinePresenterImpl(QuejasOfflineView quejasOfflineView){
        this.quejasOfflineView = quejasOfflineView;
        this.quejasOfflineInteractor = new QuejasOfflineInteractorImpl();
    }

    @Override
    public void DescargarQuejas(int usuario, String aldea) {

    }

    @Override
    public void SincronizarQuejas() {

    }
}
