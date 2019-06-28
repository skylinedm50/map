package com.map_movil.map_movil.elemnts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permissions {

    private Context context;
    private Activity activity;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 5 ;

    public Permissions(Context cntx , Activity actv){
        this.context = cntx;
        this.activity = actv;
    }

    public boolean LocationPermissionsGranted(){

        if(ContextCompat.checkSelfPermission(this.context , Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this.activity , new String[]{Manifest.permission.ACCESS_FINE_LOCATION } , MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                return  false;
        }
        return true;
    }

}
