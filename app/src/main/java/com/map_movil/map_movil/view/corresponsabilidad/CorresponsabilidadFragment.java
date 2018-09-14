package com.map_movil.map_movil.view.corresponsabilidad;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.corresponsabilidad.ApiAdapterCorresponsabilidad;
import com.map_movil.map_movil.api.corresponsabilidad.ApiServiceCorresponsabilidad;
import com.map_movil.map_movil.model.Corresponsabilidades;
import com.map_movil.map_movil.model.CorresponsabilidadesClearByMenor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CorresponsabilidadFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private View view;
    private ApiAdapterCorresponsabilidad adapterCorresponsabilidad;
    private ApiServiceCorresponsabilidad serviceCorresponsabilidad;

    private ArrayList<Corresponsabilidades> listCorresponsabilidad = new ArrayList<>();
    private AdaptadorCorresponsabilidad adaptadorcorresponsabilidad;
    private LinearLayout linearLayoutInicial;
    private LinearLayout linearLayouttitular;
    private TextView textViewFindinfo;
    private TextView textViewTitular;
    private TextView textViewHogar;
    private String NombreTitular;
    private String CodHogar;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    ArrayList <CorresponsabilidadesClearByMenor> listShowCorresponsabilidad;
    RecyclerView recyclerCorresponsabilidad;

    public CorresponsabilidadFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_corresponsabilidad, container, false);
        linearLayoutInicial=view.findViewById(R.id.linearLayoutinfosearch);
        linearLayouttitular=view.findViewById(R.id.llinfhogar);
        textViewFindinfo=view.findViewById(R.id.textViewMessageFind);
        textViewTitular = view.findViewById(R.id.textViewtitular);
        textViewHogar=view.findViewById(R.id.textViewhogar);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_option, menu);
        MenuItem searchItem = menu.findItem(R.id.searchViewFind);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Buscar...");
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        super.onCreateOptionsMenu(menu, inflater);
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
    public boolean onQueryTextSubmit(String query) {
        findCorrByTitular(query.toString());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void findDataShowMessage(Boolean find){
        String strTextMessage = "Ingrese el numero de identidad para buscar las corresponsabilidades asociadas.";

        if(find == true){
            textViewTitular.setText("TITULAR: "+ NombreTitular);
            textViewHogar.setText("HOGAR: "+CodHogar);
            linearLayoutInicial.setVisibility(View.GONE);
            recyclerCorresponsabilidad.setVisibility(View.VISIBLE);
            linearLayouttitular.setVisibility(View.VISIBLE);
        }else{
            strTextMessage = "No se encontraron datos";
            recyclerCorresponsabilidad.setVisibility(View.GONE);
            linearLayouttitular.setVisibility(View.GONE);
            linearLayoutInicial.setVisibility(View.VISIBLE);
            textViewFindinfo.setText(strTextMessage);
            textViewFindinfo.setGravity(Gravity.CENTER);
        }

    }

    private void findCorrByTitular(String strCodIdentidad){

        adapterCorresponsabilidad = new ApiAdapterCorresponsabilidad();
        serviceCorresponsabilidad = adapterCorresponsabilidad.getClientService();
        listShowCorresponsabilidad = new ArrayList<>();
        recyclerCorresponsabilidad = view.findViewById(R.id.rv_personas_validar);
        recyclerCorresponsabilidad.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorcorresponsabilidad = new AdaptadorCorresponsabilidad(listShowCorresponsabilidad);


        final List<Map<String, String>> data = new ArrayList<>();
        Call<ArrayList<Corresponsabilidades>> call = serviceCorresponsabilidad.getCorresponsabilidadInfo(strCodIdentidad);

        call.enqueue(new Callback<ArrayList<Corresponsabilidades>>() {
            @Override
            public void onResponse(Call<ArrayList<Corresponsabilidades>> call, Response<ArrayList<Corresponsabilidades>> response) {
                if(response.body() != null && response.body().size()>0){
                    listCorresponsabilidad = response.body();
                    List<String> persona = new ArrayList<>();


                    //eliminar per persona repetido
                    for(Corresponsabilidades item: listCorresponsabilidad){
                        if (persona.isEmpty()){
                            persona.add(item.getPer_persona());
                            NombreTitular=item.getStrTitular();
                            CodHogar=item.getSrtHogar();

                        }else{
                            if(!persona.contains(item.getPer_persona())){
                                persona.add(item.getPer_persona());
                            }
                        }
                    }

                    //crear nuevo array sin data repetida
                    int  contador;
                    for(int x=0;x<persona.size();x++) {
                        contador=0;
                        Map<String, String> map = new HashMap<String, String>();
                        for(Corresponsabilidades item: listCorresponsabilidad){
                            String perPersona = persona.get(x);
                            if(perPersona.equals(item.getPer_persona())){
                                String parcial = new String(item.getStrCorresponsabilidad().toString());
                                if (parcial.equals("ASISTENCIA I (PARCIAL I y PARCIAL II)")){
                                    if (!map.containsKey("ASISTENCIA I (PARCIAL I y PARCIAL II)")){
                                        if (!map.containsKey("YearRegistro")){
                                            map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                            map.put("EstadoParcialI",item.getStrEstadoCorr());
                                            map.put("YearRegistro", item.getStrYear());
                                        }
                                        else if (map.get("YearRegistro").equals(item.getStrYear()) ){
                                            map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                            map.put("EstadoParcialIB",item.getStrEstadoCorr());
                                        }
                                        else{
                                            map.put("ASISTENCIA IB (PARCIAL I y PARCIAL II)", item.getStrDiasfaltantes());
                                            map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                                            map.put("YearRegistroB", item.getStrYear());
                                        }
                                    }
                                    if (!map.containsKey("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)")){
                                        if (map.get("YearRegistro").equals(item.getStrYear()) ){
                                            map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                            map.put("EstadoParcialI",item.getStrEstadoCorr());
                                        }
                                        else{
                                            map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", item.getStrDiasfaltantes());
                                            map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                                            map.put("YearRegistroB", item.getStrYear());
                                        }
                                        /*
                                        map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", item.getStrDiasfaltantes());
                                        map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                                        map.put("YearRegistroB", item.getStrYear());*/
                                    }
                                }

                                if (parcial.equals("ASISTENCIA II (PARCIAL III y PARCIAL IV)")){
                                    if (!map.containsKey("ASISTENCIA II (PARCIAL III y PARCIAL IV)")){
                                        if (!map.containsKey("YearRegistro")){
                                            map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                            map.put("EstadoParcialII",item.getStrEstadoCorr());
                                            map.put("YearRegistro", item.getStrYear());
                                        }
                                        else if (map.get("YearRegistro").equals(item.getStrYear()) ){
                                            map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                            map.put("EstadoParcialII",item.getStrEstadoCorr());
                                        }
                                        else{
                                            map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", item.getStrDiasfaltantes());
                                            map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                                        }
                                    }
                                    if (!map.containsKey("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)")){
                                        if (map.get("YearRegistro").equals(item.getStrYear()) ){
                                            map.put(item.getStrCorresponsabilidad(), item.getStrDiasfaltantes());
                                            map.put("EstadoParcialII",item.getStrEstadoCorr());
                                        }
                                        else{
                                            map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", item.getStrDiasfaltantes());
                                            map.put("EstadoParcialIIB",item.getStrEstadoCorr());
                                            map.put("YearRegistroB", item.getStrYear());
                                        }
                                    }
                                }

                                String Visitamedica = item.getStrVisita();
                                if(!Visitamedica.equals("NO APLICA")){
                                    map.put("VisitaIngreso",item.getStrIngresoVIsita());
                                    map.put("Visitafecha"+contador,item.getStrVisita());
                                    map.put("VisitaCod"+contador,item.getStrCodVisita());
                                    contador++;
                                }
                                if(!item.getStrMatricula().equals("NO APLICA")){
                                    map.put("matricula", item.getStrMatricula());
                                }
                                map.put("PerPersona", item.getPer_persona().toString());
                                map.put("nombre", item.getStrNombre());
                                map.put("titular", item.getStrTitular());
                                map.put("identidad", item.getStrIdentidad());
                                map.put("edad", item.getStrEdad().toString());
                                map.put("sexo", item.getStrSexo());


                            }}
                        if (!map.containsKey("matricula")){
                            map.put("matricula", "NO APLICA");
                        }
                        if (!map.containsKey("VisitaIngreso")){
                            map.put("VisitaIngreso", "N/D");
                        }
                        if (!map.containsKey("ASISTENCIA I (PARCIAL I y PARCIAL II)")){
                            map.put("ASISTENCIA I (PARCIAL I y PARCIAL II)", "ND");
                            map.put("EstadoParcialI","N/D");
                        }
                        if (!map.containsKey("ASISTENCIA II (PARCIAL III y PARCIAL IV)")){
                            map.put("ASISTENCIA II (PARCIAL III y PARCIAL IV)", "ND");
                            map.put("EstadoParcialII","N/D");
                        }

                        if (!map.containsKey("ASISTENCIA IB (PARCIAL I y PARCIAL II)")){
                            map.put("ASISTENCIA IB (PARCIAL I y PARCIAL II)", "ND");
                            map.put("EstadoParcialIB","N/D");
                        }
                        if (!map.containsKey("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)")){
                            map.put("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)", "ND");
                            map.put("EstadoParcialIIB","N/D");
                        }
                        if (!map.containsKey("YearRegistro")){
                            map.put("YearRegistro","N/D");
                        }
                        if (!map.containsKey("YearRegistroB")){
                            map.put("YearRegistroB","N/D");
                        }
                        for(int i=0;i<6;i++){
                            if (!map.containsKey("VisitaCod"+i)){
                                map.put("Visitafecha"+i,"N/D");
                                map.put("VisitaCod"+i,"N/D");
                            }
                        }
                        data.add(x, map);
                    }
                    recyclerCorresponsabilidad = view.findViewById(R.id.rv_personas_validar);
                    recyclerCorresponsabilidad.setLayoutManager(linearLayoutManager);
                    adaptadorcorresponsabilidad = new AdaptadorCorresponsabilidad(listShowCorresponsabilidad);
                    recyclerCorresponsabilidad.setAdapter(adaptadorcorresponsabilidad);
                    recyclerCorresponsabilidad.setHasFixedSize(true);
                    findDataShowMessage(true);
                }
                else{
                    findDataShowMessage(false);
                }

                for (Map<String, String> entry : data) {
                    listShowCorresponsabilidad.add(new CorresponsabilidadesClearByMenor(entry.get("PerPersona"),entry.get("nombre"),entry.get("identidad"),entry.get("edad"),entry.get("sexo"),entry.get("matricula"),entry.get("YearRegistro"),entry.get("VisitaIngreso"),entry.get("ASISTENCIA I (PARCIAL I y PARCIAL II)"),entry.get("ASISTENCIA II (PARCIAL III y PARCIAL IV)"), entry.get("EstadoParcialI"), entry.get("EstadoParcialII"), entry.get("Visitafecha0"), entry.get("VisitaCod0"), entry.get("Visitafecha1"), entry.get("VisitaCod1"), entry.get("Visitafecha2"), entry.get("VisitaCod2"), entry.get("Visitafecha3"), entry.get("VisitaCod3"), entry.get("Visitafecha4"), entry.get("VisitaCod4"), entry.get("Visitafecha5"), entry.get("VisitaCod5"), entry.get("ASISTENCIA IB (PARCIAL I y PARCIAL II)"), entry.get("ASISTENCIA IIB (PARCIAL III y PARCIAL IV)"), entry.get("EstadoParcialIB"), entry.get("EstadoParcialIIB"), entry.get("YearRegistroB")));
                }

            }
            @Override
            public void onFailure(Call<ArrayList<Corresponsabilidades>> call, Throwable t) {
                /* showProgressBar(false);*/
                findDataShowMessage(false);

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class AdaptadorCorresponsabilidad extends RecyclerView.Adapter<AdaptadorCorresponsabilidad.ViewHolderCorresponsabilidad> {

        ArrayList<CorresponsabilidadesClearByMenor> ListaCorresponsabilidad;

        public AdaptadorCorresponsabilidad(ArrayList<CorresponsabilidadesClearByMenor> listaCorresponsabilidad) {
            ListaCorresponsabilidad = listaCorresponsabilidad;
        }

        @NonNull
        @Override
        public ViewHolderCorresponsabilidad onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_corresponsabilidad_menor,parent, false);
            return new ViewHolderCorresponsabilidad(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderCorresponsabilidad holder, int position) {
            try {
                holder.Nombre_Persona.setText( ListaCorresponsabilidad.get(position).getNombre());
                holder.identidad.setText("IDENTIDAD: " +ListaCorresponsabilidad.get(position).getIdentidad());
                holder.per_edad.setText("EDAD: " +ListaCorresponsabilidad.get(position).getEdad());
                holder.sexo.setText("SEXO: " +ListaCorresponsabilidad.get(position).getSexo());
                holder.estadoMatricula.setText("MATRICULA: " +ListaCorresponsabilidad.get(position).getMatricula());
                if (ListaCorresponsabilidad.get(position).getParcialI().equals("ND")){
                    holder.parcial1.setText(ListaCorresponsabilidad.get(position).getEstadoParcialI());
                }
                else if (ListaCorresponsabilidad.get(position).getEstadoParcialI().equals("CUMPLE")){
                    holder.parcial1.setText(ListaCorresponsabilidad.get(position).getEstadoParcialI()+" - "+ListaCorresponsabilidad.get(position).getParcialI()+" DIAS F.");
                }
                else {
                    holder.parcial1.setText(ListaCorresponsabilidad.get(position).getEstadoParcialI()+" - "+ListaCorresponsabilidad.get(position).getParcialI()+" DIAS F.");
                    holder.parcial1.setTextColor(Color.RED);
                }


                if (ListaCorresponsabilidad.get(position).getParcialII().equals("ND")){
                    holder.parcial2.setText(ListaCorresponsabilidad.get(position).getEstadoParcialII());
                }
                else if (ListaCorresponsabilidad.get(position).getEstadoParcialII().equals("CUMPLE")){
                    holder.parcial2.setText(ListaCorresponsabilidad.get(position).getEstadoParcialII()+" - "+ListaCorresponsabilidad.get(position).getParcialII()+" DIAS F.");
                }
                else {
                    holder.parcial2.setText(ListaCorresponsabilidad.get(position).getEstadoParcialII()+" - "+ListaCorresponsabilidad.get(position).getParcialII()+" DIAS F.");
                    holder.parcial2.setTextColor(Color.RED);
                }
                /* sec b parciales*/

                if (ListaCorresponsabilidad.get(position).getParcialIB().equals("ND")){
                    holder.parcial1b.setText(ListaCorresponsabilidad.get(position).getEstadoParcialIB());
                }
                else if (ListaCorresponsabilidad.get(position).getEstadoParcialIB().equals("CUMPLE")){
                    holder.parcial1b.setText(ListaCorresponsabilidad.get(position).getEstadoParcialIB()+" - "+ListaCorresponsabilidad.get(position).getParcialIB()+" DIAS F.");
                }
                else {
                    holder.parcial1b.setText(ListaCorresponsabilidad.get(position).getEstadoParcialIB()+" - "+ListaCorresponsabilidad.get(position).getParcialIB()+" DIAS F.");
                    holder.parcial1b.setTextColor(Color.RED);
                }


                if (ListaCorresponsabilidad.get(position).getParcialIIB().equals("ND")){
                    holder.parcial2b.setText(ListaCorresponsabilidad.get(position).getEstadoParcialIIB());
                }
                else if (ListaCorresponsabilidad.get(position).getEstadoParcialIIB().equals("CUMPLE")){
                    holder.parcial2b.setText(ListaCorresponsabilidad.get(position).getEstadoParcialIIB()+" - "+ListaCorresponsabilidad.get(position).getParcialIIB()+" DIAS F.");
                }
                else {
                    holder.parcial2b.setText(ListaCorresponsabilidad.get(position).getEstadoParcialIIB()+" - "+ListaCorresponsabilidad.get(position).getParcialIIB()+" DIAS F.");
                    holder.parcial2b.setTextColor(Color.RED);
                }

                holder.visitamedica.setText(ListaCorresponsabilidad.get(position).getVisitaMedicaIngr());
                holder.yearRegistro.setText("REGISTRO CORRESPONDIENTE AL AÑO "+ListaCorresponsabilidad.get(position).getYearRegistro());
                holder.yearRegistrob.setText("REGISTRO CORRESPONDIENTE AL AÑO "+ListaCorresponsabilidad.get(position).getYearRegistroB());
                holder.Visita1.setText(ListaCorresponsabilidad.get(position).getFechVisitamedica1());
                holder.CodVisita1.setText(ListaCorresponsabilidad.get(position).getCodVisitaMedica1());
                holder.Visita2.setText(ListaCorresponsabilidad.get(position).getFechVisitamedica2());
                holder.CodVisita2.setText(ListaCorresponsabilidad.get(position).getCodVisitaMedica2());
                holder.Visita3.setText(ListaCorresponsabilidad.get(position).getFechVisitamedica3());
                holder.CodVisita3.setText(ListaCorresponsabilidad.get(position).getCodVisitaMedica3());
                holder.Visita4.setText(ListaCorresponsabilidad.get(position).getFechVisitamedica4());
                holder.CodVisita4.setText(ListaCorresponsabilidad.get(position).getCodVisitaMedica4());
                holder.Visita5.setText(ListaCorresponsabilidad.get(position).getFechVisitamedica5());
                holder.CodVisita5.setText(ListaCorresponsabilidad.get(position).getCodVisitaMedica5());
                holder.Visita6.setText(ListaCorresponsabilidad.get(position).getFechVisitamedica6());
                holder.CodVisita6.setText(ListaCorresponsabilidad.get(position).getCodVisitaMedica6());

                if (!ListaCorresponsabilidad.get(position).getCodVisitaMedica1().equals("N/D")){
                    holder.llvisitas0.setVisibility(View.VISIBLE);
                    holder.llvisitas1.setVisibility(View.VISIBLE);
                    holder.llvisitaingreso.setVisibility(View.VISIBLE);
                }
                if (!ListaCorresponsabilidad.get(position).getCodVisitaMedica2().equals("N/D")){
                    holder.llvisitas2.setVisibility(View.VISIBLE);
                }
                if (!ListaCorresponsabilidad.get(position).getCodVisitaMedica3().equals("N/D")){
                    holder.llvisitas3.setVisibility(View.VISIBLE);
                }
                if (!ListaCorresponsabilidad.get(position).getCodVisitaMedica4().equals("N/D")){
                    holder.llvisitas4.setVisibility(View.VISIBLE);
                }
                if (!ListaCorresponsabilidad.get(position).getCodVisitaMedica5().equals("N/D")){
                    holder.llvisitas5.setVisibility(View.VISIBLE);
                }
                if (!ListaCorresponsabilidad.get(position).getCodVisitaMedica6().equals("N/D")){
                    holder.llvisitas6.setVisibility(View.VISIBLE);
                }

                /* visibilidad notas*/
                if (!ListaCorresponsabilidad.get(position).getParcialI().equals("ND") || !ListaCorresponsabilidad.get(position).getParcialII().equals("ND")){
                    holder.llenota1.setVisibility(View.VISIBLE);
                    holder.llnotas1.setVisibility(View.VISIBLE);
                }
                if (!ListaCorresponsabilidad.get(position).getParcialIB().equals("ND") || !ListaCorresponsabilidad.get(position).getParcialIIB().equals("ND")){
                    holder.llenota2.setVisibility(View.VISIBLE);
                    holder.llnotas2.setVisibility(View.VISIBLE);
                }

            }catch (Throwable e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return ListaCorresponsabilidad.size();
        }

        public class ViewHolderCorresponsabilidad extends RecyclerView.ViewHolder {
            TextView Nombre_Persona,identidad,per_edad,sexo,estadoMatricula,parcial1,parcial2,parcial1b,parcial2b,visitamedica,yearRegistro,yearRegistrob,Visita1,CodVisita1,Visita2,CodVisita2,Visita3,CodVisita3,Visita4,CodVisita4,Visita5,CodVisita5,Visita6,CodVisita6;
            LinearLayout llvisitas0,llvisitas1,llvisitas2,llvisitas3,llvisitas4,llvisitas5,llvisitas6, llenota1,llenota2,llnotas1,llnotas2,llvisitaingreso;
            public ViewHolderCorresponsabilidad(View itemView) {
                super(itemView);
                Nombre_Persona = (TextView) itemView.findViewById(R.id.Nombre_Persona);
                identidad=(TextView) itemView.findViewById(R.id.identidad);
                per_edad=(TextView) itemView.findViewById(R.id.per_edad);
                sexo=(TextView) itemView.findViewById(R.id.sexo);
                estadoMatricula=(TextView) itemView.findViewById(R.id.estadoMatricula);
                parcial1=(TextView) itemView.findViewById(R.id.parcial1);
                parcial2=(TextView) itemView.findViewById(R.id.parcial2);
                parcial1b=(TextView) itemView.findViewById(R.id.parcial1b);
                parcial2b=(TextView) itemView.findViewById(R.id.parcial2b);
                visitamedica=(TextView) itemView.findViewById(R.id.visitamedica);
                yearRegistro=(TextView) itemView.findViewById(R.id.yearRegistro);
                yearRegistrob=(TextView) itemView.findViewById(R.id.yearRegistroB);
                llvisitas0=(LinearLayout) itemView.findViewById(R.id.llvisita0);
                llvisitas1=(LinearLayout) itemView.findViewById(R.id.llvisita1);
                llvisitas2=(LinearLayout) itemView.findViewById(R.id.llvisita2);
                llvisitas3=(LinearLayout) itemView.findViewById(R.id.llvisita3);
                llvisitas4=(LinearLayout) itemView.findViewById(R.id.llvisita4);
                llvisitas5=(LinearLayout) itemView.findViewById(R.id.llvisita5);
                llvisitas6=(LinearLayout) itemView.findViewById(R.id.llvisita6);
                llenota1=(LinearLayout) itemView.findViewById(R.id.llenotas1);
                llenota2=(LinearLayout) itemView.findViewById(R.id.llenotas2);
                llnotas1=(LinearLayout) itemView.findViewById(R.id.llnotas1);
                llnotas2=(LinearLayout) itemView.findViewById(R.id.llnotas2);
                llvisitaingreso=(LinearLayout) itemView.findViewById(R.id.visitaingreso);
                Visita1=(TextView) itemView.findViewById(R.id.Visita1);
                Visita2=(TextView) itemView.findViewById(R.id.Visita2);
                Visita3=(TextView) itemView.findViewById(R.id.Visita3);
                Visita4=(TextView) itemView.findViewById(R.id.Visita4);
                Visita5=(TextView) itemView.findViewById(R.id.Visita5);
                Visita6=(TextView) itemView.findViewById(R.id.Visita6);
                CodVisita1=(TextView) itemView.findViewById(R.id.CodVisita1);
                CodVisita2=(TextView) itemView.findViewById(R.id.CodVisita2);
                CodVisita3=(TextView) itemView.findViewById(R.id.CodVisita3);
                CodVisita4=(TextView) itemView.findViewById(R.id.CodVisita4);
                CodVisita5=(TextView) itemView.findViewById(R.id.CodVisita5);
                CodVisita6=(TextView) itemView.findViewById(R.id.CodVisita6);

            }
        }
    }
}
