package com.map_movil.map_movil.repository.ubicaciones;

import android.content.Context;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.ubicaciones.ApiAdapterUbicacion;
import com.map_movil.map_movil.api.ubicaciones.ApiServicesUbicacion;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UbicacionesRepositoryImpl implements UbicacionesRepository {
    ApiAdapterUbicacion apiAdapterUbicacion;
    ApiServicesUbicacion apiServicesUbicacion;
    private  UbicacionesPresenter ubicacionesPresenter;
    private Context context;

    public UbicacionesRepositoryImpl( UbicacionesPresenter ubicacionesPresenter, Context context) {
        this.context = context;
        this.apiAdapterUbicacion = new ApiAdapterUbicacion();
        this.apiServicesUbicacion = this.apiAdapterUbicacion.getClientService();
        this.ubicacionesPresenter = ubicacionesPresenter;
    }


    @Override
    public void getDepartamentos() {
        String[] arrayString = context.getResources().getStringArray(R.array.departamentos_array);
        ubicacionesPresenter.cargarDepartamentos(new ArrayList<String>(Arrays.asList(arrayString)));
    }

    @Override
    public void getMunicipios(String depto) {
        String[] arrayList = context.getResources().getStringArray(R.array.municipios_array);
        ArrayList<String> arrayListNew = new ArrayList<String>();

        for (String item: arrayList){
            String[] arrayItem = item.toString().split("-");
            String string = arrayItem[0].substring(0,2);

            if(depto.contains(string)){
                arrayListNew.add(item);
            }
        }

        ubicacionesPresenter.cargarMunicipios(arrayListNew);
    }

    @Override
    public void getAldeas(String muni) {
        String[] arrayList = context.getResources().getStringArray(R.array.aldeas_array);
        List<Aldeas> arrayListNew = new ArrayList<>();

        for (String item: arrayList){
            String[] arrayItem = item.toString().split("-");
            String string = arrayItem[0].substring(0,4);

            if(muni.contains(string)){
                Aldeas aldeas = new Aldeas();
                aldeas.setCod_aldea(arrayItem[0]);
                aldeas.setDesc_aldea(arrayItem[1]);
                arrayListNew.add(aldeas);
            }
        }

        ubicacionesPresenter.cargarAldeas(arrayListNew);
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
