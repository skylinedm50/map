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
import com.map_movil.map_movil.decorator.HeaderInterface;
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenter;
import com.map_movil.map_movil.presenter.validar_hogares.ListarValidacionesPresenterImpl;
import com.map_movil.map_movil.view.validar_hogares.ListarValidacionesView;
import com.map_movil.map_movil.view.validar_hogares.ValidarNucleoActivity;
import java.util.ArrayList;

public class hogares_validar_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
                                     implements ListarValidacionesView, HeaderInterface {

    private ListarValidacionesPresenter listarValidacionesPresenter;
    private ArrayList<String[]> Hogar_validar =new ArrayList<>();
    private ArrayList<String[]> valHogares =new ArrayList<>();

    public hogares_validar_adapter(Context context){
        this.listarValidacionesPresenter = new ListarValidacionesPresenterImpl(this , context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new header_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticky_header, parent, false));
            default:
                return new validar_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hogar_rv, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(Hogar_validar.get(position)[9]);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            if(holder instanceof validar_holder){
                ( (validar_holder) holder).nombre_persona.setText(this.Hogar_validar.get(position)[0]);
                ( (validar_holder) holder).direccion_casa.setText(this.Hogar_validar.get(position)[1]);
                ( (validar_holder) holder).referencia_geografica.setText(this.Hogar_validar.get(position)[2]);
                ( (validar_holder) holder).estado_validacion.setText(this.Hogar_validar.get(position)[8]);
                ( (validar_holder) holder).ly_hogar_validar.setOnClickListener(new View.OnClickListener() {
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
            }else {
                ( (header_holder) holder).header.setText(Hogar_validar.get(position)[2].split("-")[0]+"-"+Hogar_validar.get(position)[2].split("-")[1]);
                ( (header_holder) holder).header_amount.setText(Hogar_validar.get(position)[10]+" Hogares");
            }
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
        this.valHogares    = Personas;
        notifyDataSetChanged();
    }

    public ArrayList<String[]> ListarHogaresValidar(){
        this.Hogar_validar = this.valHogares;
        return  this.Hogar_validar;
    }

    public void ActualizarFiltro(ArrayList<String[]> filtro){
        this.Hogar_validar = (filtro.size()!= 0)?filtro: this.Hogar_validar;
        notifyDataSetChanged();
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int PosicionHeader = 0;
        do{
            if(this.isHeader(itemPosition)){
                PosicionHeader = itemPosition;
                break;
            }
            itemPosition -= 1;
        }while(itemPosition >= 0);
        return PosicionHeader;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.item_sticky_header;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView txt_ubicacion = header.findViewById(R.id.Header);
        TextView txt_cantidad  = header.findViewById(R.id.Header_amount);

        txt_ubicacion.setText(Hogar_validar.get(headerPosition)[2].split("-")[0]+"-"+Hogar_validar.get(headerPosition)[2].split("-")[1]);
        txt_cantidad.setText(Hogar_validar.get(headerPosition)[10]+" Hogares");
    }

    @Override
    public boolean isHeader(int itemPosition) {
        return this.Hogar_validar.get(itemPosition)[9].equals("1");
    }

    class validar_holder extends RecyclerView.ViewHolder {

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
    class header_holder extends  RecyclerView.ViewHolder {

        private TextView header;
        private TextView header_amount;

        public header_holder(View itemView) {
            super(itemView);
            this.header        = itemView.findViewById(R.id.Header);
            this.header_amount = itemView.findViewById(R.id.Header_amount);
        }
    }
}
