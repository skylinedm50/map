package com.map_movil.map_movil.api.login;

import com.map_movil.map_movil.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceLogin { //Aquí solo estaran las URL o mejor dicho su definición
    @GET("login/{user}/{password}")
    Call<List<User>> getLogin(@Path("user") String strUser, @Path("password") String strPassword);
}
