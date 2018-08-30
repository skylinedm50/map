package com.map_movil.map_movil.view.planilla;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.planilla.ApiAdapterPlanilla;
import com.map_movil.map_movil.api.planilla.ApiServicePlanilla;
import com.map_movil.map_movil.model.Aldeas;
import com.map_movil.map_movil.model.Caserios;
import com.map_movil.map_movil.model.Pagos;
import com.map_movil.map_movil.model.PagosExcluido;
import com.map_movil.map_movil.presenter.planilla.PlanillaPresenter;
import com.map_movil.map_movil.presenter.planilla.PlanillaPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExcluidoFragment extends Fragment implements UbicacionView, PlanillaView {
    private View view;
    private UbicacionesPresenter ubicacionesPresenter;
    private PlanillaPresenter planillaPresenter;
    private AppCompatSpinner DepartamentoSpiner;
    private AppCompatSpinner MunicipioSpiner;
    private AppCompatSpinner AldeaSpiner;
    private AppCompatSpinner PagosSpiner;
    private RadioButton RadGlobal;
    private RadioButton RadMdesc;
    private ListView listplanillaexcluidos;
    private ArrayList<PagosExcluido> listexcluidos= new ArrayList<>();
    private AdaptadorExcluidos adaptadorExcluidos;
    private Context context;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private LinearLayout linearLayoutnodata;

    ArrayList<PagosExcluido> planillaExcluidos;

    private ApiAdapterPlanilla adapterPlanilla;
    private ApiServicePlanilla servicePlanilla;

    private HashMap<Integer , String > SpinnerMapDepto;
    private HashMap<Integer , String > SpinnerMapMuni;
    private HashMap<Integer , String > SpinnerMapAldea;
    private HashMap<Integer , String > SpinnerMapPagos;


    public ExcluidoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_excluido, container, false);
        try {
            this.ubicacionesPresenter = new UbicacionPresenterImpl(this, view.getContext());
            this.planillaPresenter = new PlanillaPresenterImpl(this);
            this.SpinnerMapDepto = new HashMap<Integer, String>();
            this.SpinnerMapMuni = new HashMap<Integer, String>();
            this.SpinnerMapAldea = new HashMap<Integer, String>();
            this.SpinnerMapPagos = new HashMap<Integer, String>();

            RadGlobal = (RadioButton) view.findViewById(R.id.radio_global);
            RadMdesc = (RadioButton) view.findViewById(R.id.radio_m);
            relativeLayout =(RelativeLayout) view.findViewById(R.id.relativeLayoutProgressBar);
            linearLayout = view.findViewById(R.id.linearLayoutdatos);
            linearLayoutnodata=view.findViewById(R.id.linearLayoutnodata);

            DepartamentoSpiner = (AppCompatSpinner) view.findViewById(R.id.departamento);
            MunicipioSpiner = (AppCompatSpinner) view.findViewById(R.id.municipio);
            AldeaSpiner = (AppCompatSpinner) view.findViewById(R.id.aldea);
            PagosSpiner = (AppCompatSpinner) view.findViewById(R.id.pago);

            this.getDepartamentos();
            this.getPagos();

            final Button button = view.findViewById(R.id.buttonsearchex);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loading("search");
                    if (RadGlobal.isChecked()){
                        findexcluidos(SpinnerMapAldea.get(AldeaSpiner.getSelectedItemPosition()),SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()),"Global");
                    }
                    else{
                        findexcluidos(SpinnerMapAldea.get(AldeaSpiner.getSelectedItemPosition()),SpinnerMapPagos.get(PagosSpiner.getSelectedItemPosition()),"Mancomunidad");
                    }
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

            return view;
        }catch (Throwable e) {
            e.printStackTrace();
        }
        return view;
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
        this.SpinnerMapAldea.clear();

        for(int x = 0; x < aldeas.size(); x++){
            spinner.add(aldeas.get(x).getDesc_aldea());
            this.SpinnerMapAldea.put(x ,aldeas.get(x).getCod_aldea());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getContext(), android.R.layout.simple_dropdown_item_1line, spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.AldeaSpiner.setAdapter(adapter);
    }

    @Override
    public void getCaserios(String aldea) {

    }

    @Override
    public void cargarCaserios(List<Caserios> caserios) {

    }

    @Override
    public void getPagos() {
        this.planillaPresenter.getPagos();
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


    public class AdaptadorExcluidos extends BaseAdapter {

        private Context context;
        private ArrayList<PagosExcluido> listExcluidos;

        public AdaptadorExcluidos(Context context, ArrayList<PagosExcluido> listExcluidos) {
            this.context = context;
            this.listExcluidos = listExcluidos;
        }

        @Override
        public int getCount() {
            return listExcluidos.size();
        }

        @Override
        public Object getItem(int position) {
            return listExcluidos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            PagosExcluido Item = (PagosExcluido) getItem(position);

            convertView = LayoutInflater.from(context).inflate(R.layout.item_planilla_excluido,null);
            TextView textViewNombre =(TextView) convertView.findViewById(R.id.Nombre_Persona) ;
            TextView textViewCodhoga=(TextView) convertView.findViewById(R.id.codhogar) ;
            TextView textViewcaserio = (TextView) convertView.findViewById(R.id.caserio) ;
            TextView textViewrazon =(TextView) convertView.findViewById(R.id.razon) ;

            textViewNombre.setText(Item.getStrnombre_titular());
            textViewCodhoga.setText("CÓDIGO HOGAR : " + Item.getStrcodigo_hogar());
            textViewcaserio.setText("CASERIO : " + Item.getStrdesc_caserio());
            textViewrazon.setText("RAZÓN DE LA EXCLUSIÓN : " + Item.getStrrazon_exclusion());

            return convertView;
        }

        public void changeAdapater(ArrayList<PagosExcluido> listExcluidos){
            this.listExcluidos = listExcluidos;

        }
    }

    private void loading(String estado){
        try {
            switch (estado){
                case "datos":
                    relativeLayout.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    listplanillaexcluidos.setVisibility(View.VISIBLE);
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
                    listplanillaexcluidos.setVisibility(View.GONE);
                    linearLayoutnodata.setVisibility(View.VISIBLE);

            }}
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void findexcluidos(String strCodAldea, String strCodpago,String tipoGM){
        adapterPlanilla=new ApiAdapterPlanilla();
        servicePlanilla=adapterPlanilla.getClientService();
        planillaExcluidos=new ArrayList<>();
        context=getContext();


        final List<Map<String, String>> data = new ArrayList<>();
        if(tipoGM.equals("Global")){
            Call<ArrayList<PagosExcluido>> call = servicePlanilla.getPlanillaExcluidoInfo(strCodAldea,strCodpago);
            call.enqueue(new Callback<ArrayList<PagosExcluido>>() {
                public void onResponse(Call<ArrayList<PagosExcluido>> call, Response<ArrayList<PagosExcluido>> response) {
                    if(response.body() != null && response.body().size()>0){
                        listexcluidos= response.body();
                        listplanillaexcluidos = (ListView) view.findViewById(R.id.listaexcluidos);
                        adaptadorExcluidos=new AdaptadorExcluidos(context,listexcluidos);
                        listplanillaexcluidos.setAdapter(adaptadorExcluidos);
                        loading("datos");
                    }
                    else{
                        //listplanillaexcluidos.setAdapter(null);
                        loading("no_data");
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<PagosExcluido>> call, Throwable t) {
                    /* showProgressBar(false);*/
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Call<ArrayList<PagosExcluido>> call = servicePlanilla.getPlanillaExcluidomancInfo(strCodAldea,strCodpago);
            call.enqueue(new Callback<ArrayList<PagosExcluido>>() {
                public void onResponse(Call<ArrayList<PagosExcluido>> call, Response<ArrayList<PagosExcluido>> response) {
                    if(response.body() != null && response.body().size()>0){
                        listexcluidos= response.body();
                        listplanillaexcluidos = (ListView) view.findViewById(R.id.listaexcluidos);
                        adaptadorExcluidos=new AdaptadorExcluidos(context,listexcluidos);
                        listplanillaexcluidos.setAdapter(adaptadorExcluidos);
                        loading("datos");
                    }
                    else{
                        listplanillaexcluidos.setAdapter(null);
                        loading("no_data");
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<PagosExcluido>> call, Throwable t) {
                    /* showProgressBar(false);*/
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
