package com.map_movil.map_movil.view.solicitudes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.interactor.solicitudes.AdapterRecyclerViewSolicitudes;
import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SolicitudesFragment extends Fragment {
    public Integer intCodUser;
    public String strSimbolo;
    private AdapterRecyclerViewSolicitudes objAdapterSolicitudesUsuario;
    private ArrayList<SolicitudesUsuario> arrSolicitudesUsuarios = new ArrayList<SolicitudesUsuario>();
    private RecyclerView objRecycleView;
    private LinearLayoutManager objLinearLayoutManager;
    private View objView;
    private ApiAdapterSolicitudes objApiAdapterSolicitudes;
    private ApiServiceSolicitudes objApiServiceSolicitudes;
    private TextInputEditText textInputEditTextBuscar;

    public SolicitudesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objView = inflater.inflate(R.layout.fragment_solicitudes, container, false);


        /*textInputEditTextBuscar = objView.findViewById(R.id.textEditBuscar);
        textInputEditTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtro(s.toString());
            }
        });*/

        configFragment();

        return objView;
    }

    private void filtro(String texto){
        ArrayList<SolicitudesUsuario> arrayListFilter = new ArrayList<>();
        for(SolicitudesUsuario item: arrSolicitudesUsuarios){
            if( item.getIntCodSolicitud().toString().contains(texto)){
                arrayListFilter.add(item);
            }
        }

        objAdapterSolicitudesUsuario.dataAdapterChange(arrayListFilter);
    }

    public void configFragment() {
        objRecycleView = objView.findViewById(R.id.recyclerSolicitudes);
        objRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();

        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();
        objAdapterSolicitudesUsuario = new AdapterRecyclerViewSolicitudes(arrSolicitudesUsuarios, getContext());

        objAdapterSolicitudesUsuario.setOnClickListener(new AdapterRecyclerViewSolicitudes.OnItemClickListener() {
            @Override
            public void onitemClick(int position) {
                int intCodSolicitud = arrSolicitudesUsuarios.get(position).getIntCodSolicitud();
                Intent intent = new Intent(getContext(), VerSolicitudActivity.class);
                intent.putExtra("intTipoOperacion", 2);
                intent.putExtra("intCodSolicitud", intCodSolicitud);
                startActivity(intent);
            }
        });
        objRecycleView.setAdapter(objAdapterSolicitudesUsuario);


        Call<ArrayList<SolicitudesUsuario>> call = objApiServiceSolicitudes.getSolicitudesFinalizadasUsuario(intCodUser, strSimbolo);
        call.enqueue(new Callback<ArrayList<SolicitudesUsuario>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesUsuario>> call, Response<ArrayList<SolicitudesUsuario>> response) {
                if (response.body() != null) {
                    arrSolicitudesUsuarios = response.body();
                    objAdapterSolicitudesUsuario.dataAdapterChange(arrSolicitudesUsuarios);
                } else {
                    objAdapterSolicitudesUsuario.dataAdapterChange(new ArrayList<SolicitudesUsuario>());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SolicitudesUsuario>> call, Throwable t) {
                objAdapterSolicitudesUsuario.dataAdapterChange(new ArrayList<SolicitudesUsuario>());
            }
        });
    }
}
