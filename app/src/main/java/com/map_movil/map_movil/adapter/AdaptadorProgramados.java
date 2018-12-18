package com.map_movil.map_movil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.PagosProgramados;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdaptadorProgramados extends BaseAdapter {

    private Context context;
    private ArrayList<PagosProgramados> listPagos;

    public AdaptadorProgramados(Context context, ArrayList<PagosProgramados> listPagos) {
        this.context = context;
        this.listPagos = listPagos;
    }

    @Override
    public int getCount() {
        return listPagos.size();
    }

    @Override
    public Object getItem(int position) {
        return listPagos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PagosProgramados Item = (PagosProgramados) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_planilla_programado,null);
        TextView textViewNombre      = (TextView) convertView.findViewById(R.id.Nombre_Persona) ;
        TextView textViewIdentidad   = (TextView) convertView.findViewById(R.id.Identidad_Persona) ;
        TextView textViewMontoPagar  = (TextView) convertView.findViewById(R.id.monto_pagar) ;
        TextView textViewCodhoga     = (TextView) convertView.findViewById(R.id.codhogar) ;
        TextView textViewcaserio     = (TextView) convertView.findViewById(R.id.caserio) ;
        LinearLayout divider         = (LinearLayout) convertView.findViewById(R.id.divider_programado);

        if(position == listPagos.size() -1 ){
            LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT ,1);
            layoutParams.setMargins(0,0,0,400);
            divider.setLayoutParams(layoutParams);
        }else{
            LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , 1);
            layoutParams.setMargins(0,0,0,0);
            divider.setLayoutParams(layoutParams);
        }

        textViewNombre.setText(Item.getStrnombre_titular());
        textViewCodhoga.setText("CÓDIGO HOGAR : " + Item.getStrcodigo_hogar());
        textViewcaserio.setText("CASERIO : " + Item.getStrdesc_caserio());
        textViewIdentidad.setText(Item.getStridentidad_titular());
        textViewMontoPagar.setText("MONTO PROGRAMANDO : "+ NumberFormat.getCurrencyInstance().format(Integer.parseInt( Item.getIntmonto_pagar() )).replace("$","L") );

        return convertView;
    }

    public void changeAdapater(ArrayList<PagosProgramados> listExcluidos){
        this.listPagos = listExcluidos;
        notifyDataSetChanged();
    }

}
