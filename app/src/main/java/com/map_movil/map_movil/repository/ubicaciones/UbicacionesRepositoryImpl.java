package com.map_movil.map_movil.repository.ubicaciones;

import android.content.Context;

import com.map_movil.map_movil.api.ubicaciones.ApiAdapterUbicacion;
import com.map_movil.map_movil.api.ubicaciones.ApiServicesUbicacion;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Departamentos;
import com.map_movil.map_movil.model.Municipios;

import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbicacionesRepositoryImpl implements UbicacionesRepository {
    ApiAdapterUbicacion apiAdapterUbicacion;
    ApiServicesUbicacion apiServicesUbicacion;
    private  UbicacionesPresenter ubicacionesPresenter;

    public UbicacionesRepositoryImpl( UbicacionesPresenter ubicacionesPresenter)
    {
        this.apiAdapterUbicacion = new ApiAdapterUbicacion();
        this.apiServicesUbicacion = this.apiAdapterUbicacion.getClientService();
        this.ubicacionesPresenter = ubicacionesPresenter;
    }


    @Override
    public void getDepartamentos() {
        final Call<List<Departamentos>> departamentos = this.apiServicesUbicacion.getAllDepartamentos();
        departamentos.enqueue(new Callback<List<Departamentos>>() {
            @Override
            public void onResponse(Call<List<Departamentos>> call, Response<List<Departamentos>> response) {
                if(response.isSuccessful()){
                    if(response.body().size()>0){
                        ubicacionesPresenter.cargarDepartamentos(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Departamentos>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getMunicipios(String depto) {
        Call<List<Municipios>> municipios = this.apiServicesUbicacion.getAllMunicipios(depto);
        municipios.enqueue(new Callback<List<Municipios>>() {
            @Override
            public void onResponse(Call<List<Municipios>> call, Response<List<Municipios>> response) {
                if(response.isSuccessful()){
                    if(response.body().size()>0){
                        ubicacionesPresenter.cargarMunicipios(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Municipios>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getAldeas(String muni) {
        Call<List<Aldeas>> aldeas = this.apiServicesUbicacion.getAllAldeas(muni);
        aldeas.enqueue(new Callback<List<Aldeas>>() {
            @Override
            public void onResponse(Call<List<Aldeas>> call, Response<List<Aldeas>> response) {
                if(response.isSuccessful()){

                    if(response.body().size()>0){
                        ubicacionesPresenter.cargarAldeas(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Aldeas>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getCaserios(String aldea) {
        Call<List<Caserios>> Caserios = this.apiServicesUbicacion.getAllCaserios(aldea);
        Caserios.enqueue(new Callback<List<com.map_movil.map_movil.model.Caserios>>() {
            @Override
            public void onResponse(Call<List<Caserios>> call, Response<List<Caserios>> response) {
                if(response.isSuccessful()){

                    if(response.body().size()>0){
                        ubicacionesPresenter.cargarCaserios(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Caserios>> call, Throwable t) {

            }
        });
    }
}
