package com.map_movil.map_movil.view.reportes;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.map_movil.map_movil.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.map_movil.map_movil.model.Rendimiento;
import com.map_movil.map_movil.presenter.reportes.ReportesPresenter;
import com.map_movil.map_movil.presenter.reportes.ReportesPresenterImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReportRendimientoFragment extends Fragment implements ReportesView {

    private View view;
    private HorizontalBarChart horizontalBarChart;
    private ArrayList<BarEntry> yVals;
    private ReportesPresenter reportesPresenter;

    public ReportRendimientoFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        view = inflater.inflate(R.layout.fragment_desempeno,container,false);
        reportesPresenter = new ReportesPresenterImpl(this);
        getRendimiento();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getRendimiento();
    }

    @Override
    public void getRendimiento() {
        reportesPresenter.getRendimiento();
    }

    @Override
    public void showRendimiento(ArrayList<Rendimiento> arrayList) {
        horizontalBarChart = view.findViewById(R.id.horizontalBarChart);
        horizontalBarChart.animateXY(2000,2000);
        yVals = new ArrayList();
        ArrayList<String> stringArrayList = new ArrayList<>();
        int count = 0;
        for(Rendimiento rendimiento: arrayList){
         /*   if(rendimiento.getResueltas()==0){
                yVals.add(new BarEntry(count, new float[]{rendimiento.getIngresadas() - rendimiento.getResueltas()}));
            }else if(rendimiento.getIngresadas()==0){
                yVals.add(new BarEntry(count, new float[]{rendimiento.getResueltas()}));
            }else {*/
                yVals.add(new BarEntry(count, new float[]{rendimiento.getResueltas(), rendimiento.getIngresadas() - rendimiento.getResueltas()}));
           // }
            stringArrayList.add(rendimiento.getDepartamento());
            count ++;
        }
        BarDataSet dataSet = new BarDataSet(yVals, "");
        BarData data = new BarData(dataSet);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ColorTemplate.MATERIAL_COLORS[3]);
        colors.add(ColorTemplate.MATERIAL_COLORS[0]);

        String[] StackLabels = {"Resueltas","Ingresadas"};

        dataSet.setStackLabels(StackLabels);
     //   dataSet.setDrawValues(true);
     //   dataSet.setHighlightEnabled(false);
        dataSet.setValueFormatter(new MyBarValueFormatter(getContext()));

       // dataSet.setVisible(false);

        dataSet.setColors(colors);
        horizontalBarChart.setData(data);
        horizontalBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(stringArrayList));
        horizontalBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        horizontalBarChart.getXAxis().setEnabled(true);
        horizontalBarChart.getXAxis().setDrawGridLines(false);
        horizontalBarChart.getXAxis().setDrawAxisLine(false);
        horizontalBarChart.getAxisLeft().mAxisMinimum=0f;
        horizontalBarChart.getAxisRight().mAxisMinimum=0f;
      //  horizontalBarChart.getAxisLeft().setEnabled(false);
      //  horizontalBarChart.getAxisRight().setEnabled(false);
        horizontalBarChart.setDrawValueAboveBar(false); //mostrar sobre stackedbar
        horizontalBarChart.getXAxis().setLabelCount(17);
        horizontalBarChart.getXAxis().setTextSize(14f);
        horizontalBarChart.setExtraOffsets(11f,0,0,0);
        horizontalBarChart.setTouchEnabled(false);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.getLegend().setEnabled(true);
        horizontalBarChart.getLegend().setTextSize(15f);
        horizontalBarChart.getLegend().setFormSize(15f);
        horizontalBarChart.invalidate();

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
    }

    @Override
    public void showMessage(String strMessage) {

    }
}


class MyBarValueFormatter implements IValueFormatter {

    private DecimalFormat mFormat;
    private Context context;

    int toggle;
    float totalVal;

    public MyBarValueFormatter(Context context){
        toggle = 0;
        totalVal = 0;
        this.context = context;
        mFormat = new DecimalFormat("###,###,###,##0");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        if(toggle % 2 == 0){
            toggle++;
            totalVal =  value;
            
            if(totalVal==0){
                return "";
            }else {
                return " " + mFormat.format(totalVal);
            }
        }else{
            toggle++;
            if(value != 0) {
                totalVal = totalVal + value;
                if(totalVal==0){
                    return "";
                }else {
                    return " " + mFormat.format(totalVal);
                }
            }else{
                return "";
            }
        }
    }
}


