package com.map_movil.map_movil.repository.login;

import com.map_movil.map_movil.api.login.ApiAdapterLogin;
import com.map_movil.map_movil.api.login.ApiServiceLogin;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.presenter.login.ChangePasswordPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordRepositoryImpl implements ChangePasswordRepository {
    private ApiAdapterLogin apiAdapterLogin;
    private ApiServiceLogin apiServiceLogin;
    private ChangePasswordPresenter changePasswordPresenter;
    private ResponseApi responseApi;


    public ChangePasswordRepositoryImpl(ChangePasswordPresenter changePasswordPresenter){
        this.changePasswordPresenter = changePasswordPresenter;
    }

    @Override
    public void setChangePassword(int intCodUser, String strNewPassword) {
        apiAdapterLogin = new ApiAdapterLogin();
        apiServiceLogin = apiAdapterLogin.getClientService();

        Call<ResponseApi> call = apiServiceLogin.changePassword(intCodUser, strNewPassword);

        call.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
               if(response.body() != null){
                   responseApi = response.body();
                   if(responseApi.getIntState() == 1){
                       changePasswordPresenter.closeChangePassword();
                   }
                }else{
                    changePasswordPresenter.showError("Error, la instrucción no se logro ejecutar.");
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                changePasswordPresenter.showError(t.getMessage());
            }
        });
    }
}
