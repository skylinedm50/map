package com.map_movil.map_movil.view.help;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.map_movil.map_movil.R;
import com.map_movil.map_movil.api.ApiConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpFragment extends Fragment{
    private View view;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private DownloadManager downloadManager;

    private static final int  MEGABYTE = 1024 * 1024;

    public HelpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.help_expandablelv, container, false);

        listView=(ExpandableListView) view.findViewById(R.id.exlvhelp);
        initData();
        listAdapter=new ExpandableListAdapter(view.getContext(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //TextView textView= view.findViewById(R.id.listitem);
                String text= (String) listView.getExpandableListAdapter().getChild(groupPosition,childPosition).toString();

                switch(text) {
                    case "Crear Solicitud y Carga de Imagen(web).":
                        openvid("https://youtu.be/w5fAyF1paVE");
                        break;
                    case "Video 2 (Sin link)":
                        break;
                    case "Manual MAP":
                        downloadpdf(ApiConfig.strURLDocument + ":82/areas/siap/MAP/AppRepository/manual_usuario_app_map.pdf");
                        break;
                    default:
                        break;
                }
                return false;

            }
        });

        return view;
    }
    /*private void openpdf(String name){
        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream archivo =assetManager.open(name);
            File file = createFileFromInputStream(archivo);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    private void downloadpdf(String urls){
        try {
            downloadManager=(DownloadManager)getContext().getSystemService(getContext().DOWNLOAD_SERVICE);
            Uri uri =Uri.parse(urls);
            DownloadManager.Request request= new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            Long reference= downloadManager.enqueue(request);

        } catch (Throwable e) {
            Log.e("Abhan", "Error: " + e);
        }
        Log.i("Abhan", "Check Your File.");
    }


    private void openvid(String urls){
        Intent httpIntent = new Intent(Intent.ACTION_VIEW);
        httpIntent.setData(Uri.parse(urls));

        startActivity(httpIntent);
    }

    private File createFileFromInputStream(InputStream inputStream) {

        try{
            File f = new File(getContext().getExternalFilesDir(""),"PDF_Test.pdf");
            OutputStream outputStream = new FileOutputStream(f);
            byte[] buffer = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            //Logging exception
        }

        return null;
    }
    private void initData(){
        listDataHeader = new ArrayList<>();
        listHash=new HashMap<>();

        listDataHeader.add("Video Tutoriales");
        listDataHeader.add("Manuales");


        List<String> videos=new ArrayList<>();
        videos.add("Crear Solicitud y Carga de Imagen(web).");

        List<String> manuales=new ArrayList<>();
        manuales.add("Manual MAP");


        listHash.put(listDataHeader.get(0),videos);
        listHash.put(listDataHeader.get(1),manuales);

    }
}
