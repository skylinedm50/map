package com.map_movil.map_movil.Realm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
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
    private SharedPreferences sharedPreferences;
    private  RealmConfiguration config;

    public RealmConfig(Context context){
        this.context = context;
        Realm.init(this.context);
        sharedPreferences = context.getSharedPreferences("USER" , context.MODE_PRIVATE);
        strNombre = sharedPreferences.getString("nombre","");
        intCodUser = sharedPreferences.getInt("codigo",0);
        strDate = sharedPreferences.getString("fechaLogin", "");

        //createPassword();

        config = new RealmConfiguration.Builder()
                .encryptionKey(byteKey)
                .build();

        //this.realm = Realm.getInstance(config);
       realm = Realm.getDefaultInstance();
    }

    private void createPassword(){
        strPassword = String.valueOf(strNombre.length());
        String[] arrStrDateLogin = strDate.split("-");
        strPassword = strPassword + arrStrDateLogin[0];

        for(int i = 0; i < strNombre.length(); i++){
            if(String.valueOf(strNombre.charAt(i) ).matches("[aeiouAEIOU]") == true){
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
      try{
            PackageManager packageManager = context.getPackageManager();
            String PackageName = context.getPackageName();
            PackageInfo PackageInfo = packageManager.getPackageInfo(PackageName, 0);
            File[] files = new File(PackageInfo.applicationInfo.dataDir).listFiles();
            deleteFiles(files , 0, true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void deleteFiles(File[] files , int tipo , boolean main_contain_realm){

        for(int i = 0; i < files.length; i++){
            /**
             * Se verifica que la carpeta principal sea "Files", aqui es donde se contienen los archivos de realm.
             * El tipo sirve para identificar que los directorios encontrados en la variables "files" son sub-directorios de la carpeta "files"
             * */
            if((files[i].isDirectory() && files[i].getName().equals("files")) || tipo == 1 ){
                if(files[i].isDirectory()){
                    /// la variable "main_contain_realm" sirve para identificar que el subdirectorio en la variable "files"
                    /// es un carpeta de realm, por lo tanto hay que eliminarla, si no es una carpeta realm el subdirectorio no se elimina.
                    deleteFiles(files[i].listFiles() , 1 , files[i].getName().contains("realm"));
                }else{
                    // si el archivo en la variable "files" no es un archivo realm no se elimina.
                    // si la carpeta padre del archivo no es un subdirectorio de realm no se elimina.
                    if(files[i].getName().contains("realm") || main_contain_realm)
                        files[i].delete();
                }
                /// se eliminan los subdirectorios realm sin archivos dentro de ellos.
                if(files[i].isDirectory() && files[i].getName().contains("realm") && files[i].listFiles().length == 0)
                    files[i].delete();
            }
        }
    }


    public Realm getRealm() {
        return realm;
    }

}

