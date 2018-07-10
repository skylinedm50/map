package com.map_movil.map_movil.api.login;

import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServiceLogin { //Aquí solo estaran las URL o mejor dicho su definición
    @GET("login/{user}/{password}")
    Call<List<User>> getLogin(@Path("user") String strUser, @Path("password") String strPassword);

    @FormUrlEncoded
    @POST("login/change_key")
    Call<ResponseApi> changePassword(@Field("userId") int userId,
                                     @Field("newPassword") String newPassword);
}
