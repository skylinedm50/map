package com.map_movil.map_movil.view.reportes;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.map_movil.map_movil.R;

import java.util.ArrayList;


public class ReportsFragment extends Fragment {
    private View view;
    private SharedPreferences sharedPreferences;
    private int intCodUser;
    private String strNombreUser;
    private ArrayList<Report> arrayListReport;

    public ReportsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_reports, container, false);
        arrayListReport = new ArrayList<>();
        this.sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        this.intCodUser = this.sharedPreferences.getInt("codigo", 0);
        this.strNombreUser = this.sharedPreferences.getString("nombre", "");

        //arrayListReport.add(new Report("Estado de solicitudes", "Por realizacion"));
        //arrayListReport.add(new Report("Estado de solicitudes", "Por estado"));
        arrayListReport.add(new Report("Mis estados de solicitud", "Por realizacion"));
        arrayListReport.add(new Report("Mis estados de solicitud", "Por estado"));
        //arrayListReport.add(new Report("Tiempo de respuesta", "De solicitudes"));

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewReport);
        AdapterRecyclerReport adapterRecyclerReport = new AdapterRecyclerReport(arrayListReport);
        adapterRecyclerReport.setOnClickListener(new AdapterRecyclerReport.OnItemClickListener() {
            @Override
            public void onitemClick(int position) {
                if(position >= 0 && position <= 3){
                    String Nombre_Grafico = arrayListReport.get(position).getStrNombre()+ " " + arrayListReport.get(position).getStrDescripcion();
                    Intent intent = new Intent(view.getContext(), ReportCircularActivity.class);
                    intent.putExtra("Nombre_Grafico",Nombre_Grafico);
                    intent.putExtra("Codigo_Usuario", intCodUser);
                    intent.putExtra("Nombre_Usuario", strNombreUser);
                    startActivity(intent);
                }else if(position == 4){
                    Intent intent = new Intent(view.getContext(), ReportBarActivity.class);
                    intent.putExtra("Nombre_Usuario",strNombreUser);
                    startActivity(intent);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapterRecyclerReport);

        return this.view;
    }

    class Report {
        private String strNombre;
        private String strDescripcion;

        public Report(String strNombre, String strDescripcion) {
            this.strNombre = strNombre;
            this.strDescripcion = strDescripcion;
        }

        public String getStrNombre() {
            return strNombre;
        }

        public void setStrNombre(String strNombre) {
            this.strNombre = strNombre;
        }

        public String getStrDescripcion() {
            return strDescripcion;
        }

        public void setStrDescripcion(String strDescripcion) {
            this.strDescripcion = strDescripcion;
        }
    }

    static class AdapterRecyclerReport extends RecyclerView.Adapter<AdapterRecyclerReport.ViewHolder>{
        private ArrayList<Report> arrayListReport;
        private OnItemClickListener onClickListener;

       public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView textViewNombre;
            private TextView textViewDescripcion;

            public ViewHolder(View itemView, OnItemClickListener listener){
                super(itemView);

                this.textViewNombre = itemView.findViewById(R.id.textViewNombre);
                this.textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onClickListener != null){
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION){
                                onClickListener.onitemClick(position);
                            }
                        }
                    }
                });
            }
        }

        public interface OnItemClickListener{
            void onitemClick(int position);
        }

        public void setOnClickListener(OnItemClickListener onClickListener){
            this.onClickListener = onClickListener;
        }

        public AdapterRecyclerReport(ArrayList<Report> arrayListReport){
            this.arrayListReport = arrayListReport;
        }

        @NonNull
        @Override
        public AdapterRecyclerReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);

            return new ViewHolder(view, this.onClickListener);
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterRecyclerReport.ViewHolder holder, int position) {
            Report report = this.arrayListReport.get(position);
            holder.textViewNombre.setText(report.getStrNombre());
            holder.textViewDescripcion.setText(report.getStrDescripcion());
        }

        @Override
        public int getItemCount() {
            return arrayListReport.size();
        }
    }

}
