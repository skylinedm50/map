package com.map_movil.map_movil.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenter;
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenterImpl;
import com.map_movil.map_movil.view.validar_hogares.ListarValidacionesView;
import com.map_movil.map_movil.view.validar_hogares.ValidarNucleoActivity;

import java.util.ArrayList;

import io.realm.RealmResults;

public class hogares_validar_adapter extends RecyclerView.Adapter<hogares_validar_adapter.validar_holder>
                                     implements ListarValidacionesView {

    private ListarValidacionesPresenter listarValidacionesPresenter;
    private ArrayList<Hogar_Validar> Hogar_validar =new ArrayList<>();

    public hogares_validar_adapter(Context context){
        this.listarValidacionesPresenter = new ListarValidacionesPresenterImpl(this , context);
    }

    @NonNull
    @Override
    public hogares_validar_adapter.validar_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hogar_validar,parent, false);
        validar_holder holder = new validar_holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull hogares_validar_adapter.validar_holder holder, final int position) {

        holder.estado_validacion.setVisibility(View.GONE);
         holder.nombre_persona.setText(this.Hogar_validar.get(position).getNombre());
         holder.direccion_casa.setText(this.Hogar_validar.get(position).getHogar_direccion());
         holder.referencia_geografica.setText(
                 this.Hogar_validar.get(position).getDesc_departamento()+" - "+
                 this.Hogar_validar.get(position).getDesc_municipio()+" - "+
                 this.Hogar_validar.get(position).getDesc_aldea()
         );

        holder.ly_hogar_validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ValidarNucleoActivity.class);
                intent.putExtra("per_persona", Hogar_validar.get(position).getPer_persona());
                intent.putExtra("identidad", Hogar_validar.get(position).getPer_identidad());
                intent.putExtra("hogar", Hogar_validar.get(position).getHog_hogar());
                intent.putExtra("Umbral", Hogar_validar.get(position).getHog_umbral());
                intent.putExtra("Direccion", Hogar_validar.get(position).getHogar_direccion());
                intent.putExtra("Aldea",  Hogar_validar.get(position).getDesc_departamento()+" - "+
                                                     Hogar_validar.get(position).getDesc_municipio()+" - "+
                                                     Hogar_validar.get(position).getDesc_aldea());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.Hogar_validar.size();
    }

    @Override
    public void BuscarValidaciones() {
        this.listarValidacionesPresenter.BuscarValidaciones();
    }

    @Override
    public void MostarValidaciones(RealmResults<Hogar_Validar> Personas) {
        for(int i = 0; i < Personas.size(); i++){
            this.Hogar_validar.add(Personas.get(i));
        }
        notifyDataSetChanged();
    }

    public class validar_holder extends RecyclerView.ViewHolder {

        private TextView nombre_persona;
        private TextView direccion_casa;
        private TextView referencia_geografica;
        private LinearLayout ly_hogar_validar;
        private TextView estado_validacion;


        public validar_holder(View itemView) {
            super(itemView);
            this.ly_hogar_validar = itemView.findViewById(R.id.ly_persona);
            this.nombre_persona = itemView.findViewById(R.id.Nombre_Persona);
            this.direccion_casa = itemView.findViewById(R.id.direccion_casa);
            this.referencia_geografica = itemView.findViewById(R.id.referencia);
            this.estado_validacion = itemView.findViewById(R.id.estado_validacion);
        }
    }
}
