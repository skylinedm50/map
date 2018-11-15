package com.map_movil.map_movil.view.programados;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdaptadorProgramados;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosProgramados;
import com.map_movil.map_movil.presenter.planilla.PlanillaPresenter;
import com.map_movil.map_movil.presenter.planilla.PlanillaPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.planilla.PlanillaView;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramadosFragment extends Fragment implements PlanillaView, UbicacionView, SearchView.OnQueryTextListener {

    private View view;
    private UbicacionesPresenter ubicacionesPresenter;
    private PlanillaPresenter pagosPresenter;
    private AppCompatSpinner DepartamentoSpiner;
    private AppCompatSpinner MunicipioSpiner;
    private AppCompatSpinner AldeaSpiner;
    private AppCompatSpinner PagosSpiner;
    private ListView listplanillapagos;
    private ArrayList<PagosProgramados> listPagos = new ArrayList<PagosProgramados>();
    private AdaptadorProgramados adaptadorProgramados;
    private Context context;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private LinearLayout linearLayoutnodata;

    private HashMap<Integer, String> SpinnerMapDepto;
    private HashMap<Integer, String> SpinnerMapMuni;
    private HashMap<Integer, String> SpinnerMapAldea;
    private HashMap<Integer, String> SpinnerMapPagos;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ProgramadosFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.view                 = inflater.inflate(R.layout.fragment_programados, container, false);
        this.context              = this.view.getContext();
        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());
        this.pagosPresenter       = new PlanillaPresenterImpl(this , getContext());
        this.swipeRefreshLayout   = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshprogramados);
        this.SpinnerMapDepto      = new HashMap<Integer, String>();
        this.SpinnerMapMuni       = new HashMap<Integer, String>();
        this.SpinnerMapAldea      = new HashMap<Integer, String>();
        this.SpinnerMapPagos      = new HashMap<Integer, String>();

        relativeLayout = view.findViewById(R.id.relativeLayoutProgressBar);
        linearLayout = view.findViewById(R.id.linearLayoutdatos);
        linearLayoutnodata = view.findViewById(R.id.linearLayoutnodata);
        DepartamentoSpiner = view.findViewById(R.id.departamento);
        MunicipioSpiner = view.findViewById(R.id.municipio);
        AldeaSpiner = view.findViewById(R.id.aldea);
        PagosSpiner = view.findViewById(R.id.pago);

        listplanillapagos = view.findViewById(R.id.listaexcluidos);
        adaptadorProgramados = new AdaptadorProgramados(context, listPagos);
        listplanillapagos.setAdapter(adaptadorProgramados);

        final Button button = view.findViewById(R.id.buttonsearchex);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading("search");
                SolicitarDatosProgramados( AldeaSpiner.getSelectedItem().toString().split("-")[0] ,
                                           SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()) );
            }
        });

        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading("search");
                SolicitarDatosProgramados(  AldeaSpiner.getSelectedItem().toString().split("-")[0],
                        SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()) );
            }
        });

        PagosSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listPagos = new ArrayList<>();
                adaptadorProgramados.changeAdapater(listPagos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        DepartamentoSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMunicipios(adapterView.getItemAtPosition(i).toString());
                listPagos = new ArrayList<>();
                adaptadorProgramados.changeAdapater(listPagos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        MunicipioSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAldeas(adapterView.getItemAtPosition(i).toString());
                listPagos = new ArrayList<>();
                adaptadorProgramados.changeAdapater(listPagos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        AldeaSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listPagos = new ArrayList<>();
                adaptadorProgramados.changeAdapater(listPagos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.getDepartamentos();
        this.getPagos();

        BroadCastInternet.subscribeForMessageInternet(view.getContext(), view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar por titular...");

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loading(String estado){
        try {
            switch (estado){
                case "datos":
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    listplanillapagos.setVisibility(View.VISIBLE);
                    linearLayoutnodata.setVisibility(View.GONE);
                    break;
                case "search":
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    break;
                case "no_data":
                    TextView texMensaje=view.findViewById(R.id.textViewMessageFind);
                    texMensaje.setText("NO SE ENCONTRARON DATOS");
                    linearLayout.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    listplanillapagos.setVisibility(View.GONE);
                    linearLayoutnodata.setVisibility(View.VISIBLE);

            }}
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPagos() {
        this.pagosPresenter.getPagos();
    }

    @Override
    public void cargarPagos(List<Pagos> pagos) {
        List<String> spinner =  new ArrayList<String>();
        this.SpinnerMapPagos.clear();

        for(int x = 0; x < pagos.size(); x++){
            spinner.add(pagos.get(x).getPag_nombre());
            this.SpinnerMapPagos.put(x ,pagos.get(x).getPag_codigo());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.PagosSpiner.setAdapter(adapter);
    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.DepartamentoSpiner.setAdapter(adapter);
    }

    @Override
    public void getMunicipios(String depto) {
        this.ubicacionesPresenter.getMunicipios(depto);
    }

    @Override
    public void cargarMunicipios(ArrayList<String> municipios) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, municipios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.MunicipioSpiner.setAdapter(adapter);
    }

    @Override
    public void getAldeas(String muni) {
        this.ubicacionesPresenter.getAldeas(muni);
    }

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {
        List<String> spinner =  new ArrayList<String>();

        for(int x = 0; x < aldeas.size(); x++){
            spinner.add(aldeas.get(x).getCod_aldea()+"-"+aldeas.get(x).getDesc_aldea());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.AldeaSpiner.setAdapter(adapter);
    }

    @Override
    public void getCaserios(String aldea) { }

    @Override
    public void cargarCaserios(List<Caserios> caserios) { }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        findByTitular(newText);
        return false;
    }

    @Override
    public void SolicitarDatosProgramados(String strCodAldea, String strCodpago) {
        this.pagosPresenter.getProgramados(strCodAldea , strCodpago);
    }

    @Override
    public void MostarDatosProgramados(ArrayList<PagosProgramados> pagosProgramados) {
        listPagos = pagosProgramados;
        adaptadorProgramados.changeAdapater(listPagos);
        this.swipeRefreshLayout.setRefreshing(false);
        if(pagosProgramados.size()>0){
            loading("datos");
        }else{
            loading("no_data");
        }
    }

    private void findByTitular(String strNombre){
        ArrayList<PagosProgramados> arrayListPagosProgramados = new ArrayList<>();
        for(PagosProgramados item: listPagos){
            if(item.getStrnombre_titular().toString().toLowerCase().contains(strNombre.toLowerCase())){
                arrayListPagosProgramados.add(item);
            }
        }

        adaptadorProgramados.changeAdapater(arrayListPagosProgramados);
    }
}
