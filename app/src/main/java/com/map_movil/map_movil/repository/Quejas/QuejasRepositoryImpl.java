package com.map_movil.map_movil.repository.Quejas;

import com.google.gson.JsonObject;
import com.map_movil.map_movil.api.quejas.ApiAdapterQuejas;
import com.map_movil.map_movil.api.quejas.ApiServicesQuejas;
import com.map_movil.map_movil.model.QuejasDenuncias;
import com.map_movil.map_movil.model.ResponseApi;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuejasRepositoryImpl implements QuejasRepository {

   private QuejasPresenter quejasPresenter;

   public QuejasRepositoryImpl(QuejasPresenter quejasPresenter){
       this.quejasPresenter = quejasPresenter;
   }

    @Override
    public void SolicitarQuejas(int usuario) {

        ApiAdapterQuejas apiAdapterQuejas = new ApiAdapterQuejas();
        ApiServicesQuejas apiServiceQuejas = apiAdapterQuejas.getClientService();
        Call<ArrayList<QuejasDenuncias>> call = apiServiceQuejas.getQuejasDenuncias(usuario);
        call.enqueue(new Callback<ArrayList<QuejasDenuncias>>() {
            @Override
            public void onResponse(Call<ArrayList<QuejasDenuncias>> call, Response<ArrayList<QuejasDenuncias>> response) {
                if(response.isSuccessful()){
                    quejasPresenter.MostarQuejas(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuejasDenuncias>> call, Throwable t) {

            }
        });
   }

    @Override
    public void RegistrarQueja(JsonObject jsonQuejaDenuncia) {

       ApiAdapterQuejas apiAdapterQuejas = new ApiAdapterQuejas();
       ApiServicesQuejas apiServicesQuejas = apiAdapterQuejas.getClientService();

       Call<ResponseApi> call = apiServicesQuejas.IngregarQueja(jsonQuejaDenuncia);
       call.enqueue(new Callback<ResponseApi>() {
           @Override
           public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
               if(response.isSuccessful()){
                   quejasPresenter.ActualizarDatos();
               }
           }

           @Override
           public void onFailure(Call<ResponseApi> call, Throwable t) {

           }
       });

    }
}
