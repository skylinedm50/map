package com.map_movil.map_movil.view.solicitudes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesPresenter;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesPresenterImpl;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SolicitudesNoRealizadasFragment extends Fragment implements SolicitudesView{
    public Integer intCodUser;
    private SolicitudesPresenter objSolicitudesPresenter;
    private RecyclerView.Adapter objAdapterSolicitudesUsuario;
    private ArrayList<SolicitudesUsuario> arrSolicitudesUsuarios;

    private RecyclerView objRecycleView;
    private LinearLayoutManager objLinearLayoutManager;
    private View objView;

    private ApiAdapterSolicitudes objApiAdapterSolicitudes;
    private ApiServiceSolicitudes objApiServiceSolicitudes;

    public SolicitudesNoRealizadasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objSolicitudesPresenter = new SolicitudesPresenterImpl(this);
        objView = inflater.inflate(R.layout.fragment_solicitudes, container, false);
        configFragment();

        return objView;
    }

    public void configFragment(){
        /*objRecycleView = objView.findViewById(R.id.recyclerSolicitudes);
        objRecycleView.setHasFixedSize(true);
        objLinearLayoutManager = new LinearLayoutManager(getContext());
        objLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        objRecycleView.setLayoutManager(objLinearLayoutManager);

        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();
        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();

        Call<ArrayList<SolicitudesUsuario>> call = objApiServiceSolicitudes.getSolicitudesFinalizadasUsuario(intCodUser, "noResueltas");
        call.enqueue(new Callback<ArrayList<SolicitudesUsuario>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesUsuario>> call, Response<ArrayList<SolicitudesUsuario>> response) {
                if(response.body() != null){
                    arrSolicitudesUsuarios = response.body();
                    objAdapterSolicitudesUsuario = new AdapterRecyclerViewSolicitudes(arrSolicitudesUsuarios, R.id.linearLayoutMain, getActivity());
                    objRecycleView.setAdapter(objAdapterSolicitudesUsuario);

                }else{
                    arrSolicitudesUsuarios = new ArrayList<SolicitudesUsuario>();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SolicitudesUsuario>> call, Throwable t) {
                arrSolicitudesUsuarios = new ArrayList<SolicitudesUsuario>();
            }
        });*/
    }

    @Override
    public void getSolicitudesGestionadasUser(Integer intCodUser, String strSimbolo) {

    }

    @Override
    public void showDataSolicitudesGestionadasUser(ArrayList<SolicitudesUsuario> objSolicitudesUsuario) {
    }

    @Override
    public void showError(String strError) {
        Toast.makeText(getView().getContext(), strError, Toast.LENGTH_SHORT).show();
    }
}
