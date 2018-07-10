package com.map_movil.map_movil.presenter.login;

import android.content.SharedPreferences;

import com.map_movil.map_movil.model.User;

public interface LoginPresenter {
    void existLogin(SharedPreferences sharedPreferences);
    void getDataUser(String strUser, String strPassword);
    void showDataUser(User objUser);
    void showError(String strError);
    void getDetectError(String strUser, String strPassword);
}
