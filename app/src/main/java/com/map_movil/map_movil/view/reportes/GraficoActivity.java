package com.map_movil.map_movil.view.reportes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.map_movil.map_movil.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import com.map_movil.map_movil.api.ApiConfig;
import com.map_movil.map_movil.model.EstadosCantidadSolicitudes;

public class GraficoActivity extends AppCompatActivity {
    PieChart pieChart;
    List<PieEntry> valsY;
    List<PieEntry> valsX;
    ArrayList<Integer> colors;
    PieDataSet set1;
    PieData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String NombreGrafico = getIntent().getExtras().getString("NombreGrafico");
        int NumGrafico = getIntent().getExtras().getInt("NumGrafico");

        this.setTitle(NombreGrafico);


        pieChart = findViewById(R.id.pieChart);
        pieChart.setDrawCenterText(true);

        pieChart.setHoleRadius(40f);
        pieChart.setRotationEnabled(true);
        pieChart.animateY(1400);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setUsePercentValues(false);
        pieChart.setTransparentCircleRadius(50f);


        final String url = ApiConfig.strURL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GraficoActivity.GetDataService service = retrofit.create(GraficoActivity.GetDataService.class);


        if(NumGrafico==0) {




            service.getAllData().enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                @Override
                public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                    ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result


                    valsY = new ArrayList<>();

                    int CantidadRealizada=CantidadSegunEstado("Realizada",arreglo);
                    int CantidadNoRealizada= TotalCantidadNoRealizada(arreglo);

                    valsY.add(new PieEntry(CantidadNoRealizada, "Realizadas"));
                    valsY.add(new PieEntry(CantidadRealizada, "No Realizadas"));


                    valsX = new ArrayList<>();

                    /*valsX.add("REALIZADAS");
                    valsX.add("NO REALIZADAS");*/


                    colors = new ArrayList<Integer>();
                    colors.add(ColorTemplate.VORDIPLOM_COLORS[3]);
                    colors.add(ColorTemplate.VORDIPLOM_COLORS[4]);


                    set1 = new PieDataSet(valsY, null);
                    set1.setSliceSpace(3f);
                    set1.setColors(colors);
                    set1.setValueTextSize(15f);
                    //set1.setValueFormatter();


                    data = new PieData(set1);


                    //data.setValueFormatter(new GraficoActivity.IntValueFormatter());


                    pieChart.setData(data);
                    pieChart.highlightValues(null);
                    pieChart.invalidate();

                }

                @Override
                public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable XXX) {
                    Log.d("mensaje", XXX.getMessage());
                }
            });

        }

        if(NumGrafico==1) {

            service.getAllData().enqueue(new Callback<ArrayList<EstadosCantidadSolicitudes>>() {
                @Override
                public void onResponse(Call<ArrayList<EstadosCantidadSolicitudes>> call, Response<ArrayList<EstadosCantidadSolicitudes>> response) {
                    ArrayList<EstadosCantidadSolicitudes> arreglo = response.body(); //result

                    valsY = new ArrayList<>();


                    valsY.add(new PieEntry(CantidadSegunEstado("Denegada", arreglo), "Denegada"));
                    valsY.add(new PieEntry(CantidadSegunEstado("En proceso", arreglo), "En proceso"));
                    valsY.add(new PieEntry(CantidadSegunEstado("Incompleta", arreglo), "Incompleta"));
                    valsY.add(new PieEntry(CantidadSegunEstado("Ingresada", arreglo), "Ingresada"));
                    valsY.add(new PieEntry(CantidadSegunEstado("Realizada",arreglo), "Realizada"));


                    valsX = new ArrayList<>();

                   /* valsX.add("DENEGADAS");
                    valsX.add("EN PROCESO");
                    valsX.add("INCOMPLETAS");
                    valsX.add("INGRESADAS");
                    valsX.add("REALIZADAS");*/

                    colors = new ArrayList<>();


                    colors.add(ColorTemplate.VORDIPLOM_COLORS[0]);
                    colors.add(ColorTemplate.VORDIPLOM_COLORS[1]);
                    colors.add(ColorTemplate.VORDIPLOM_COLORS[2]);
                    colors.add(ColorTemplate.VORDIPLOM_COLORS[3]);
                    colors.add(ColorTemplate.VORDIPLOM_COLORS[4]);


                    set1 = new PieDataSet(valsY, null);
                    set1.setSliceSpace(3f);
                    set1.setColors(colors);
                    set1.setValueTextSize(15f);
                    //set1.setValueFormatter(new IntValueFormatter());

                    data = new PieData(set1);
                    pieChart.setData(data);
                    pieChart.highlightValues(null);
                    pieChart.invalidate();
                }

                @Override
                public void onFailure(Call<ArrayList<EstadosCantidadSolicitudes>> call, Throwable XXX) {
                    Log.d("mensaje", XXX.getMessage());
                }

            });
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public int CantidadSegunEstado(String Estado, ArrayList<EstadosCantidadSolicitudes> ES){

        for(int i = 0; i < ES.size() ; i++){

            if(ES.get(i).getEstado().equals(Estado)){
                return ES.get(i).getCantidad();
            }

        }
        return 0;
    }
    public int TotalCantidadNoRealizada(ArrayList<EstadosCantidadSolicitudes> ES){
        int Total=0;
        for(int i = 0 ; i < ES.size() ; i++){
            if(!ES.get(i).getEstado().equals("Realizada")) {
                Total = ES.get(i).getCantidad() + Total;
            }
        }
        return Total;

    }

    public interface Servicio {

        public static final String BASE_URL = "http://192.168.90.23:3004/index.php/solicitudes/cifras/cantidad/estados";
        @GET("Estado")

        Call<EstadosCantidadSolicitudes> ListaEstadoSolicitud();
    }

    public interface GetDataService {

        @GET("solicitudes/cifras/cantidad/estados")
        Call< ArrayList<EstadosCantidadSolicitudes>>getAllData();
    }

    public class IntValueFormatter implements IValueFormatter {

       /* @Override
        public String getFormattedValue(float value) {
            return "" + ((int) value);
        }*/

        public IntValueFormatter(){

        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            int newValues = ((int) value);

            return Integer.toString(newValues);
        }
    }
}
