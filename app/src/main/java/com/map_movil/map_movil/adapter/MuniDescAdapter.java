package com.map_movil.map_movil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.model.Hogar_Validar;

import java.util.List;

public class MuniDescAdapter extends BaseAdapter {
    private Context context;
    private List<Hogar_Validar> list;
    private int layout;

    public MuniDescAdapter(Context context, List<Hogar_Validar> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Hogar_Validar getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new ViewHolder();
            vh.Departamento = (TextView) convertView.findViewById(R.id.textViewDepto);
            vh.Municipio= (TextView) convertView.findViewById(R.id.textViewMuni);
            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        Hogar_Validar hp = list.get(position);
        vh.Departamento.setText(hp.getDesc_departamento());
        vh.Municipio.setText(hp.getDesc_municipio());

        // int numberOfSolicitudes = solicitud.getSoli
        return convertView;
    }
    public class ViewHolder
    {
        TextView Departamento;
        TextView Municipio;
    }
}
