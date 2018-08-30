package com.map_movil.map_movil.view.solicitudes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.solicitudes.ApiAdapterSolicitudes;
import com.map_movil.map_movil.api.solicitudes.ApiServiceSolicitudes;
import com.map_movil.map_movil.interactor.solicitudes.AdapterRecyclerViewSolicitudes;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.SolicitudesUsuario;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesFragmentPresentImpl;
import com.map_movil.map_movil.presenter.solicitud.SolicitudesFragmentPresenter;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, SolicitudesFragmentView, UbicacionView {
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
    private UbicacionesPresenter ubicacionesPresenter;
    private SolicitudesFragmentPresenter solicitudesFragmentPresenter;
    private Spinner spinnerDepartamento;
    private Spinner spinnerMunicipio;
    private Spinner spinnerAldea;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LinearLayout linearLayoutPorcentage;
    private SharedPreferences sharedPreferences;


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
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());
        this.solicitudesFragmentPresenter = new SolicitudesFragmentPresentImpl(this, view.getContext());
        this.sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);

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
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar...");

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = getLayoutInflater();
                View viewInflater = layoutInflater.inflate(R.layout.dialog_download_solicitudes, null);
                spinnerDepartamento = viewInflater.findViewById(R.id.spinnerDepartamento);
                spinnerMunicipio = viewInflater.findViewById(R.id.spinnerMunicipio);
                spinnerAldea = viewInflater.findViewById(R.id.spinnerAldea);
                TextView textViewCancel = viewInflater.findViewById(R.id.ngButtonCancel);
                TextView textViewDownload = viewInflater.findViewById(R.id.ngButtonDownload);
                linearLayoutPorcentage = viewInflater.findViewById(R.id.LinearLayoutPorcentage);

                spinnerDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getMunicipios(parent.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spinnerMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getAldeas(parent.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                builder.setTitle("Descargar-Solicitudes");
                builder.setView(viewInflater);
                builder.setCancelable(false);
                dialog = builder.create();

                textViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                textViewDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int intCodUser = sharedPreferences.getInt("codigo",0);
                        downloadSolicitudes(spinnerAldea.getSelectedItem().toString(), intCodUser);
                    }
                });

                dialog.show();
                getDepartamentos();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public void downloadSolicitudes(String Aldea, int intCodUser) {
        linearLayoutPorcentage.setVisibility(View.VISIBLE);
        this.solicitudesFragmentPresenter.downloadSolicitudes(Aldea, intCodUser);
    }

    @Override
    public void finishDownloadSolicitudes() {
        linearLayoutPorcentage.setVisibility(View.GONE);
        dialog.cancel();
    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, departamentos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamento.setAdapter(arrayAdapter);
    }

    @Override
    public void getMunicipios(String depto) {
        this.ubicacionesPresenter.getMunicipios(depto);
    }


    @Override
    public void cargarMunicipios(ArrayList<String> municipios) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, municipios);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMunicipio.setAdapter(arrayAdapter);
    }

    @Override
    public void getAldeas(String muni) {
        this.ubicacionesPresenter.getAldeas(muni);
    }

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {
        List<String> listString = new ArrayList<String>();
        for (Aldeas list: aldeas){
            listString.add(list.getCod_aldea() + "-" + list.getDesc_aldea());
        }
        ArrayAdapter<String>  arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, listString);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAldea.setAdapter(arrayAdapter);
    }

    @Override
    public void getCaserios(String aldea) {

    }

    @Override
    public void cargarCaserios(List<Caserios> caserios) {

    }
}
