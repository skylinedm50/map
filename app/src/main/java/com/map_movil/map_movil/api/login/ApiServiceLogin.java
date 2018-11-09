package com.map_movil.map_movil.api.login;

import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServiceLogin { //Aquí solo estaran las URL o mejor dicho su definición
    @FormUrlEncoded
    @POST("login")
    Call<List<User>> getLogin(@Field("user") String strUser, @Field("password") String strPassword);

    @FormUrlEncoded
    @POST("login/change_key")
    Call<ResponseApi> changePassword(@Field("userId") int userId,
                                     @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("login/set_token")
    Call<ResponseApi> setTokenNotification(@Field("userId") int userId,
                                     @Field("token") String strToken);
}
