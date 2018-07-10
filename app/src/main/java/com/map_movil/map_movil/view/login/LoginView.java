package com.map_movil.map_movil.view.login;

import android.content.SharedPreferences;

import com.map_movil.map_movil.model.User;

public interface LoginView {
    void getDataUser(String strUser, String strPassword);
    void existLogin(SharedPreferences sharedPreferences);
    void goToHome();
    void goToChangePassword();
    void showDataUser(User user);
    void showError(String strError);
    void saveLocalLogin(int intCodUser, String strNombre, int intCantLogin, int inCodEstado);
}
