package com.map_movil.map_movil.view.programados;


import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.adapter.AdaptadorProgramados;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
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

public class ProgramadosFragment extends Fragment implements PlanillaView, UbicacionView, SearchView.OnQueryTextListener {

    private View view;
    private View v;
    private MenuItem searchItem;
    private UbicacionesPresenter ubicacionesPresenter;
    private PlanillaPresenter pagosPresenter;
    private AppCompatSpinner DepartamentoSpiner;
    private AppCompatSpinner MunicipioSpiner;
    private AppCompatSpinner AldeaSpiner;
    private AppCompatSpinner PagosSpiner;
    private RadioButton rb_busc_id;
    private RadioButton rb_geo;
    private EditText tv_identidad;
    private ListView listplanillapagos;
    private ArrayList<PagosProgramados> listPagos = new ArrayList<PagosProgramados>();
    private AdaptadorProgramados adaptadorProgramados;
    private Context context;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private LinearLayout linearLayoutnodata;
    private LinearLayout lytBuscar;
    private RelativeLayout ryProgramados;
    private TextView tvBuscar;
    private TextView CantidadProgramados;
    private TextView tv_departamentos;
    private TextView tv_municipios;
    private TextView tv_aldeas;
    private HashMap<Integer, String> SpinnerMapPagos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String CodigoDepartamento="";
    private String CodigoMunicipio="";
    private String CodigoAldea="";
    private String CodigoPago="";
    private String Identidad_titular;
    private boolean a = true;
    private WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
    private SharedPreferences sharedPreferencesUsuario;

    public ProgramadosFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.view                        = inflater.inflate(R.layout.fragment_programados, container, false);
        this.context                     = this.view.getContext();
        this.ubicacionesPresenter        = new UbicacionPresenterImpl(this, view.getContext());
        this.pagosPresenter              = new PlanillaPresenterImpl(this , getContext());
        this.swipeRefreshLayout          = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshprogramados);
        this.SpinnerMapPagos             = new HashMap<Integer, String>();
        this.ryProgramados               = (RelativeLayout) view.findViewById(R.id.ry_cantidad);
        this.CantidadProgramados         = (TextView) view.findViewById(R.id.cantidad_hogares);
        this.sharedPreferencesUsuario    = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);

        this.relativeLayout = view.findViewById(R.id.relativeLayoutProgressBar);
        this.linearLayout = view.findViewById(R.id.linearLayoutdatos);
        this.linearLayoutnodata = view.findViewById(R.id.linearLayoutnodata);

        this.listplanillapagos = view.findViewById(R.id.listaexcluidos);
        this.adaptadorProgramados = new AdaptadorProgramados(context, listPagos);
        this.listplanillapagos.setAdapter(adaptadorProgramados);

        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading("search",null);
                if(a){
                    SolicitarDatosProgramados( (AldeaSpiner != null)?AldeaSpiner.getSelectedItem().toString().split("-")[0]:"",
                            (PagosSpiner != null)?SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()):"0" , sharedPreferencesUsuario.getInt("codigo",0)
                    );
                }else{
                    SolicitarDatosProgramadosPorID(Identidad_titular,
                        (PagosSpiner.getCount() != 0) ? SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()).toString() : "0" ,
                            sharedPreferencesUsuario.getInt("codigo",0));
                }
            }
        });

        loading("no_data","REALICE UNA BÚSQUEDA PARA VISUALIZAR INFORMACIÓN");
        BroadCastInternet.subscribeForMessageInternet(view.getContext(), view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        this.searchItem = menu.findItem(R.id.searchViewFind);
        MenuItem filterItem = menu.findItem(R.id.filter);

        this.searchItem.setEnabled(false);
        this.searchItem.setVisible(false);
        filterItem.setEnabled(true);
        filterItem.setVisible(true);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                MostarFiltros();
                return true;
            }
        });
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar por titular...");

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loading(String estado , String Texto){
        try {
            switch (estado){
                case "datos":
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    listplanillapagos.setVisibility(View.VISIBLE);
                    linearLayoutnodata.setVisibility(View.GONE);
                    this.swipeRefreshLayout.setVisibility(View.VISIBLE);
                    break;
                case "search":
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    break;
                case "no_data":
                    TextView texMensaje =view.findViewById(R.id.textViewMessageFind);
                    texMensaje.setText(Texto);
                    this.swipeRefreshLayout.setVisibility(View.GONE);
                    this.ryProgramados.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    listplanillapagos.setVisibility(View.GONE);
                    linearLayoutnodata.setVisibility(View.VISIBLE);
                    break;
            }
        }
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        findByTitular(newText);
        return false;
    }

    @Override
    public void SolicitarDatosProgramados(String strCodAldea, String strCodpago,  int Usuario) {
        this.pagosPresenter.getProgramados(strCodAldea , strCodpago , Usuario);
    }

    @Override
    public void MostarDatosProgramados(ArrayList<PagosProgramados> pagosProgramados) {
        listPagos = pagosProgramados;
        adaptadorProgramados.changeAdapater(listPagos);
        this.swipeRefreshLayout.setRefreshing(false);
        if(pagosProgramados.size()>0){
            loading("datos",null);
            this.searchItem.setEnabled(true);
            this.searchItem.setVisible(true);
            this.ryProgramados.setVisibility(View.VISIBLE);
            this.CantidadProgramados.setText(String.valueOf( pagosProgramados.size() ));
        }else{
            this.searchItem.setEnabled(false);
            this.searchItem.setVisible(false);
            loading("no_data" , "NO SE ENCONTRARON DATOS");
        }
    }

    @Override
    public void MostarExcluidos(ArrayList<PagosExcluido> listexcluidos) { }

    @Override
    public void SolicitarDatosProgramadosPorID(String strIdentidad, String strCodpago,  int Usuario) {
        this.pagosPresenter.getProgramado_By_ID(strIdentidad , strCodpago , Usuario);
    }

    private void findByTitular(String strNombre){
        ArrayList<PagosProgramados> arrayListPagosProgramados = new ArrayList<>();
        for(PagosProgramados item: listPagos){
            if(item.getStrnombre_titular().toString().toLowerCase().contains(strNombre.toLowerCase())){
                arrayListPagosProgramados.add(item);
            }
        }
        adaptadorProgramados.changeAdapater(arrayListPagosProgramados);
        this.CantidadProgramados.setText(String.valueOf(arrayListPagosProgramados.size()));
    }

    private void MostarFiltros(){

        final Display display = getActivity().getWindowManager().getDefaultDisplay();
        final int mwidth = display.getWidth();
        final int mheight = display.getHeight();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        v = LayoutInflater.from(this.context).inflate(R.layout.dialog_filters ,null);
        builder.setView(v);
        final AlertDialog dialog = builder.create();

        dialog.show();
      //  final WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
        LP.copyFrom(dialog.getWindow().getAttributes());
        LP.width  = (int)(( mwidth/2) * 1.8);
        LP.height = (int)(( mheight/2) * 1.4);//1.22

        this.DepartamentoSpiner = v.findViewById(R.id.departamento);
        this.MunicipioSpiner    = v.findViewById(R.id.municipio);
        this.AldeaSpiner        = v.findViewById(R.id.aldea);
        this.PagosSpiner        = v.findViewById(R.id.pago);
        this.tv_identidad       = v.findViewById(R.id.tv_prog_identidad);
        this.rb_busc_id         = v.findViewById(R.id.rb_busc_id);
        this.rb_geo             = v.findViewById(R.id.rb_geo);
        this.lytBuscar          = v.findViewById(R.id.lytfiltroBuscar);
        this.tvBuscar           = v.findViewById(R.id.tvBuscar);

        this.tv_identidad.setVisibility(View.GONE);
        this.rb_geo.setVisibility(View.VISIBLE);
        this.rb_busc_id.setVisibility(View.VISIBLE);
        this.lytBuscar.setVisibility(View.VISIBLE);
        this.tvBuscar.setVisibility(View.VISIBLE);
    //    this.rb_geo.setChecked(true);
    //    this.rb_busc_id.setChecked(false);


        TextView button = v.findViewById(R.id.buttonsearchex);
        TextView Cancel = v.findViewById(R.id.ButtonCancel);
        tv_departamentos = v.findViewById(R.id.tv_df_deptos);
        tv_municipios    = v.findViewById(R.id.tv_df_municipios);
        tv_aldeas        = v.findViewById(R.id.tv_df_aldeas);
        tv_identidad.setText(Identidad_titular);
        ShowLytAldeas(a);
        rb_busc_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    LP.copyFrom(dialog.getWindow().getAttributes());
                    LP.height = (int)(( mheight/2) * 0.8);
                    ShowLytAldeas(!b);
                }
            }
        });
        rb_geo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    LP.height = (int)(( mheight/2) * 1.4);
                    ShowLytAldeas(b);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.hide();
                if(rb_geo.isChecked()) {
                    Identidad_titular = "";
                    CodigoDepartamento = DepartamentoSpiner.getSelectedItem().toString().split("-")[0];
                    CodigoMunicipio = MunicipioSpiner.getSelectedItem().toString().split("-")[0];
                    CodigoAldea = AldeaSpiner.getSelectedItem().toString().split("-")[0];
                    CodigoPago = (PagosSpiner.getCount() != 0) ? SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()).toString() : "0";

                    loading("search", null);
                    SolicitarDatosProgramados(AldeaSpiner.getSelectedItem().toString().split("-")[0],
                            (PagosSpiner.getCount() != 0) ? SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()).toString() : "0" ,
                             sharedPreferencesUsuario.getInt("codigo",0)
                            );
                    a = true;
                }else if(rb_busc_id.isChecked()){
                    CodigoPago = (PagosSpiner.getCount() != 0) ? SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()).toString() : "0";
                    Identidad_titular = tv_identidad.getText().toString();

                    loading("search", null);
                    SolicitarDatosProgramadosPorID(Identidad_titular,
                            (PagosSpiner.getCount() != 0) ? SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()).toString() : "0" ,
                             sharedPreferencesUsuario.getInt("codigo",0)
                            );
                    a = false;
                }
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
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.getDepartamentos();
        this.getPagos();

       // dialog.getWindow().setAttributes(LP);
    }
    public void ShowLytAldeas(boolean c){
        rb_busc_id.setChecked(!c);
        rb_geo.setChecked(c);
        //a=c;
        if(c) {
            tv_identidad.setVisibility(View.GONE);
            MunicipioSpiner.setVisibility(View.VISIBLE);
            AldeaSpiner.setVisibility(View.VISIBLE);
            DepartamentoSpiner.setVisibility(View.VISIBLE);
            tv_departamentos.setVisibility(View.VISIBLE);
            tv_municipios.setVisibility(View.VISIBLE);
            tv_aldeas.setVisibility(View.VISIBLE);
        }else{
            tv_identidad.setVisibility(View.VISIBLE);
            MunicipioSpiner.setVisibility(View.GONE);
            AldeaSpiner.setVisibility(View.GONE);
            DepartamentoSpiner.setVisibility(View.GONE);
            tv_departamentos.setVisibility(View.GONE);
            tv_municipios.setVisibility(View.GONE);
            tv_aldeas.setVisibility(View.GONE);
        }
    }
}
