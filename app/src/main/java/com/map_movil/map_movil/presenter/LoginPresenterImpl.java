package com.map_movil.map_movil.presenter;

import com.map_movil.map_movil.interactor.LoginInteractor;
import com.map_movil.map_movil.interactor.LoginInteractorImpl;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView objLoginView;
    private LoginInteractor objLoginInteractor;

    public LoginPresenterImpl(LoginView objLoginView){
       this.objLoginView = objLoginView;
        objLoginInteractor = new LoginInteractorImpl(this);
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        objLoginInteractor.getDataUser(strUser, strPassword);
    }

    @Override
    public void showDataUser(User ObjUser){
        objLoginView.showDataUser(ObjUser);
    }

    @Override
    public void showError(String strError) {
        objLoginView.showError(strError);
    }
}
