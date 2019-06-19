package com.map_movil.map_movil.view.reportes;

import com.map_movil.map_movil.model.Rendimiento;

import java.util.ArrayList;

public interface ReportesView {
    void getRendimiento();
    void showRendimiento(ArrayList<Rendimiento> arrayList);
    void showMessage(String strMessage);
}
