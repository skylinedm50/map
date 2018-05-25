package com.map_movil.map_movil.api;

import com.map_movil.map_movil.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService { //Aquí solo estaran las URL o mejor dicho su definición
    @GET("login/{User}/{password}")
    Call<List<User>> getLogin(@Path("User") String strUser, @Path("password") String strPassword);
}
