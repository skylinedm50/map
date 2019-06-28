package com.map_movil.map_movil.view.downloadData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdapterItemMunicipioRecyclerView;
import com.map_movil.map_movil.adapter.AdpterItemMunicipiosSaveDownload;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.presenter.downloadData.DownloadDataFragmentPresenter;
import com.map_movil.map_movil.presenter.downloadData.DownloadDataFragmentPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;
import java.util.ArrayList;
import java.util.List;

public class DownloadDataFragment extends Fragment implements DownloadDataFragmentView, UbicacionView {

   private View view;
   private Spinner spinnerDepartamento;
   private RecyclerView recyclerViewMunicipio;
   private TextView textViewDepartamento;
   private TextView textViewCantHogares;
   private TextView textViewCantHistorialPago;
   private TextView textViewCantSolicitudes;
   private TextView textViewCantQuejas;
   private AdapterItemMunicipioRecyclerView adapterItemMunicipioRecyclerView;
   private ArrayList<String> arrayListMunicipio = new ArrayList<>();
   private UbicacionesPresenter ubicacionesPresenter;
   private ProgressBar progressBar;
   private LinearLayout linearLayoutDetail;
   private DownloadDataFragmentPresenter downloadDataFragmentPresenter;
   private ArrayList<String> stringListMunicipiosSaveDownload = new ArrayList<>();
   private AdpterItemMunicipiosSaveDownload adpterItemMunicipiosSaveDownload;
   private RecyclerView recyclerViewMunicipiosSave;
   private SharedPreferences sharedPreferencesUsuario;

    public DownloadDataFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_download_data, container, false);
        this.downloadDataFragmentPresenter = new DownloadDataFragmentPresenterImpl(this, view.getContext());
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());
        this.linearLayoutDetail = view.findViewById(R.id.linearLayoutDetail);
        this.progressBar = view.findViewById(R.id.progressBar);
        this.spinnerDepartamento = view.findViewById(R.id.spinnerDepartamento);
        this.recyclerViewMunicipio = view.findViewById(R.id.recyclerViewMunicipio);
        this.recyclerViewMunicipio.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        this.textViewDepartamento = view.findViewById(R.id.textViewDepartamento);
        this.textViewCantHogares = view.findViewById(R.id.textViewCantHogares);
        this.textViewCantHistorialPago = view.findViewById(R.id.textViewCantHistorialPago);
        this.textViewCantSolicitudes = view.findViewById(R.id.textViewCantSolicitudes);
        this.textViewCantQuejas = view.findViewById(R.id.textViewCantQuejas);
        this.adapterItemMunicipioRecyclerView = new AdapterItemMunicipioRecyclerView(arrayListMunicipio);
        this.recyclerViewMunicipio.setAdapter(adapterItemMunicipioRecyclerView);
        this.sharedPreferencesUsuario    = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);

        this.spinnerDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getMunicipios(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        this.recyclerViewMunicipiosSave = view.findViewById(R.id.recyclerViewMunicipiosSave);
        this.recyclerViewMunicipiosSave.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        this.adpterItemMunicipiosSaveDownload = new AdpterItemMunicipiosSaveDownload(stringListMunicipiosSaveDownload);
        this.recyclerViewMunicipiosSave.setAdapter(adpterItemMunicipiosSaveDownload);

        getDepartamentos();
        findDetailDataLocal();

        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        MenuItem downloadDataItem = menu.findItem(R.id.download);
        searchItem.setEnabled(false);
        searchItem.setVisible(false);
        downloadDataItem.setEnabled(true);
        downloadDataItem.setVisible(true);
        downloadDataItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                downloadData(adapterItemMunicipioRecyclerView.getArrayListSelected(), spinnerDepartamento.getSelectedItem().toString(), sharedPreferencesUsuario.getInt("codigo",0));
                return false;
            }
        });
    }

    @Override
    public void findDetailDataLocal() {
        linearLayoutDetail.setVisibility(View.GONE);
        downloadDataFragmentPresenter.findDetailDataLocal();
    }

    @Override
    public void showDetailDataLocal(int[] arrayIntCant, ArrayList<String> arrayListMunicipio, String strDepartamentoSelected) {
        showProgressBar(false);
        textViewCantHogares.setText(String.valueOf(arrayIntCant[0]));
        textViewCantHistorialPago.setText(String.valueOf(arrayIntCant[1]));
        textViewCantSolicitudes.setText(String.valueOf(arrayIntCant[2]));
        textViewCantQuejas.setText(String.valueOf(arrayIntCant[3]));
        textViewDepartamento.setText(strDepartamentoSelected);
        adpterItemMunicipiosSaveDownload.adapterDataChange(arrayListMunicipio);
        linearLayoutDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String strMessage) {
        Toast.makeText(view.getContext(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(boolean bolShow) {
        progressBar.setVisibility((bolShow)? View.VISIBLE: View.GONE);
    }

    @Override
    public void downloadData(ArrayList<String> arrayListMunicipiosSelect, String strDepartamento, int Usuario) {
        linearLayoutDetail.setVisibility(View.GONE);
        showProgressBar(true);
        downloadDataFragmentPresenter.downloadData(arrayListMunicipiosSelect, strDepartamento, Usuario);
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
        arrayListMunicipio = municipios;
        adapterItemMunicipioRecyclerView.adapterDataChange(arrayListMunicipio);
    }

    @Override
    public void getAldeas(String muni) {

    }

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {

    }

    @Override
    public void getCaserios(String aldea) {

    }

    @Override
    public void cargarCaserios(List<Caserios> caserios) {

    }
}
