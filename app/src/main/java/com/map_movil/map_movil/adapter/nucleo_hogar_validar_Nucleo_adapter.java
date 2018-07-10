package com.map_movil.map_movil.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.Realm.Hogar_Validar;
import com.map_movil.map_movil.presenter.validar_hogares.ValidarNucleoPresenter;
import com.map_movil.map_movil.presenter.validar_hogares.ValidarNucleoPresenterImpl;
import com.map_movil.map_movil.view.validar_hogares.ValidarNucleoView;

import java.util.ArrayList;

import io.realm.RealmResults;

public class nucleo_hogar_validar_Nucleo_adapter extends RecyclerView.Adapter<nucleo_hogar_validar_Nucleo_adapter.nucleo_adapter> implements ValidarNucleoView {

    private ValidarNucleoPresenter validarNucleoPresenter;
    private ArrayList<Hogar_Validar> Hogar_Validar = new ArrayList<Hogar_Validar>();
    private Activity activity;
    private Context context;
    private int per_persona;
    private int hog_hogar;
    private CheckBox doc_identidad;
    private CheckBox doc_compromiso;
    private CheckBox doc_actualizacion;
    private CheckBox doc_partida;
    private CheckBox doc_educacion;
    private CheckBox doc_desagregar;
    private CheckBox doc_debe_doc;
    private ArrayList<int[]> personas_validada = new ArrayList<>();

    public nucleo_hogar_validar_Nucleo_adapter(Context context , Activity activity ,
                                               int per_persona , int hog_hogar    ){
        this.activity = activity;
        this.context = context;
        this.validarNucleoPresenter = new ValidarNucleoPresenterImpl(this , context);
        this.per_persona = per_persona;
        this.hog_hogar = hog_hogar;
    }


    @NonNull
    @Override
    public nucleo_hogar_validar_Nucleo_adapter.nucleo_adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nucleo_hogar_validar , parent , false);
        nucleo_adapter nucleo_adapter = new nucleo_adapter(view);
        return nucleo_adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull nucleo_hogar_validar_Nucleo_adapter.nucleo_adapter holder, final int position) {

        holder.Nombre.setText(this.Hogar_Validar.get(position).getNombre());
        holder.Identidad.setText(this.Hogar_Validar.get(position).getPer_identidad());
        holder.Sexo.setText(this.Hogar_Validar.get(position).getSexo());
        holder.Edad.setText(this.Hogar_Validar.get(position).getEdad());
        holder.Estado.setText(this.Hogar_Validar.get(position).getPer_estado_descripcion());

        if(this.Hogar_Validar.get(position).getPer_titular() == 1){
            holder.Encabezado.setVisibility(View.VISIBLE);
        }else{
            holder.Encabezado.setVisibility(View.GONE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostarPopUp(Hogar_Validar.get(position).getPer_titular() , Hogar_Validar.get(position).getNombre());
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.Hogar_Validar.size();
    }

    @Override
    public void BuscarDatos(String identidad) {
        this.validarNucleoPresenter.BuscarDatos(identidad);
    }

    @Override
    public void MostarDatos(RealmResults<Hogar_Validar> Personas) {

        for(int i = 0 ; i < Personas.size(); i++){
            this.Hogar_Validar.add(Personas.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public void MostarPopUp(int titular , String Persona) {
        Display display = this.activity.getWindowManager().getDefaultDisplay();
        int mwidth = display.getWidth();
        int mheight = display.getHeight();

        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        View v = LayoutInflater.from(this.context).inflate(R.layout.popup_validacion ,null);

        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
        LP.copyFrom(dialog.getWindow().getAttributes());
        LP.width  = (int)(( mwidth/2) * 1.8);
        LP.height = (int)(( mheight/2) * 1.1);

        TextView ng_CancelPop   = (TextView) v.findViewById(R.id.ng_cancelpop);
        TextView ng_Selected    = (TextView) v.findViewById(R.id.ng_schedule_selected);
        final TextView error_mjs      = (TextView) v.findViewById(R.id.validacion_msj);
        TextView txt_nombre     = (TextView) v.findViewById(R.id.doc_persona);
        txt_nombre.setText(Persona);
        error_mjs.setVisibility(View.GONE);

        Iniciar_Controles(v , titular);

        ng_CancelPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        ng_Selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                        doc_identidad.isChecked() || doc_compromiso.isChecked()  ||
                        doc_actualizacion.isChecked() || doc_partida.isChecked() ||
                        doc_educacion.isChecked()     || doc_desagregar.isChecked() ||
                        doc_debe_doc.isChecked()
                        )
                {
                    validarNucleoPresenter.GuardarValidacion(per_persona                                   ,
                            hog_hogar                                     ,
                            return_integer(doc_identidad.isChecked())      ,
                            return_integer(doc_compromiso.isChecked()    ) ,
                            return_integer(doc_actualizacion.isChecked() ) ,
                            return_integer(doc_partida.isChecked()       ) ,
                            return_integer(doc_educacion.isChecked()     ) ,
                            return_integer(doc_desagregar.isChecked()    ) ,
                            return_integer(doc_debe_doc.isChecked()      )
                    );
                    dialog.cancel();
                }else{
                    error_mjs.setVisibility(View.VISIBLE);
                }
            }
        });

        dialog.getWindow().setAttributes(LP);
    }


    private int return_integer(boolean valor){
        return  valor?1:0;
    }

    public void Iniciar_Controles(View v , int titular){

        this.doc_identidad      = (CheckBox) v.findViewById(R.id.doc_identidad);
        this.doc_compromiso     = (CheckBox) v.findViewById(R.id.doc_compromiso);
        this.doc_actualizacion  = (CheckBox) v.findViewById(R.id.doc_actualizacion);
        /*-----------------------------------------------------------------------*/
        this.doc_actualizacion  = (CheckBox) v.findViewById(R.id.doc_actualizacion);
        this.doc_partida        = (CheckBox) v.findViewById(R.id.doc_partida);
        this.doc_educacion      = (CheckBox) v.findViewById(R.id.doc_educacion);
        this.doc_desagregar     = (CheckBox) v.findViewById(R.id.doc_desagregar);
        this.doc_debe_doc       = (CheckBox) v.findViewById(R.id.doc_no_presento);

        if(titular == 1 ){
            //this.doc_actualizacion.setVisibility(View.GONE);
            this.doc_partida.setVisibility(View.GONE);
            this.doc_educacion.setVisibility(View.GONE);
            this.doc_desagregar.setVisibility(View.GONE);
            this.doc_debe_doc.setVisibility(View.GONE);
        }else{
            this.doc_identidad.setVisibility(View.GONE);
            this.doc_compromiso.setVisibility(View.GONE);
        }
    }

    public  class nucleo_adapter extends RecyclerView.ViewHolder{

        public TextView Nombre;
        public TextView Identidad;
        public TextView Sexo;
        public TextView Edad;
        public TextView Encabezado;
        public TextView Estado;
        public CardView cardView;


        public nucleo_adapter(View itemView) {
            super(itemView);

            this.Nombre = itemView.findViewById(R.id.nombre_persona);
            this.Identidad = itemView.findViewById(R.id.identidad_persona);
            this.Sexo = itemView.findViewById(R.id.sexo_persona);
            this.Edad = itemView.findViewById(R.id.edad_persona);
            this.Encabezado = itemView.findViewById(R.id.encabezado_nucleo);
            this.Estado = itemView.findViewById(R.id.estado_persona);
            this.cardView = itemView.findViewById(R.id.card_view);

        }
    }
}
