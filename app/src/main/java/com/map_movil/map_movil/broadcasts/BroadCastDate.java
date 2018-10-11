package com.map_movil.map_movil.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.map_movil.map_movil.Realm.RealmConfig;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class BroadCastDate extends BroadcastReceiver {
    private  SharedPreferences.Editor sharedPreferencesEditor;
    private SharedPreferences sharedPreferences;
    private Date datePhone;
    private Date dateDeleteData;
    private long longCountDay;
    private SimpleDateFormat simpleDateFormat;
    private RealmConfig realmConfig;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        realmConfig = new RealmConfig(context);

        try{
            datePhone = simpleDateFormat.parse(simpleDateFormat.format(new Date().getTime()));
            dateDeleteData = simpleDateFormat.parse(sharedPreferences.getString("fechaDeleteData", ""));
            longCountDay = ((datePhone.getTime() - dateDeleteData.getTime()) / (1000*60*60*24));

            if(longCountDay >= 1 || longCountDay < 0){
                sharedPreferencesEditor.putString("fechaDeleteData", simpleDateFormat.format(datePhone.getTime()));
                sharedPreferencesEditor.commit();
                realmConfig.deleteDataBase();
            }
        }catch (Exception e){
            String message = e.getMessage();
        }
    }
}
