package com.map_movil.map_movil.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.map_movil.map_movil.R;

import java.util.List;

public class AdpaterItemMunicipiosSaveDownload extends RecyclerView.Adapter<AdpaterItemMunicipiosSaveDownload.ViewHolder> {
    private List<String> stringList;
    private Context context;

    public AdpaterItemMunicipiosSaveDownload(List<String> stringList){
        this.stringList = stringList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewMunicipio;

        public ViewHolder(View itemView){
            super(itemView);
            textViewMunicipio = itemView.findViewById(R.id.textViewSomeField);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewMunicipio.setText(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void adapterDataChange(List<String> stringList){
        this.stringList = stringList;
        notifyDataSetChanged();
    }
}
