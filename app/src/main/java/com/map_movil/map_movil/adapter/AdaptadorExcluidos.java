package com.map_movil.map_movil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.PagosExcluido;

import java.util.ArrayList;

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
        TextView textViewNombre = convertView.findViewById(R.id.Nombre_Persona) ;
        TextView textViewCodhoga = convertView.findViewById(R.id.codhogar) ;
        TextView textViewcaserio = convertView.findViewById(R.id.caserio) ;
        TextView textViewrazon = convertView.findViewById(R.id.razon) ;
        LinearLayout divider         = (LinearLayout) convertView.findViewById(R.id.divider_excluidos);

        textViewNombre.setText(Item.getStrnombre_titular());
        textViewCodhoga.setText("CÓDIGO HOGAR : " + Item.getStrcodigo_hogar());
        textViewcaserio.setText("CASERIO : " + Item.getStrdesc_caserio());
        textViewrazon.setText("RAZÓN DE LA EXCLUSIÓN : " + Item.getStrrazon_exclusion());

        if(position == listExcluidos.size() -1 ){
            LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT ,1);
            layoutParams.setMargins(0,0,0,400);
            divider.setLayoutParams(layoutParams);
        }else{
            LinearLayout.LayoutParams  layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT , 1);
            layoutParams.setMargins(0,0,0,0);
            divider.setLayoutParams(layoutParams);
        }
        return convertView;
    }

    public void changeAdapater(ArrayList<PagosExcluido> listExcluidos){
        this.listExcluidos = listExcluidos;
        notifyDataSetChanged();
    }
}
