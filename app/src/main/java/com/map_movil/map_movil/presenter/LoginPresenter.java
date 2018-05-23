package com.map_movil.map_movil.presenter;

import com.map_movil.map_movil.model.User;

public interface LoginPresenter {
    void getDataUser(String strUser, String strPassword);//Corresponde al repositorio.
    void showDataUser(User objUser);//Vista.
    void showError(String strError);//Vista.
}
