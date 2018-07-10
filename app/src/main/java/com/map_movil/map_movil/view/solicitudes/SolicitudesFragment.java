package com.map_movil.map_movil.view.solicitudes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.interactor.solicitudes.AdapterRecyclerViewSolicitudes;
import com.map_movil.map_movil.model.SolicitudesUsuario;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    public Integer intCodUser;
    public String strSimbolo;
    private AdapterRecyclerViewSolicitudes objAdapterSolicitudesUsuario;
    private ArrayList<SolicitudesUsuario> arrSolicitudesUsuarios = new ArrayList<SolicitudesUsuario>();
    private RecyclerView objRecycleView;
    private View view;
    private ApiAdapterSolicitudes objApiAdapterSolicitudes;
    private ApiServiceSolicitudes objApiServiceSolicitudes;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout relativeLayoutProgressBar;

    public SolicitudesFragment() {

    }


    @Override
    public void onResume(){
        super.onResume();
        showProgressBar(true);
        findSolicitud();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_solicitudes, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        relativeLayoutProgressBar = view.findViewById(R.id.relativeLayoutProgressBar);
        objApiAdapterSolicitudes = new ApiAdapterSolicitudes();

        objApiServiceSolicitudes = objApiAdapterSolicitudes.getClientService();
        objAdapterSolicitudesUsuario = new AdapterRecyclerViewSolicitudes(arrSolicitudesUsuarios, getActivity());

        objRecycleView = view.findViewById(R.id.recyclerSolicitudes);
        objRecycleView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        objAdapterSolicitudesUsuario.setOnClickListener(new AdapterRecyclerViewSolicitudes.OnItemClickListener() {
            @Override
            public void onitemClick(int position) {
                int intCodSolicitud = arrSolicitudesUsuarios.get(position).getIntCodSolicitud();
                Intent intent = new Intent(getActivity(), VerSolicitudActivity.class);
                intent.putExtra("intTipoOperacion", 2);
                intent.putExtra("intCodSolicitud", intCodSolicitud);
                startActivity(intent);
            }
        });
        objRecycleView.setAdapter(objAdapterSolicitudesUsuario);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                findSolicitud();
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    private void filtro(String texto){
        ArrayList<SolicitudesUsuario> arrayListFilter1 = new ArrayList<>();
        ArrayList<SolicitudesUsuario> arrayListFilter2 = new ArrayList<>();

        for(SolicitudesUsuario item: arrSolicitudesUsuarios){
            if(item.getIntCodSolicitud().toString().contains(texto)){
                arrayListFilter1.add(item);
            }
        }

        for(SolicitudesUsuario item: arrSolicitudesUsuarios){
            if(item.getStrNombreSolicitante().toString().contains(texto.toUpperCase())){
                arrayListFilter2.add(item);
            }
        }

        if(arrayListFilter1.size() > arrayListFilter2.size()){
            objAdapterSolicitudesUsuario.dataAdapterChange(arrayListFilter1);
        }else{
            objAdapterSolicitudesUsuario.dataAdapterChange(arrayListFilter2);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_buscador, menu);
        MenuItem searchItem = menu.findItem(R.id.buscador);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar...");

        super.onCreateOptionsMenu(menu, inflater);
    }


    //De la implementación de la busqueda y del menu para poder modificarlo.
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filtro(newText.toString());
        return false;
    }
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    private void findSolicitud(){
        Call<ArrayList<SolicitudesUsuario>> call = objApiServiceSolicitudes.getSolicitudesFinalizadasUsuario(intCodUser, strSimbolo);
        call.enqueue(new Callback<ArrayList<SolicitudesUsuario>>() {
            @Override
            public void onResponse(Call<ArrayList<SolicitudesUsuario>> call, Response<ArrayList<SolicitudesUsuario>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    arrSolicitudesUsuarios = response.body();
                    objAdapterSolicitudesUsuario.dataAdapterChange(arrSolicitudesUsuarios);
                } else {
                    objAdapterSolicitudesUsuario.dataAdapterChange(new ArrayList<SolicitudesUsuario>());
                }
                swipeRefreshLayout.setRefreshing(false);
                showProgressBar(false);
            }
            @Override
            public void onFailure(Call<ArrayList<SolicitudesUsuario>> call, Throwable t) {
                objAdapterSolicitudesUsuario.dataAdapterChange(new ArrayList<SolicitudesUsuario>());
                swipeRefreshLayout.setRefreshing(false);
                showProgressBar(false);
            }
        });
    }

    private void showProgressBar(boolean show){
        if(show == true){
            relativeLayoutProgressBar.setVisibility(View.VISIBLE);
        }else{
            relativeLayoutProgressBar.setVisibility(View.GONE);
        }
    }

}
