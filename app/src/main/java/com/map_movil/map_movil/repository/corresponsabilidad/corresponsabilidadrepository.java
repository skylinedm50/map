package com.map_movil.map_movil.repository.corresponsabilidad;

import android.content.Context;
import android.widget.Toast;

import com.map_movil.map_movil.api.corresponsabilidad.ApiAdapterCorresponsabilidad;
import com.map_movil.map_movil.api.corresponsabilidad.ApiServiceCorresponsabilidad;
import com.map_movil.map_movil.model.Corresponsabilidades;
import com.map_movil.map_movil.presenter.corresponsabilidad.corresponsabilidadpresenter;
import com.map_movil.map_movil.view.corresponsabilidad.CorresponsabilidadFragment;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class corresponsabilidadrepository implements corresponsabiliadrepositoryImpl {

    private Context context;
    private corresponsabilidadpresenter corresponsabilidadpresenter;
    private ApiServiceCorresponsabilidad serviceCorresponsabilidad;
    private ArrayList<Corresponsabilidades> listCorresponsabilidad = new ArrayList<>();
    private CorresponsabilidadFragment.AdaptadorCorresponsabilidad adaptadorcorresponsabilidad;
    private ApiAdapterCorresponsabilidad adapterCorresponsabilidad;

    public corresponsabilidadrepository(Context context , corresponsabilidadpresenter correspresenter){
        this.context = context;
        this.corresponsabilidadpresenter = correspresenter;
    }

    @Override
    public void findCorrByTitular(String strCodIdentidad) {

        adapterCorresponsabilidad = new ApiAdapterCorresponsabilidad();
        serviceCorresponsabilidad = adapterCorresponsabilidad.getClientService();

        Call<ArrayList<Corresponsabilidades>> call = serviceCorresponsabilidad.getCorresponsabilidadInfo(strCodIdentidad);

        call.enqueue(new Callback<ArrayList<Corresponsabilidades>>() {
            @Override
            public void onResponse(Call<ArrayList<Corresponsabilidades>> call, Response<ArrayList<Corresponsabilidades>> response) {
                if(response.body() != null && response.body().size()>0) {
                    listCorresponsabilidad = response.body();

                    corresponsabilidadpresenter.showdata(listCorresponsabilidad);
                }else{
                    corresponsabilidadpresenter.findDataShowMessage("No se encontraron datos",false);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Corresponsabilidades>> call, Throwable t) {
                /* showProgressBar(false);*/
                  corresponsabilidadpresenter.findDataShowMessage("Error al solicitar datos",false);

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
