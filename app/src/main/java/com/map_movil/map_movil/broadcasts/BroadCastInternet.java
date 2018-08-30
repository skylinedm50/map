package com.map_movil.map_movil.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.map_movil.map_movil.R;

public class BroadCastInternet extends BroadcastReceiver {

    private  static final String Tag = "BroadCasts";

    @Override
    public void onReceive(Context context, Intent intent) {

        try{

            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            FrameLayout frameLayout = (FrameLayout) BroadCastImpl.getView().findViewById(R.id.Fl_BottomItem);

            if(networkInfo == null){
                if(BroadCastImpl.isSuscribir() && BroadCastImpl.getVisible() == 0){
                    frameLayout.setVisibility(View.VISIBLE);
                    BroadCastImpl.setVisible(1);
                }
            }else{
                if(BroadCastImpl.isSuscribir() && BroadCastImpl.getVisible() == 1 ){
                    frameLayout.setVisibility(View.GONE);
                    BroadCastImpl.setVisible(0);
                }
            }
        }
        catch (Exception e){}

    }

    public static void MostarMensajeInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        FrameLayout frameLayout = (FrameLayout) BroadCastImpl.getView().findViewById(R.id.Fl_BottomItem);

        if(networkInfo == null){
            if(BroadCastImpl.isSuscribir() && BroadCastImpl.getVisible() == 0){
                frameLayout.setVisibility(View.VISIBLE);
                BroadCastImpl.setVisible(1);
            }
        }else{
            if(BroadCastImpl.isSuscribir() && BroadCastImpl.getVisible() == 1 ){
                frameLayout.setVisibility(View.GONE);
                BroadCastImpl.setVisible(0);
            }
        }

    }


}
