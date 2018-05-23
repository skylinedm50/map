package com.map_movil.map_movil.view;

import com.map_movil.map_movil.model.User;

public interface LoginView {
    void getDataUser(String strUser, String strPassword);
    void showDataUser(User objUser);
    void showError(String strError);
}
