package com.map_movil.map_movil.Realm;

import android.content.Context;
import android.content.SharedPreferences;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmConfig {
    private String strPassword;
    private String strNombre;
    private int intCodUser;
    private String strDate;
    private String strConsonant = "";
    private String strVowel = "";
    private byte[] byteKey = new byte[64];
    private Realm realm;
    private Context context;
    private RealmConfiguration config;

    public RealmConfig(Context context) {
        this.context = context;
        Realm.init(this.context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER", context.MODE_PRIVATE);
        strNombre = sharedPreferences.getString("nombre", "");
        intCodUser = sharedPreferences.getInt("codigo", 0);
        strDate = sharedPreferences.getString("fechaLogin", "");

        createPassword();
        config = new RealmConfiguration.Builder().encryptionKey(byteKey).build();

        this.realm = Realm.getInstance(config);
        //realm = Realm.getDefaultInstance();
    }

    private void createPassword(){
        strPassword = String.valueOf(strNombre.length());
        String[] arrStrDateLogin = strDate.split("-");
        strPassword = strPassword + arrStrDateLogin[0];

        for(int i = 0; i < strNombre.length(); i++){
            if(String.valueOf(strNombre.charAt(i) ).matches("[aeiouAEIOUáéíóúÁÉÍÓÚ]")){
               strVowel = strVowel + strNombre.charAt(i);
            }else{
                strConsonant = strConsonant + strNombre.charAt(i);
            }
        }
        strPassword = strPassword + strConsonant + String.valueOf(intCodUser) + strVowel + arrStrDateLogin[1] + arrStrDateLogin[2];
        convertPasswordToByte();
    }

    private void convertPasswordToByte(){
        byte[] byteTexto = strPassword.getBytes();
        for(int i = 1; i < 16; i++){
            byteKey[(i*4) - 1] = byteTexto[i];
            byteKey[(i*4) - 2] = byteTexto[i];
            byteKey[(i*4) - 3] = byteTexto[i];
            byteKey[(i*4) - 4] = byteTexto[i];
        }
    }

    public void deleteDataBase(){
        this.realm.beginTransaction();
        this.realm.deleteAll();
        this.realm.commitTransaction();
        this.realm.close();
        Realm.deleteRealm(config);
    }

    public Realm getRealm() {
        return realm;
    }

}

