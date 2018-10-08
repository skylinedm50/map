package com.map_movil.map_movil.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.FrameLayout;

import com.map_movil.map_movil.R;

public class BroadCastInternet extends BroadcastReceiver {
    private static View view;
    public static boolean bolSubscribe = false;
    public static boolean isConnected;
    private static ConnectivityManager connectivityManager;
    private static FrameLayout frameLayout;
    private static NetworkInfo networkInfo;

    @Override
    public void onReceive(Context context, Intent intent) {
        showMessageConectionInternet(context);
    }

    public static void subscribeForMessageInternet(Context context, View viewClassSubscribe){
        view = viewClassSubscribe;
        bolSubscribe = true;
        showMessageConectionInternet(context);
    }

    private static void showMessageConectionInternet(Context context){
        isConnected = false;

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        try {
            frameLayout = view.findViewById(R.id.FragmentLayoutBottomMessage);
            if (networkInfo == null) {
                if (bolSubscribe) {
                    frameLayout.setVisibility(View.VISIBLE);
                }
            } else {
                if (bolSubscribe) {
                    frameLayout.setVisibility(View.GONE);
                }
                isConnected = true;
            }
        }catch (Exception e){
            if (networkInfo != null) {
                isConnected = true;
            }
        }
    }
}
