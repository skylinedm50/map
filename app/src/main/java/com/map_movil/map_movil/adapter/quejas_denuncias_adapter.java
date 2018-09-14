package com.map_movil.map_movil.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.QuejasDenuncias;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenter;
import com.map_movil.map_movil.presenter.Quejas.QuejasPresenterImpl;
import com.map_movil.map_movil.view.Quejas.QuejasFragment;
import com.map_movil.map_movil.view.Quejas.QuejasView;
import com.map_movil.map_movil.view.Quejas.RefrescarDatos;

import java.util.ArrayList;

public class quejas_denuncias_adapter extends RecyclerView.Adapter<quejas_denuncias_adapter.quejas_denuncias_holder>
                                      implements QuejasView
{

    private QuejasPresenter quejasPresenter;
    private Context context;
    private int RealizadosLenght   [] = {0,0};
    private int noRealizadosLenght [] = {0,0};
    private ArrayList<QuejasDenuncias> quejas_denuncias = new ArrayList<>();
    private ArrayList<QuejasDenuncias> Realizadas = new ArrayList<>();
    private ArrayList<QuejasDenuncias> NoRealizadas = new ArrayList<>();

    private int tipo_queja = 0;

    public quejas_denuncias_adapter(Context context , int tipo_queja ){
        this.context = context;
        this.quejasPresenter = new QuejasPresenterImpl(this);
        this.tipo_queja = tipo_queja;
    }

    @NonNull
    @Override
    public quejas_denuncias_adapter.quejas_denuncias_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hogar_rv , parent , false);
        quejas_denuncias_holder holder = new quejas_denuncias_holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull quejas_denuncias_adapter.quejas_denuncias_holder holder, int position) {

        if(this.tipo_queja == 1 ){
                holder.CodigoSolicitud.setText( this.Realizadas.get(position).getTipo_gestion()+" N°: "+
                                                String.valueOf(this.Realizadas.get(position).getCodigo_solicitud()));
                holder.NombreSolicitante.setText(this.Realizadas.get(position).getNombre_solicitante());
                holder.Descripcion.setText(this.Realizadas.get(position).getObservacion());
                holder.EstadoQueja.setText(this.Realizadas.get(position).getEstado());

                if(position == 0){
                    LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT ,1);
                    layoutParams.setMargins(0,0,0,400);
                    holder.ly_Divider.setLayoutParams(layoutParams);
                }else{
                    LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , 1);
                    layoutParams.setMargins(0,0,0,0);
                    holder.ly_Divider.setLayoutParams(layoutParams);
                }
        }
        else if(this.tipo_queja == 2) {
                holder.CodigoSolicitud.setText(this.NoRealizadas.get(position).getTipo_gestion()+" N°: "+
                                               String.valueOf(this.NoRealizadas.get(position).getCodigo_solicitud()));
                holder.NombreSolicitante.setText(this.NoRealizadas.get(position).getNombre_solicitante());
                holder.Descripcion.setText(this.NoRealizadas.get(position).getObservacion());
                holder.EstadoQueja.setText(this.NoRealizadas.get(position).getEstado());

                if(position == 0){
                    LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT ,1);
                    layoutParams.setMargins(0,0,0,400);
                    holder.ly_Divider.setLayoutParams(layoutParams);
                }else{
                    LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , 1);
                    layoutParams.setMargins(0,0,0,0);
                    holder.ly_Divider.setLayoutParams(layoutParams);
                }
        }


    }

    public ArrayList<QuejasDenuncias> ArrayAdapterQuejas(int accion){

        this.noRealizadosLenght[0] = this.noRealizadosLenght[1];
        this.RealizadosLenght[0] = this.RealizadosLenght[1];
        Crear_Grupo(this.quejas_denuncias);

        return (accion == 1)? Realizadas : NoRealizadas ;
    }


    @Override
    public int getItemCount() {
        if(this.tipo_queja == 2){
            return this.noRealizadosLenght[0];
        }else{
            return this.RealizadosLenght[0];
        }
    }

    public void ActualizarFiltro(ArrayList<QuejasDenuncias> filtro , int Lenght , int action ){

        this.NoRealizadas = (action == 2 && this.noRealizadosLenght[1] != 0) ? filtro : this.NoRealizadas;
        this.Realizadas = (action == 1 && this.RealizadosLenght[1] != 0) ? filtro : this.Realizadas;

        this.noRealizadosLenght[0] = (action == 2 && this.noRealizadosLenght[1] != 0 )? Lenght:this.noRealizadosLenght[1];
        this.RealizadosLenght[0] = (action == 1 && this.RealizadosLenght[1] != 0 )? Lenght:this.RealizadosLenght[1];
        notifyDataSetChanged();
    }


    @Override
    public void MostarQuejas(ArrayList<QuejasDenuncias> respuesta, int  RealizadosLenght , int noRealizadosLenght) {
        this.quejas_denuncias      = respuesta;
        this.RealizadosLenght[0]   = RealizadosLenght;
        this.noRealizadosLenght[0] = noRealizadosLenght;

        this.RealizadosLenght[1] = RealizadosLenght;
        this.noRealizadosLenght[1] = noRealizadosLenght;
        Crear_Grupo(respuesta);
        notifyDataSetChanged();
    }

    private void Crear_Grupo(ArrayList<QuejasDenuncias> respuesta){
        this.Realizadas.clear();
        this.NoRealizadas.clear();
        for(int i = 0; i<respuesta.size();i++){
            if(respuesta.get(i).getEstado().equals("Realizada")){
                this.Realizadas.add(respuesta.get(i));
            }else{
                this.NoRealizadas.add(respuesta.get(i));
            }
        }
    }


    @Override
    public void SolicitarQuejas() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        int UsuarioCod = sharedPreferences.getInt("codigo",0);
        this.quejasPresenter.SolicitarQuejas(UsuarioCod);
    }

    @Override
    public void RegistrarQueja() {

    }

    @Override
    public void ActualizarDatos() {

    }


    public static class quejas_denuncias_holder extends RecyclerView.ViewHolder {

        private TextView CodigoSolicitud;
        private TextView NombreSolicitante;
        private TextView Descripcion;
        private LinearLayout Ly_Queja;
        private TextView EstadoQueja;
        private LinearLayout ly_Divider;

        public quejas_denuncias_holder(View itemView) {
            super(itemView);

            this.Ly_Queja = itemView.findViewById(R.id.ly_main);
            this.CodigoSolicitud = itemView.findViewById(R.id.Txt_Principal);
            this.NombreSolicitante = itemView.findViewById(R.id.Txt_Secundario);
            this.Descripcion = itemView.findViewById(R.id.Descripción);
            this.EstadoQueja = itemView.findViewById(R.id.estado);
            this.ly_Divider = itemView.findViewById(R.id.Ly_divider);
        }
    }

}