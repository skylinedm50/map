package com.map_movil.map_movil.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.TextView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.elemnts.Permissions;
import com.map_movil.map_movil.model.Hogar_Validaciones_Realizadas;
import com.map_movil.map_movil.presenter.validar_hogares.ValidarNucleoPresenter;
import com.map_movil.map_movil.presenter.validar_hogares.ValidarNucleoPresenterImpl;
import com.map_movil.map_movil.view.validar_hogares.ValidarNucleoView;
import java.util.ArrayList;

public class nucleo_hogar_validar_Nucleo_adapter extends RecyclerView.Adapter<nucleo_hogar_validar_Nucleo_adapter.nucleo_adapter> implements ValidarNucleoView {

    private ValidarNucleoPresenter validarNucleoPresenter;
    private ArrayList<String[]> Hogar_Validar = new ArrayList<String[]>();
    private ArrayList<Hogar_Validaciones_Realizadas> hogar_validaciones_realizadas;

    private Activity activity;
    private Context context;
    private int per_persona;
    private int hog_hogar;
    private String[] macros = {};

    private CheckBox doc_cambio_titular;
    private CheckBox doc_incorporacion;
    private CheckBox doc_identidad;
    private CheckBox doc_compromiso;
    private CheckBox doc_actualizacion;
    private CheckBox doc_partida;
    private CheckBox doc_educacion;
    private CheckBox doc_desagregar;
    private CheckBox doc_debe_doc;

    public nucleo_hogar_validar_Nucleo_adapter(Context context , Activity activity ,
                                               int hog_hogar    ){
        this.activity = activity;
        this.context = context;
        this.validarNucleoPresenter = new ValidarNucleoPresenterImpl(this , context , activity);
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

        holder.Nombre.setText(this.Hogar_Validar.get(position)[0]);
        holder.Identidad.setText(this.Hogar_Validar.get(position)[1]);
        holder.Sexo.setText(this.Hogar_Validar.get(position)[2]);
        holder.Edad.setText(this.Hogar_Validar.get(position)[3]);
        holder.Estado.setText(this.Hogar_Validar.get(position)[4]);

        if(this.Hogar_Validar.get(position)[5] == "1"){
            holder.Encabezado.setVisibility(View.VISIBLE);
        }else{
            holder.Encabezado.setVisibility(View.GONE);
        }

        if(this.Hogar_Validar.get(position)[6].equals( "1" )){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#dff0f8"));
        }else{
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permissions permissions = new Permissions(context, activity);
                boolean granted = permissions.LocationPermissionsGranted();

                per_persona = Integer.parseInt(Hogar_Validar.get(position)[7]);
                macros = new String[] { Hogar_Validar.get(position)[5] ,  Hogar_Validar.get(position)[0] ,
                                        String.valueOf( position )     ,  Hogar_Validar.get(position)[1] };

                if(granted == true){
                    MostarPopUp();
                }
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
    public void MostarDatos(ArrayList<String[]> persona , ArrayList<Hogar_Validaciones_Realizadas> hogar_validaciones_realizadas) {

        this.Hogar_Validar = persona;
        this.hogar_validaciones_realizadas = hogar_validaciones_realizadas;
        notifyDataSetChanged();
    }

    public  void MostarPopUp(){

        PopUp( Integer.valueOf( macros[0] ) ,
                macros[1] ,Integer.valueOf(  macros[2] ) , macros[3]
        );
    }

    @Override
    public void PopUp(int titular , String Persona , final int index , String identidad) {
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
        LP.height = (int)(( mheight/2) * ((titular == 1)?1.5:1.1));

        TextView ng_CancelPop         = (TextView) v.findViewById(R.id.ng_cancelpop);
        TextView ng_Selected          = (TextView) v.findViewById(R.id.ng_schedule_selected);
        final TextView error_mjs      = (TextView) v.findViewById(R.id.validacion_msj);
        TextView txt_nombre           = (TextView) v.findViewById(R.id.doc_persona);
        TextView txt_identidad        = (TextView) v.findViewById(R.id.ident_persona);
        final EditText edit_obs       = (EditText) v.findViewById(R.id.editObs);

        if(titular == 1){
            edit_obs.setVisibility(View.VISIBLE);
            edit_obs.setText( (hogar_validaciones_realizadas.size() > 0)?hogar_validaciones_realizadas.get(index).getObservacion():"" );
        }else{
            edit_obs.setVisibility(View.GONE);
            edit_obs.setText(null);
        }

        txt_nombre.setText(Persona);
        txt_identidad.setText(identidad);
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
                        doc_identidad.isChecked()     || doc_compromiso.isChecked()    ||
                        doc_actualizacion.isChecked() || doc_partida.isChecked()       ||
                        doc_educacion.isChecked()     || doc_desagregar.isChecked()    ||
                        doc_debe_doc.isChecked()      || doc_cambio_titular.isChecked()||
                        doc_incorporacion.isChecked()
                   )
                {
                    validarNucleoPresenter.GuardarValidacion(per_persona                                   ,
                            hog_hogar                                      ,
                            return_integer(doc_identidad.isChecked())      ,
                            return_integer(doc_compromiso.isChecked()    ) ,
                            return_integer(doc_actualizacion.isChecked() ) ,
                            return_integer(doc_partida.isChecked()       ) ,
                            return_integer(doc_educacion.isChecked()     ) ,
                            return_integer(doc_desagregar.isChecked()    ) ,
                            return_integer(doc_debe_doc.isChecked()      ) ,
                            return_integer(doc_incorporacion.isChecked() ) ,
                            return_integer(doc_cambio_titular.isChecked()) ,
                            edit_obs.getText().toString()
                    );
                    dialog.cancel();
                    Hogar_Validar.get(index)[6]="1";
                    notifyDataSetChanged();
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

    public void Iniciar_Controles(View v , int titular ){

        this.doc_identidad      = (CheckBox) v.findViewById(R.id.doc_identidad);
        this.doc_compromiso     = (CheckBox) v.findViewById(R.id.doc_compromiso);
        this.doc_actualizacion  = (CheckBox) v.findViewById(R.id.doc_actualizacion);
        this.doc_incorporacion  = (CheckBox) v.findViewById(R.id.doc_agregar_menor);
        this.doc_cambio_titular = (CheckBox) v.findViewById(R.id.doc_cambio_titular);
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
            this.doc_incorporacion.setVisibility(View.GONE);
            this.doc_cambio_titular.setVisibility(View.GONE);
        }

        for(int x = 0 ; x < hogar_validaciones_realizadas.size(); x++){
            if(per_persona == this.hogar_validaciones_realizadas.get(x).getPer_persona()){
                this.doc_partida.setChecked(  (hogar_validaciones_realizadas.get(x).getPartidad_nacimiento())==1?true:false );
                this.doc_actualizacion.setChecked( (hogar_validaciones_realizadas.get(x).getActualizar_datos())==1?true:false );
                this.doc_educacion.setChecked( (hogar_validaciones_realizadas.get(x).getConstancia_educacion())==1?true:false );
                this.doc_desagregar.setChecked( (hogar_validaciones_realizadas.get(x).getDesagregar())==1?true:false );
                this.doc_debe_doc.setChecked( (hogar_validaciones_realizadas.get(x).getDebe_documentos())==1?true:false );
                this.doc_identidad.setChecked( (hogar_validaciones_realizadas.get(x).getTarjeta_identidad())==1?true:false );
                this.doc_compromiso.setChecked( (hogar_validaciones_realizadas.get(x).getActa_compromiso())==1?true:false );
                this.doc_incorporacion.setChecked( (hogar_validaciones_realizadas.get(x).getIncorporacion())==1?true:false );
                this.doc_cambio_titular.setChecked( (hogar_validaciones_realizadas.get(x).getCambio_titular())==1?true:false );
            }
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
