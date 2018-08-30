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
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenter;
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenterImpl;
import com.map_movil.map_movil.view.validar_hogares.ListarValidacionesView;
import com.map_movil.map_movil.view.validar_hogares.ValidarNucleoActivity;

import java.util.ArrayList;

public class hogares_validar_adapter extends RecyclerView.Adapter<hogares_validar_adapter.validar_holder>
                                     implements ListarValidacionesView {

    private ListarValidacionesPresenter listarValidacionesPresenter;
    private ArrayList<String[]> Hogar_validar =new ArrayList<>();

    public hogares_validar_adapter(Context context){
        this.listarValidacionesPresenter = new ListarValidacionesPresenterImpl(this , context);
    }

    @NonNull
    @Override
    public hogares_validar_adapter.validar_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hogar_rv,parent, false);
        validar_holder holder = new validar_holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull hogares_validar_adapter.validar_holder holder, final int position) {

         holder.nombre_persona.setText(this.Hogar_validar.get(position)[0]);
         holder.direccion_casa.setText(this.Hogar_validar.get(position)[1]);
         holder.referencia_geografica.setText( this.Hogar_validar.get(position)[2]);
         holder.estado_validacion.setText(this.Hogar_validar.get(position)[8]);
         holder.ly_hogar_validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ValidarNucleoActivity.class);
                intent.putExtra("per_persona", Hogar_validar.get(position)[3]);
                intent.putExtra("identidad", Hogar_validar.get(position)[4]);
                intent.putExtra("hogar", Integer.valueOf( Hogar_validar.get(position)[5]) );
                intent.putExtra("Umbral", Hogar_validar.get(position)[6]);
                intent.putExtra("Direccion", Hogar_validar.get(position)[1]);
                intent.putExtra("Aldea",  Hogar_validar.get(position)[2]);
                intent.putExtra("EstadoHogar", Hogar_validar.get(position)[7]);
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
    public void MostarValidaciones(ArrayList<String[]> Personas) {
        this.Hogar_validar = Personas;
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
            this.ly_hogar_validar = itemView.findViewById(R.id.ly_main);
            this.nombre_persona = itemView.findViewById(R.id.Txt_Principal);
            this.direccion_casa = itemView.findViewById(R.id.Txt_Secundario);
            this.referencia_geografica = itemView.findViewById(R.id.Descripción);
            this.estado_validacion = itemView.findViewById(R.id.estado);
        }
    }
}
