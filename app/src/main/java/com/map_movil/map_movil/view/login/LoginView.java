package com.map_movil.map_movil.view.login;

import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.model.User;

public interface LoginView {
    void getDataUser(String strUser, String strPassword);
    void existLogin(SharedPreferences sharedPreferences);
    void goToHome();
    void goToChangePassword();
    void showDataUser(User user, JsonObject jsonObject);
    void showError(String strError);
    void saveLocalLogin(int intCodUser, String strNombre, int intCantLogin, int inCodEstado, JsonObject jsonObject);
    void showProgressBar(Boolean bolShow);
}
