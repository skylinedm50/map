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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.corresponsabilidad.ApiAdapterCorresponsabilidad;
import com.map_movil.map_movil.api.corresponsabilidad.ApiServiceCorresponsabilidad;
import com.map_movil.map_movil.model.Corresponsabilidades;
import com.map_movil.map_movil.model.CorresponsabilidadesClearByMenor;
import com.map_movil.map_movil.presenter.corresponsabilidad.corresponsabilidadpresenter;
import com.map_movil.map_movil.presenter.corresponsabilidad.corresponsabilidadpresenterimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CorresponsabilidadFragment extends Fragment implements SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener, corresponsabilidadview {

    private View view;
    private LinearLayout linearLayoutInicial;
    private LinearLayout linearLayouttitular;
    private TextView textViewFindinfo;
    private TextView textViewTitular;
    private TextView textViewHogar;
    private String NombreTitular;
    private String CodHogar;
    private corresponsabilidadpresenter corresponsabilidadpresenter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    private RecyclerView recyclerCorresponsabilidad;
    private AdaptadorCorresponsabilidad adaptadorcorresponsabilidad;
    private ProgressBar progressBar;
    private RelativeLayout lyCorr;

    public CorresponsabilidadFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.corresponsabilidadpresenter = new corresponsabilidadpresenterimpl(getContext() , this);
        this.view = inflater.inflate(R.layout.fragment_corresponsabilidad, container, false);
        this.linearLayoutInicial= (LinearLayout) view.findViewById(R.id.linearLayoutinfosearch);
        this.linearLayouttitular= (LinearLayout)view.findViewById(R.id.llinfhogar);
        this.textViewFindinfo= (TextView) view.findViewById(R.id.textViewMessageFind);
        this.textViewTitular = (TextView) view.findViewById(R.id.textViewtitular);
        this.textViewHogar= (TextView) view.findViewById(R.id.textViewhogar);
        this.recyclerCorresponsabilidad = (RecyclerView)view.findViewById(R.id.rv_personas_validar);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        this.lyCorr = (RelativeLayout) view.findViewById(R.id.ly_corr);

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


    @Override
    public void findCorrByTitular(String strCodIdentidad) {
        this.lyCorr.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);
        this.corresponsabilidadpresenter.findCorrByTitular(strCodIdentidad);
    }

    @Override
    public void showdata( ArrayList <CorresponsabilidadesClearByMenor>  corresponsabilidades , String NombreTitular , String CodHogar) {
           this.NombreTitular = NombreTitular;
           this.CodHogar = CodHogar;

        recyclerCorresponsabilidad.setLayoutManager(linearLayoutManager);
        adaptadorcorresponsabilidad = new AdaptadorCorresponsabilidad(corresponsabilidades);
        recyclerCorresponsabilidad.setAdapter(adaptadorcorresponsabilidad);
        recyclerCorresponsabilidad.setHasFixedSize(true);
        findDataShowMessage("",true);
    }

    @Override
    public void findDataShowMessage(String error , Boolean find) {
      //  String strTextMessage = "Ingrese el numero de identidad para buscar las corresponsabilidades asociadas.";

        this.lyCorr.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.GONE);

        if(find == true){
            textViewTitular.setText("TITULAR: "+ NombreTitular);
            textViewHogar.setText("HOGAR: "+ CodHogar);
            linearLayoutInicial.setVisibility(View.GONE);
            recyclerCorresponsabilidad.setVisibility(View.VISIBLE);
            linearLayouttitular.setVisibility(View.VISIBLE);
        }else{
            recyclerCorresponsabilidad.setVisibility(View.GONE);
            linearLayouttitular.setVisibility(View.GONE);
            linearLayoutInicial.setVisibility(View.VISIBLE);
            textViewFindinfo.setText(error);
            textViewFindinfo.setGravity(Gravity.CENTER);
        }
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
