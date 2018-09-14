package com.map_movil.map_movil.view.Quejas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.quejas_denuncias_adapter;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.QuejasDenuncias;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;
import java.util.ArrayList;
import java.util.List;

public class QuejasFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener , UbicacionView , QuejasOfflineView {

    public  Integer codigo_accion;
    private quejas_denuncias_adapter denuncias_adapter;
    private RecyclerView rv_quejas;
    private  LinearLayoutManager linearLayoutManager;
    private Spinner spinnerDepartamento;
    private Spinner spinnerMunicipio;
    private Spinner spinnerAldea;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private UbicacionesPresenter ubicacionesPresenter;
    private LinearLayout linearLayoutPorcentaje;
    private SharedPreferences sharedPreferences;

    public QuejasFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_quejas , container , false);

        rv_quejas = (RecyclerView)view.findViewById(R.id.rvQuejas);
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());
        this.sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        denuncias_adapter = new quejas_denuncias_adapter(getContext() , codigo_accion );
        rv_quejas.setAdapter(denuncias_adapter);
        rv_quejas.setLayoutManager(linearLayoutManager);

        denuncias_adapter.SolicitarQuejas();
        setHasOptionsMenu(true);
        return  view;
    }


    @Override
    public void onResume() {
        super.onResume();
        this.denuncias_adapter.SolicitarQuejas();
        this.rv_quejas.post(new Runnable() {
            @Override
            public void run() {
                rv_quejas.smoothScrollToPosition(denuncias_adapter.getItemCount());
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        MenuItem nextItem = menu.findItem(R.id.next);
        MenuItem downloadItem = menu.findItem(R.id.download);
        MenuItem saveItem = menu.findItem(R.id.saveServer);

        nextItem.setVisible(false);
        nextItem.setEnabled(false);

        /*comentar*/
      /*  downloadItem.setVisible(true);
        downloadItem.setEnabled(true);
        saveItem.setVisible(true);
        saveItem.setEnabled(true);*/
        /*********/

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar...");

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void filtro(String Texto){
        ArrayList<QuejasDenuncias> quejas_denuncias = this.denuncias_adapter.ArrayAdapterQuejas( this.codigo_accion);
        ArrayList<QuejasDenuncias> filtro_adapter   = new ArrayList<>();

        Texto = (Texto.trim().equals(""))?"0-9":Texto;

        for(int x = 0; x < quejas_denuncias.size(); x++){
           if(Texto.matches("^[0-9]*$") || Texto =="0-9" ){
               if(String.valueOf( quejas_denuncias.get(x).getCodigo_solicitud() ).matches("^"+Texto+".*") || Texto =="0-9"){
                   filtro_adapter.add(quejas_denuncias.get(x));
               }
           }
           else{
               if(quejas_denuncias.get(x).getNombre_solicitante().matches("^"+Texto.toUpperCase()+".*")){
                   filtro_adapter.add(quejas_denuncias.get(x));
               }
           }
        }

        this.denuncias_adapter.ActualizarFiltro(filtro_adapter , filtro_adapter.size() , this.codigo_accion);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        filtro(s);
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.download:
                builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = getLayoutInflater();
                View viewInflater = layoutInflater.inflate(R.layout.dialog_download_solicitudes, null);
                spinnerDepartamento = viewInflater.findViewById(R.id.spinnerDepartamento);
                spinnerMunicipio = viewInflater.findViewById(R.id.spinnerMunicipio);
                spinnerAldea = viewInflater.findViewById(R.id.spinnerAldea);
                TextView textViewCancel = viewInflater.findViewById(R.id.ngButtonCancel);
                TextView textViewDownload = viewInflater.findViewById(R.id.ngButtonDownload);
                linearLayoutPorcentaje = viewInflater.findViewById(R.id.LinearLayoutPorcentage);

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

                builder.setTitle("Descargar-Quejas");
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
                      //  downloadSolicitudes(spinnerAldea.getSelectedItem().toString(), intCodUser);
                    }
                });

                dialog.show();
                getDepartamentos();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, departamentos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamento.setAdapter(arrayAdapter);
    }

    @Override
    public void getMunicipios(String depto) {
        this.ubicacionesPresenter.getMunicipios(depto);
    }

    @Override
    public void cargarMunicipios(ArrayList<String> municipios) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, municipios);
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
        ArrayAdapter<String>  arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listString);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAldea.setAdapter(arrayAdapter);
    }

    @Override
    public void getCaserios(String aldea) {

    }

    @Override
    public void cargarCaserios(List<Caserios> caserios) {

    }

    @Override
    public void DescargarQuejas(int usuario, String aldea) {

    }

    @Override
    public void SincronizarQuejas() {

    }
}
