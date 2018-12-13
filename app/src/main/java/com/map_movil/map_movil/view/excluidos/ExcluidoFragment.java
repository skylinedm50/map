package com.map_movil.map_movil.view.excluidos;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdaptadorExcluidos;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosExcluido;
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

public class ExcluidoFragment extends Fragment implements UbicacionView, PlanillaView, SearchView.OnQueryTextListener {

    private View view;
    private Context context;
    private UbicacionesPresenter ubicacionesPresenter;
    private PlanillaPresenter planillaPresenter;

    private AppCompatSpinner DepartamentoSpiner;
    private AppCompatSpinner MunicipioSpiner;
    private AppCompatSpinner AldeaSpiner;
    private AppCompatSpinner PagosSpiner;

    private CheckBox chk_button;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView CantidadProgramados;
    private MenuItem searchItem;

    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private LinearLayout linearLayoutnodata;
    private RelativeLayout ryProgramados;

    private ListView listplanillaexcluidos;
    private ArrayList<PagosExcluido> listexcluidos = new ArrayList<>();
    private AdaptadorExcluidos adaptadorExcluidos;
    private HashMap<Integer , String > SpinnerMapPagos;

    private String CodigoDepartamento="";
    private String CodigoMunicipio="";
    private String CodigoAldea="";
    private String CodigoPago="";
    private Boolean Mancomunidades = false;

    public ExcluidoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_excluido, container, false);

        this.ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());
        this.planillaPresenter    = new PlanillaPresenterImpl(this , getContext());
        this.SpinnerMapPagos      = new HashMap<Integer, String>();
        this.swipeRefreshLayout   = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshexcluidos);
        this.ryProgramados        = (RelativeLayout) view.findViewById(R.id.ry_cantidadE);
        this.CantidadProgramados  = (TextView) view.findViewById(R.id.cantidad_hogaresE);

        relativeLayout = view.findViewById(R.id.relativeLayoutProgressBar);
        linearLayout = view.findViewById(R.id.linearLayoutdatos);
        linearLayoutnodata = view.findViewById(R.id.linearLayoutnodata);

        DepartamentoSpiner = view.findViewById(R.id.departamento);
        MunicipioSpiner = view.findViewById(R.id.municipio);
        AldeaSpiner = view.findViewById(R.id.aldea);
        PagosSpiner = view.findViewById(R.id.pago);

        this.context = view.getContext();
        listplanillaexcluidos = view.findViewById(R.id.listaexcluidos);
        adaptadorExcluidos = new AdaptadorExcluidos(context, listexcluidos);
        listplanillaexcluidos.setAdapter(adaptadorExcluidos);

        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading("search",null);
                planillaPresenter.getExcluidos(AldeaSpiner.getSelectedItem().toString().split("-")[0],
                        SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()),(chk_button.isChecked())?"":"Global");
            }
        });
        loading("no_data","REALICE UNA BÚSQUEDA PARA VISUALIZAR INFORMACIÓN");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem filterItem = menu.findItem(R.id.filter);

        this.searchItem = menu.findItem(R.id.searchViewFind);
        this.searchItem.setEnabled(false);
        this.searchItem.setVisible(false);

        filterItem.setEnabled(true);
        filterItem.setVisible(true);
        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                MostarFiltros();
                return true;
            }
        });

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar por titular...");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void getDepartamentos() {
        this.ubicacionesPresenter.getDepartamentos();
    }

    @Override
    public void cargarDepartamentos(ArrayList<String> departamentos) {
        int index = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.DepartamentoSpiner.setAdapter(adapter);

        if(this.CodigoDepartamento != ""){
            for(int i = 0 ; i < adapter.getCount(); i++){
                if(index == 0){
                    index = (CodigoDepartamento.equals( adapter.getItem(i).substring(0,2) ))?i:0;
                }else{
                    break;
                }
            }
            this.DepartamentoSpiner.setSelection(index);
        }
    }

    @Override
    public void getMunicipios(String depto) {
        this.ubicacionesPresenter.getMunicipios(depto);
    }

    @Override
    public void cargarMunicipios(ArrayList<String> municipios) {
        int index = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, municipios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.MunicipioSpiner.setAdapter(adapter);

        if(this.CodigoMunicipio != ""){
            for(int i = 0; i < adapter.getCount(); i++){
                if(index == 0){
                    index = (this.CodigoMunicipio.equals( adapter.getItem(i).substring(0,4) ))?i:0;
                }else{
                    break;
                }
            }
            this.MunicipioSpiner.setSelection(index);
        }
    }

    @Override
    public void getAldeas(String muni) {
        this.ubicacionesPresenter.getAldeas(muni);
    }

    @Override
    public void cargarAldeas(List<Aldeas> aldeas) {
        int index = 0;
        List<String> spinner =  new ArrayList<String>();

        for(int x = 0; x < aldeas.size(); x++){
            if(this.CodigoAldea != "" && index == 0){
                index = (this.CodigoAldea.equals(aldeas.get(x).getCod_aldea()))?x:0;
            }
            spinner.add(aldeas.get(x).getCod_aldea()+"-"+aldeas.get(x).getDesc_aldea());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_dropdown_item_1line, spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.AldeaSpiner.setAdapter(adapter);
        this.AldeaSpiner.setSelection(index);
    }

    @Override
    public void getCaserios(String aldea) { }

    @Override
    public void cargarCaserios(List<Caserios> caserios) { }

    @Override
    public void getPagos() {
        this.planillaPresenter.getPagos();
    }

    @Override
    public void cargarPagos(List<Pagos> pagos) {
        int index = 0;
        List<String> spinner =  new ArrayList<String>();

        for(int x = 0; x < pagos.size(); x++){
            if(this.CodigoPago != "" && index == 0){
                index = (this.CodigoPago.equals(pagos.get(x).getPag_codigo()))?x:0;
            }
            spinner.add(pagos.get(x).getPag_nombre());
            this.SpinnerMapPagos.put(x ,pagos.get(x).getPag_codigo());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.PagosSpiner.setAdapter(adapter);
        this.PagosSpiner.setSelection(index);
    }

    @Override
    public void SolicitarDatosProgramados(String strCodAldea, String strCodpago) { }

    @Override
    public void MostarDatosProgramados(ArrayList<PagosProgramados> pagosProgramados) { }

    @Override
    public void MostarExcluidos(ArrayList<PagosExcluido> excluidos) {
        this.listexcluidos = excluidos;
        adaptadorExcluidos.changeAdapater( this.listexcluidos );
        this.swipeRefreshLayout.setRefreshing(false);
        if(excluidos.size()>0){
            loading("datos",null);
            this.searchItem.setEnabled(true);
            this.searchItem.setVisible(true);
            this.ryProgramados.setVisibility(View.VISIBLE);
            this.CantidadProgramados.setText(String.valueOf( listexcluidos.size() ));
        }else{
            loading("no_data" , "NO SE ENCONTRARON DATOS");
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) { return false; }

    @Override
    public boolean onQueryTextChange(String newText) {
        findByTitular(newText);
        return false;
    }

    private void MostarFiltros(){
        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        int mwidth = display.getWidth();
        int mheight = display.getHeight();

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        View v = LayoutInflater.from(this.context).inflate(R.layout.dialog_filters ,null);
        builder.setView(v);
        final AlertDialog dialog = builder.create();

        dialog.show();
        WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
        LP.copyFrom(dialog.getWindow().getAttributes());
        LP.width  = (int)(( mwidth/2) * 1.8);
        LP.height = (int)(( mheight/2) * 1.34);

        this.DepartamentoSpiner = v.findViewById(R.id.departamento);
        this.MunicipioSpiner    = v.findViewById(R.id.municipio);
        this.AldeaSpiner        = v.findViewById(R.id.aldea);
        this.PagosSpiner        = v.findViewById(R.id.pago);
        this.chk_button         = v.findViewById(R.id.chk_mancomunidades);

        this.chk_button.setVisibility(View.VISIBLE);
        this.chk_button.setChecked(Mancomunidades);

        TextView button = v.findViewById(R.id.buttonsearchex);
        TextView Cancel = v.findViewById(R.id.ButtonCancel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.hide();
                CodigoDepartamento  = DepartamentoSpiner.getSelectedItem().toString().split("-")[0];
                CodigoMunicipio     = MunicipioSpiner.getSelectedItem().toString().split("-")[0];
                CodigoAldea         = AldeaSpiner.getSelectedItem().toString().split("-")[0];
                CodigoPago          = (PagosSpiner.getCount() != 0)?SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()).toString():"0";
                Mancomunidades      = chk_button.isChecked();

                loading("search",null);
                planillaPresenter.getExcluidos(AldeaSpiner.getSelectedItem().toString().split("-")[0],
                              SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()),(chk_button.isChecked())?"":"Global");
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        DepartamentoSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getMunicipios(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        MunicipioSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAldeas(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        this.getDepartamentos();
        this.getPagos();

        dialog.getWindow().setAttributes(LP);
    }

    private void findByTitular(String strNombre){
        ArrayList<PagosExcluido> arrayListPagosExcluidos = new ArrayList<>();
        for(PagosExcluido item: listexcluidos){
            if(item.getStrnombre_titular().toString().toLowerCase().contains(strNombre.toLowerCase())){
                arrayListPagosExcluidos.add(item);
            }
        }
        adaptadorExcluidos.changeAdapater(arrayListPagosExcluidos);
        this.CantidadProgramados.setText(String.valueOf(arrayListPagosExcluidos.size()));
    }

    private void loading(String estado , String Texto){
        try {
            switch (estado){
                case "datos":
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    listplanillaexcluidos.setVisibility(View.VISIBLE);
                    linearLayoutnodata.setVisibility(View.GONE);
                    this.swipeRefreshLayout.setVisibility(View.VISIBLE);
                    break;
                case "search":
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    break;
                case "no_data":
                    TextView texMensaje=view.findViewById(R.id.textViewMessageFind);
                    texMensaje.setText(Texto);
                    this.swipeRefreshLayout.setVisibility(View.GONE);
                    this.ryProgramados.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    listplanillaexcluidos.setVisibility(View.GONE);
                    linearLayoutnodata.setVisibility(View.VISIBLE);

            }}
        catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
