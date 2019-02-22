package com.map_movil.map_movil.view.reportes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.R;

import static com.map_movil.map_movil.api.ApiConfig.strURLMap;

public class ReportMapActivity extends AppCompatActivity {
    private WebView webViewMap;
    private TextView tvAD,tvCT,tvAM,tvDM,tvCS,tvCD,tvBP,tvRP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_mapa);

        webViewMap = findViewById(R.id.webViewMap);
        this.tvAD   = findViewById(R.id.tvAD);
        this.tvCT   = findViewById(R.id.tvCT);
        this.tvAM   = findViewById(R.id.tvAM);
        this.tvDM   = findViewById(R.id.tvDM);
        this.tvCS   = findViewById(R.id.tvCS);
        this.tvCD   = findViewById(R.id.tvCD);
        this.tvBP   = findViewById(R.id.tvBP);
        this.tvRP   = findViewById(R.id.tvRP);
        String url = strURLMap + "/mapa/mapa.html";

        webViewMap.getSettings().setJavaScriptEnabled(true);
        webViewMap.getSettings().setDomStorageEnabled(true);
        tvAD.setText(Html.fromHtml("<b>AD:</b> Actualizacion de datos"));
        tvCT.setText(Html.fromHtml("<b>CT:</b> Cambio de Titular"));
        tvAM.setText(Html.fromHtml("<b>AM:</b> Agregar Miembros"));
        tvDM.setText(Html.fromHtml("<b>DM:</b> Desagregar Miembros"));
        tvCS.setText(Html.fromHtml("<b>CS:</b> Corrección de Sanción"));
        tvCD.setText(Html.fromHtml("<b>CD:</b> Cambio de Domicilio"));
        tvBP.setText(Html.fromHtml("<b>BP:</b> Baja del Programa"));
        tvRP.setText(Html.fromHtml("<b>RP:</b> Reactivación en el Programa"));

        ConnectivityManager mgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
                {
                    webViewMap.getSettings().setJavaScriptEnabled(true);
                    webViewMap.getSettings().setDomStorageEnabled(true);

                    webViewMap.getSettings().setAllowUniversalAccessFromFileURLs(true);
                    webViewMap.loadUrl(url);
                }
            }else {
                Toast.makeText(this, "Dispositivo sin Conexión a Internet",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Dispositivo sin Conexión a Internet",Toast.LENGTH_LONG).show();
        }

    }

}
