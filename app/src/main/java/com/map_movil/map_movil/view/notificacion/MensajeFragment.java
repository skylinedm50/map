package com.map_movil.map_movil.view.notificacion;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
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
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdapterItemMunicipioRecyclerView;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.presenter.notificacion.MensajeFragmentPresenter;
import com.map_movil.map_movil.presenter.notificacion.MensajeFragmentPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;

import java.util.ArrayList;
import java.util.List;

public class MensajeFragment extends Fragment implements MensajeFragmentView, UbicacionView {
    private View view;
    private Spinner spinnerDepartamento;
    private AdapterItemMunicipioRecyclerView adapterItemMunicipioRecyclerView;
    private ArrayList<String> arrayListMunicipio = new ArrayList<>();
    private UbicacionesPresenter ubicacionesPresenter;
    private ProgressBar progressBar;
    private LinearLayout linearLayoutData;
    private RecyclerView recyclerViewMunicipio;
    private MenuItem sendDataItem;
    private TextInputEditText textInputEditTextMessage;
    private MensajeFragmentPresenter mensajeFragmentPresenter;

    public MensajeFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mensajes, container, false);
        ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());
        mensajeFragmentPresenter = new MensajeFragmentPresenterImpl(view.getContext(), this);
        textInputEditTextMessage = view.findViewById(R.id.textInputEditTextMessage);
        progressBar = view.findViewById(R.id.progressBar);
        linearLayoutData = view.findViewById(R.id.linearLayoutData);
        spinnerDepartamento = view.findViewById(R.id.spinnerDepartamento);
        recyclerViewMunicipio = view.findViewById(R.id.recyclerViewMunicipio);
        recyclerViewMunicipio.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapterItemMunicipioRecyclerView = new AdapterItemMunicipioRecyclerView(arrayListMunicipio);
        recyclerViewMunicipio.setAdapter(adapterItemMunicipioRecyclerView);
        spinnerDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getMunicipios(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getDepartamentos();

        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        sendDataItem = menu.findItem(R.id.sendData);
        searchItem.setEnabled(false);
        searchItem.setVisible(false);
        sendDataItem.setEnabled(true);
        sendDataItem.setVisible(true);
        sendDataItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showProgressBar(true);
                mensajeFragmentPresenter.sendMessage(adapterItemMunicipioRecyclerView.getJsonArraySelected(),
                        textInputEditTextMessage.getText().toString());
                return false;
            }
        });
    }


    @Override
    public void closeActivity() {
        getActivity().finish();
    }

    @Override
    public void showMessage(String strMessage) {
        Toast.makeText(view.getContext(), strMessage, Toast.LENGTH_SHORT).show();
        showProgressBar(false);
    }

    @Override
    public void showProgressBar(boolean bolValue) {
        sendDataItem.setEnabled((bolValue)? false: true);
        linearLayoutData.setVisibility((bolValue)? View.GONE: View.VISIBLE);
        progressBar.setVisibility((!bolValue)? View.GONE: View.VISIBLE);
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
