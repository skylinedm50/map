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
import android.widget.Toast;

import com.map_movil.map_movil.R;
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

public class SolicitudesFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, SolicitudesFragmentView, UbicacionView {

    public Integer intCodUser;
    public String strSimbolo;
    private AdapterRecyclerViewSolicitudes objAdapterSolicitudesUsuario;
    private ArrayList<SolicitudesUsuario> arrSolicitudesUsuarios = new ArrayList<SolicitudesUsuario>();
    private RecyclerView objRecycleView;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout relativeLayoutTextMessage;
    private UbicacionesPresenter ubicacionesPresenter;
    private SolicitudesFragmentPresenter solicitudesFragmentPresenter;
    private Spinner spinnerDepartamento;
    private Spinner spinnerMunicipio;
    private Spinner spinnerAldea;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LinearLayout linearLayoutPorcentage;
    private SharedPreferences sharedPreferences;
    private TextView textViewPorcentageProcess;

    private LayoutInflater layoutInflater;
    private View viewInflater;

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
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());
        this.solicitudesFragmentPresenter = new SolicitudesFragmentPresentImpl(this, view.getContext());
        this.sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        relativeLayoutTextMessage = view.findViewById(R.id.relativeLayoutTextMessage);//Pendiente de uso.

        objAdapterSolicitudesUsuario = new AdapterRecyclerViewSolicitudes(arrSolicitudesUsuarios, getActivity());

        objRecycleView = view.findViewById(R.id.recyclerSolicitudes);
        objRecycleView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        objAdapterSolicitudesUsuario.setOnClickListener(new AdapterRecyclerViewSolicitudes.OnItemClickListener() {
            @Override
            public void onitemClick(int position) {
                int intCodSolicitud = arrSolicitudesUsuarios.get(position).getIntCodSolicitud();
                Intent intent = new Intent(getActivity(), ShowAddSolicitudActivity.class);
                intent.putExtra("intTipoOperacion", 2);
                intent.putExtra("intCodSolicitud", intCodSolicitud);
                startActivity(intent);
            }
        });
        objRecycleView.setAdapter(objAdapterSolicitudesUsuario);

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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        findByTitularOrSolicitud(newText.toString());
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

    @Override
    public void getSolicitudesGestionadas() {
        showSwipeRefreshLayout(true);
        solicitudesFragmentPresenter.getSolicitudesGestionadas(intCodUser, strSimbolo);
    }

    private void showSwipeRefreshLayout(boolean show){
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void downloadSolicitudes(String Aldea, int intCodUser) {
        linearLayoutPorcentage.setVisibility(View.VISIBLE);
        this.solicitudesFragmentPresenter.downloadSolicitudes(Aldea, intCodUser);
    }

    @Override
    public void showSolicitudesGestionadas(ArrayList<SolicitudesUsuario> arrayListSolicitudes) {
        arrSolicitudesUsuarios = arrayListSolicitudes;
        objAdapterSolicitudesUsuario.dataAdapterChange(arrSolicitudesUsuarios);
        swipeRefreshLayout.setRefreshing(false);
        showSwipeRefreshLayout(false);
    }

    @Override
    public void finishDownloadSolicitudes() {
        linearLayoutPorcentage.setVisibility(View.GONE);
        dialog.cancel();
    }

    @Override
    public void changePorcentage(String strPorcentage) {
        textViewPorcentageProcess.setText(strPorcentage);
    }

    @Override
    public void showError(String strError) {
        Toast.makeText(view.getContext(), strError, Toast.LENGTH_SHORT).show();
        showSwipeRefreshLayout(false);
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
