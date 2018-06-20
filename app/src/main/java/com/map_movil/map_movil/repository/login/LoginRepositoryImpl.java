package com.map_movil.map_movil.repository.login;

import com.map_movil.map_movil.api.login.ApiAdapterLogin;
import com.map_movil.map_movil.api.login.ApiServiceLogin;
import com.map_movil.map_movil.model.User;
import com.map_movil.map_movil.presenter.login.LoginPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImpl implements LoginRepository{
    private LoginPresenter objLoginPresenter;

    public LoginRepositoryImpl(LoginPresenter objLoginPresenter){
        this.objLoginPresenter = objLoginPresenter;
    }

    @Override
    public void getDataUser(String strUser, String strPassword){
        ApiAdapterLogin objApiAdapter = new ApiAdapterLogin();
        ApiServiceLogin objApiServiceLogin = objApiAdapter.getClientService();
        Call<List<User>> call = objApiServiceLogin.getLogin(strUser, strPassword);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
               if(response.body() != null && response.body().size() > 0){
                    User objUser = response.body().get(0);
                    objLoginPresenter.showDataUser(objUser);
                }else{
                   objLoginPresenter.showError("Las credenciales son incorrectas.");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                objLoginPresenter.showError(t.getMessage());
            }
        });
    }
}