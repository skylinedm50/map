package com.map_movil.map_movil.view.reportes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.reportes.ApiAdapterReportes;
import com.map_movil.map_movil.api.reportes.ApiServiceReportes;
import com.map_movil.map_movil.model.SolicitudesCantidadPorEstado;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportBarActivity extends AppCompatActivity {
    private BarChart barChart;
    private ArrayList ds1Vals,ds2Vals,ds3Vals,ds4Vals,ds5Vals,ds6Vals,ds7Vals,ds8Vals;
    private BarData data;
    private BarDataSet ds1,ds2,ds3,ds4,ds5,ds6,ds7,ds8;
    private TextView tvTitulo_Grafico;
    private String Nombre_Usuario;
    private ApiServiceReportes service;
    private ApiAdapterReportes da;
    private Date Fecha_Alta;
    private Date Fecha_Baja;
    private ArrayList<SolicitudesCantidadPorEstado> Solicitudes;
    private SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
    private TextView tvMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTitulo_Grafico = findViewById(R.id.tvTitulo_Grafico);
        barChart = findViewById(R.id.barChart);
        tvMensaje = findViewById(R.id.tvMensaje);
        da= new ApiAdapterReportes();
        service = da.getClientService();
        Nombre_Usuario = getIntent().getExtras().getString("Nombre_Usuario");

        Func_Generar_Grafico();
    }

    private void Func_Generar_Grafico(){

        tvTitulo_Grafico.setText("Días que toma en dar respuesta a las Solicitudes");
        barChart.clear();
        barChart.animateXY(1500,1500);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getLegend().setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        barChart.setDrawBorders(false);
        barChart.getAxisLeft().setTextSize(13f);
        barChart.getDescription().setText("*Días en promedio");
        barChart.setVerticalScrollBarEnabled(true);

        ds1Vals = new ArrayList();
        ds2Vals = new ArrayList();
        ds3Vals = new ArrayList();
        ds4Vals = new ArrayList();
        ds5Vals = new ArrayList();
        ds6Vals = new ArrayList();
        ds7Vals = new ArrayList();
        ds8Vals = new ArrayList();

        service.getSolicitudesRealizadas().enqueue(new Callback<ArrayList <SolicitudesCantidadPorEstado>>() {

            @Override
            public void onResponse(Call<ArrayList <SolicitudesCantidadPorEstado>> call, Response<ArrayList <SolicitudesCantidadPorEstado>> response) {

                Solicitudes = response.body();

                ds1Vals.add(new BarEntry(0,Func_Prom_Actualizar_Datos(Solicitudes)));
                ds2Vals.add(new BarEntry(1,Func_Prom_Cambio_Titular(Solicitudes)));
                ds3Vals.add(new BarEntry(2,Func_Prom_Agregar_Miembro(Solicitudes)));
                ds4Vals.add(new BarEntry(3,Func_Prom_Desagregar_Miembros(Solicitudes)));
                ds5Vals.add(new BarEntry(4,Func_Prom_Cambio_Domicilio(Solicitudes)));
                ds6Vals.add(new BarEntry(5,Func_Prom_Baja_Programa(Solicitudes)));
                ds7Vals.add(new BarEntry(6,Func_Prom_Reactivar_Programa(Solicitudes)));
                ds8Vals.add(new BarEntry(7,Func_Prom_Correccion_Sancion(Solicitudes)));


                ds1 = new BarDataSet(ds1Vals,"Actualizar Datos");
                ds1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
                ds1.setValueTextSize(15f);
                ds1.setValueFormatter(new IntValueFormatter());

                ds2 = new BarDataSet(ds2Vals,"Cambio de Titular");
                ds2.setColor(ColorTemplate.MATERIAL_COLORS[1]);
                ds2.setValueTextSize(15f);
                ds2.setValueFormatter(new IntValueFormatter());

                ds3 = new BarDataSet(ds3Vals,"Agregar Miembro");
                ds3.setColor(ColorTemplate.MATERIAL_COLORS[2]);
                ds3.setValueTextSize(15f);
                ds3.setValueFormatter(new IntValueFormatter());

                ds4 = new BarDataSet(ds4Vals,"Desagregar Miembro");
                ds4.setColor(ColorTemplate.MATERIAL_COLORS[3]);
                ds4.setValueTextSize(15f);
                ds4.setValueFormatter(new IntValueFormatter());

                ds5 = new BarDataSet(ds5Vals,"Cambio de Domicilio");
                ds5.setColor(ColorTemplate.COLORFUL_COLORS[0]);
                ds5.setValueTextSize(15f);
                ds5.setValueFormatter(new IntValueFormatter());

                ds6 = new BarDataSet(ds6Vals,"Baja del Programa");
                ds6.setColor(ColorTemplate.COLORFUL_COLORS[1]);
                ds6.setValueTextSize(15f);
                ds6.setValueFormatter(new IntValueFormatter());

                ds7 = new BarDataSet(ds7Vals,"Reactivación al Programa");
                ds7.setColor(ColorTemplate.COLORFUL_COLORS[4]);
                ds7.setValueTextSize(15f);
                ds7.setValueFormatter(new IntValueFormatter());

                ds8 = new BarDataSet(ds8Vals,"Corrección de Sanción");
                ds8.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
                ds8.setValueTextSize(15f);
                ds8.setValueFormatter(new IntValueFormatter());

                data = new BarData(ds1,ds2,ds3,ds4,ds5,ds6,ds7,ds8);

                barChart.setData(data);//

                tvMensaje.setText("*Una solicitud podría pertenecer a varios campos");
            }

            @Override
            public void onFailure(Call <ArrayList <SolicitudesCantidadPorEstado>> call, Throwable t) {
                tvMensaje.setText("ERROR DE CONEXIÓN");
            }
        });
    }

    public class IntValueFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return ((int) value) + " " ;
        }
    }



    private int Func_Prom_Actualizar_Datos(ArrayList<SolicitudesCantidadPorEstado> Solicitudes) {
        long Suma_Total_Días = 0;
        int Promedio_Actualizar_Datos = 0;
        int n = 0;
        try {
            for (int i = 0; i < Solicitudes.size(); i++) {
                if (Solicitudes.get(i).getActualizar_Datos() == 1) {

                    Fecha_Alta = Formato.parse(Solicitudes.get(i).getFecha_Alta_Solicitud());
                    Fecha_Baja = Formato.parse(Solicitudes.get(i).getFecha_Baja_Solicitud());
                    long dif = Fecha_Baja.getTime() - Fecha_Alta.getTime();
                    dif = Math.abs(dif);
                    Suma_Total_Días = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS) + Suma_Total_Días;

                    if(dif == 0){Suma_Total_Días++;}
                    n++;
                }
            }
            Promedio_Actualizar_Datos = (int) Suma_Total_Días/n;
            return Promedio_Actualizar_Datos;
        } catch (Exception e) {
            return 0;
        }
    }
    private int Func_Prom_Cambio_Titular(ArrayList<SolicitudesCantidadPorEstado> Solicitudes){
        long Suma_Total_Días = 0;
        int Promedio_Actualizar_Datos = 0;
        int n = 0;
        try {
            for (int i = 0; i < Solicitudes.size(); i++) {
                if (Solicitudes.get(i).getCambio_Titular() == 1) {

                    Fecha_Alta = Formato.parse(Solicitudes.get(i).getFecha_Alta_Solicitud());
                    Fecha_Baja = Formato.parse(Solicitudes.get(i).getFecha_Baja_Solicitud());
                    long dif = Fecha_Baja.getTime() - Fecha_Alta.getTime();
                    dif = Math.abs(dif);
                    Suma_Total_Días = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS) + Suma_Total_Días;

                    if(dif == 0){Suma_Total_Días++;}
                    n++;
                }
            }
            Promedio_Actualizar_Datos = (int) Suma_Total_Días/n;
            return Promedio_Actualizar_Datos;
        } catch (Exception e) {
            return 0;
        }

    }

    private int Func_Prom_Agregar_Miembro(ArrayList<SolicitudesCantidadPorEstado> Solicitudes){
        long Suma_Total_Días = 0;
        int Promedio_Actualizar_Datos = 0;
        int n = 0;
        try {
            for (int i = 0; i < Solicitudes.size(); i++) {
                if (Solicitudes.get(i).getAgregar_Miembro() == 1) {

                    Fecha_Alta = Formato.parse(Solicitudes.get(i).getFecha_Alta_Solicitud());
                    Fecha_Baja = Formato.parse(Solicitudes.get(i).getFecha_Baja_Solicitud());
                    long dif = Fecha_Baja.getTime() - Fecha_Alta.getTime();
                    dif = Math.abs(dif);
                    Suma_Total_Días = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS) + Suma_Total_Días;

                    if(dif == 0){Suma_Total_Días++;}
                    n++;
                }
            }
            Promedio_Actualizar_Datos = (int) Suma_Total_Días/n;
            return Promedio_Actualizar_Datos;
        } catch (Exception e) {
            return 0;
        }
    }

    private int Func_Prom_Desagregar_Miembros(ArrayList<SolicitudesCantidadPorEstado> Solicitudes){
        long Suma_Total_Días = 0;
        int Promedio_Actualizar_Datos = 0;
        int n = 0;
        try {
            for (int i = 0; i < Solicitudes.size(); i++) {
                if (Solicitudes.get(i).getDesagregar_Miembros() == 1) {

                    Fecha_Alta = Formato.parse(Solicitudes.get(i).getFecha_Alta_Solicitud());
                    Fecha_Baja = Formato.parse(Solicitudes.get(i).getFecha_Baja_Solicitud());
                    long dif = Fecha_Baja.getTime() - Fecha_Alta.getTime();
                    dif = Math.abs(dif);
                    Suma_Total_Días = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS) + Suma_Total_Días;

                    if(dif == 0){Suma_Total_Días++;}
                    n++;
                }
            }
            Promedio_Actualizar_Datos = (int) Suma_Total_Días/n;
            return Promedio_Actualizar_Datos;
        } catch (Exception e) {
            return 0;
        }
    }

    private int Func_Prom_Cambio_Domicilio(ArrayList<SolicitudesCantidadPorEstado> Solicitudes){
        long Suma_Total_Días = 0;
        int Promedio_Actualizar_Datos = 0;
        int n = 0;
        try {
            for (int i = 0; i < Solicitudes.size(); i++) {
                if (Solicitudes.get(i).getCambio_Domicilio() == 1) {

                    Fecha_Alta = Formato.parse(Solicitudes.get(i).getFecha_Alta_Solicitud());
                    Fecha_Baja = Formato.parse(Solicitudes.get(i).getFecha_Baja_Solicitud());
                    long dif = Fecha_Baja.getTime() - Fecha_Alta.getTime();
                    dif = Math.abs(dif);
                    Suma_Total_Días = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS) + Suma_Total_Días;

                    if(dif == 0){Suma_Total_Días++;}
                    n++;
                }
            }
            Promedio_Actualizar_Datos = (int) Suma_Total_Días/n;
            return Promedio_Actualizar_Datos;
        } catch (Exception e) {
            return 0;
        }
    }

    private int Func_Prom_Baja_Programa(ArrayList<SolicitudesCantidadPorEstado> Solicitudes){
        long Suma_Total_Días = 0;
        int Promedio_Actualizar_Datos = 0;
        int n = 0;
        try {
            for (int i = 0; i < Solicitudes.size(); i++) {
                if (Solicitudes.get(i).getBaja_Programa() == 1) {

                    Fecha_Alta = Formato.parse(Solicitudes.get(i).getFecha_Alta_Solicitud());
                    Fecha_Baja = Formato.parse(Solicitudes.get(i).getFecha_Baja_Solicitud());
                    long dif = Fecha_Baja.getTime() - Fecha_Alta.getTime();
                    dif = Math.abs(dif);
                    Suma_Total_Días = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS) + Suma_Total_Días;

                    if(dif == 0){Suma_Total_Días++;}
                    n++;
                }
            }
            Promedio_Actualizar_Datos = (int) Suma_Total_Días/n;
            return Promedio_Actualizar_Datos;
        } catch (Exception e) {
            return 0;
        }
    }

    private int Func_Prom_Reactivar_Programa(ArrayList<SolicitudesCantidadPorEstado> Solicitudes){
        long Suma_Total_Días = 0;
        int Promedio_Actualizar_Datos = 0;
        int n = 0;
        try {
            for (int i = 0; i < Solicitudes.size(); i++) {
                if (Solicitudes.get(i).getReactivar_Programa() == 1) {

                    Fecha_Alta = Formato.parse(Solicitudes.get(i).getFecha_Alta_Solicitud());
                    Fecha_Baja = Formato.parse(Solicitudes.get(i).getFecha_Baja_Solicitud());
                    long dif = Fecha_Baja.getTime() - Fecha_Alta.getTime();
                    dif = Math.abs(dif);
                    Suma_Total_Días = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS) + Suma_Total_Días;

                    if(dif == 0){Suma_Total_Días++;}
                    n++;
                }
            }
            Promedio_Actualizar_Datos = (int) Suma_Total_Días/n;
            return Promedio_Actualizar_Datos;
        } catch (Exception e) {
            return 0;
        }
    }

    private int Func_Prom_Correccion_Sancion(ArrayList<SolicitudesCantidadPorEstado> Solicitudes){
        long Suma_Total_Días = 0;
        int Promedio_Actualizar_Datos = 0;
        int n = 0;
        try {
            for (int i = 0; i < Solicitudes.size(); i++) {
                if (Solicitudes.get(i).getCorreccion_Sancion() == 1) {

                    Fecha_Alta = Formato.parse(Solicitudes.get(i).getFecha_Alta_Solicitud());
                    Fecha_Baja = Formato.parse(Solicitudes.get(i).getFecha_Baja_Solicitud());
                    long dif = Fecha_Baja.getTime() - Fecha_Alta.getTime();
                    dif = Math.abs(dif);
                    Suma_Total_Días = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS) + Suma_Total_Días;

                    if(dif == 0){Suma_Total_Días++;}
                    n++;
                }
            }
            Promedio_Actualizar_Datos = (int) Suma_Total_Días/n;
            return Promedio_Actualizar_Datos;
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void Evt_Actualizar(View button){
        Func_Generar_Grafico();
    }

    public void Evt_ImprimirPDF(View button){
        try {

            Toast.makeText(this, "Abriendo PDF...", Toast.LENGTH_LONG).show();

            Document documento = new Document();
            File archivo = new File(getExternalFilesDir(""), "Tiempo_de_Respuesta_de_Solicitudes.pdf");
            FileOutputStream ficheroPdf = new FileOutputStream(archivo);
            documento.setPageSize(PageSize.LETTER);

            PdfWriter.getInstance(documento, ficheroPdf);
            Font boldFont = new Font(Font.FontFamily.UNDEFINED, 17, Font.BOLD);
            documento.open();
            documento.add(new Paragraph("GRÁFICO: "+ tvTitulo_Grafico.getText(),boldFont));
            documento.add(new Paragraph("GENERADO POR: "+ Nombre_Usuario));

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = Bitmap.createScaledBitmap(barChart.getChartBitmap(), 500, 500, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image myImg = Image.getInstance(stream.toByteArray());

            myImg.setAlignment(Image.ALIGN_CENTER);
            documento.add(myImg);
            documento.add(new Paragraph("*Una solicitud podría pertenecer a varios campos"));
            documento.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(archivo), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }catch (Exception e) {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
