package com.map_movil.map_movil.view.solicitudes;

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
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdapterRecyclerViewSolicitudes;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesFragmentPresentImpl;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesFragmentPresenter;

import java.util.ArrayList;

public class SolicitudesFragment extends Fragment implements MenuItem.OnActionExpandListener, SolicitudesFragmentView{

    public Integer intCodUser;
    public String strSimbolo;
    private AdapterRecyclerViewSolicitudes adapterRecyclerViewSolicitudes;
    private ArrayList<SolicitudesUsuario> arrSolicitudesUsuarios = new ArrayList<SolicitudesUsuario>();
    private RecyclerView objRecycleView;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout relativeLayoutTextMessage;
    private SolicitudesFragmentPresenter solicitudesFragmentPresenter;

    public SolicitudesFragment() {
    }

    @Override
    public void onResume(){
        super.onResume();
        getSolicitudesGestionadas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_solicitudes, container, false);
        this.solicitudesFragmentPresenter = new SolicitudesFragmentPresentImpl(this, view.getContext());

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        relativeLayoutTextMessage = view.findViewById(R.id.relativeLayoutTextMessage);//Pendiente de uso.

        adapterRecyclerViewSolicitudes = new AdapterRecyclerViewSolicitudes(arrSolicitudesUsuarios, getActivity());

        objRecycleView = view.findViewById(R.id.recyclerSolicitudes);
        objRecycleView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        objRecycleView.setAdapter(adapterRecyclerViewSolicitudes);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSolicitudesGestionadas();
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    private void findByTitularOrSolicitud(String texto){
        ArrayList<SolicitudesUsuario> arrayListFilter1 = new ArrayList<>();
        ArrayList<SolicitudesUsuario> arrayListFilter2 = new ArrayList<>();

        for(SolicitudesUsuario item: arrSolicitudesUsuarios){
            if(item.getIntCodSolicitud().toString().contains(texto)){
                arrayListFilter1.add(item);
            }
        }

        for(SolicitudesUsuario item: arrSolicitudesUsuarios){
            if(item.getStrNombreSolicitante().contains(texto.toUpperCase())){
                arrayListFilter2.add(item);
            }
        }

        if(arrayListFilter1.size() > arrayListFilter2.size()){
            adapterRecyclerViewSolicitudes.dataAdapterChange(arrayListFilter1);
        }else{
            adapterRecyclerViewSolicitudes.dataAdapterChange(arrayListFilter2);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Buscar...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findByTitularOrSolicitud(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    public void getSolicitudesGestionadas() {
        showSwipeRefreshLayout(true);
        solicitudesFragmentPresenter.getSolicitudesGestionadas(intCodUser, strSimbolo);
    }

    private void showSwipeRefreshLayout(boolean show){
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void showSolicitudesGestionadas(ArrayList<SolicitudesUsuario> arrayListSolicitudes) {
        arrSolicitudesUsuarios = arrayListSolicitudes;
        adapterRecyclerViewSolicitudes.dataAdapterChange(arrSolicitudesUsuarios);
        swipeRefreshLayout.setRefreshing(false);
        showSwipeRefreshLayout(false);
    }

    @Override
    public void showError(String strError) {
        Toast.makeText(view.getContext(), strError, Toast.LENGTH_SHORT).show();
        showSwipeRefreshLayout(false);
    }
}
