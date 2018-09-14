package com.map_movil.map_movil.Realm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.io.File;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmConfig {

    private String   Clave      = "";
    private String   Nombre     = "";
    private int      Usuario    = 0;
    private String   Fecha      = "13/01/2018";
    private String   Consonante = "";
    private String   Vocales    = "";
    private byte[]   Key        = new byte[64];
    private Realm    realm      = null;
    private Context  cntx       = null;

    public RealmConfig(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER" , context.MODE_PRIVATE);
        this.Nombre = sharedPreferences.getString("nombre","");
        this.Usuario = sharedPreferences.getInt("codigo",0);
        this.cntx = context;

        Realm.init(context);
        CrearClave();
        CrearLlave();

        RealmConfiguration config = new RealmConfiguration.Builder()
                .encryptionKey(this.Key)
                .build();

        this.realm = Realm.getInstance(config);
    }

    private void CrearClave(){

        this.Clave = String.valueOf( this.Nombre.length() );
        String[] Campos = this.Fecha.split("/");
        this.Clave = this.Clave + Campos[0];

        for(int i = 0; i < this.Nombre.length(); i++){

            if( String.valueOf( this.Nombre.charAt(i) ).matches("[aeiouAEIOU]") == true){
                this.Vocales = this.Vocales+this.Nombre.charAt(i);
            }else{
                this.Consonante = this.Consonante+this.Nombre.charAt(i);
            }
        }
        this.Clave = this.Clave+this.Consonante+String.valueOf(this.Usuario)+this.Vocales+Campos[1]+Campos[2];
    }

    private void CrearLlave(){

        byte[] ByteTexto = this.Clave.getBytes();
        for(int i=1; i<16; i++){
            this.Key[(i*4)-1] = ByteTexto[i];
            this.Key[(i*4)-2] = ByteTexto[i];
            this.Key[(i*4)-3] = ByteTexto[i];
            this.Key[(i*4)-4] = ByteTexto[i];
        }
    }

    public void EliminarBaseDatos(){

        this.realm.beginTransaction();
        this.realm.deleteAll();
        this.realm.commitTransaction();
      /*  try{
            PackageManager PackagenManager = this.cntx.getPackageManager();
            String PackageName = this.cntx.getPackageName();
            PackageInfo PackageInfo = PackagenManager.getPackageInfo(PackageName, 0);
            File[] files = new File(PackageInfo.applicationInfo.dataDir).listFiles();
            eliminar_archivos(files , 0, true);
        }
        catch(Exception e){
            e.printStackTrace();
        }*/
    }

    private void eliminar_archivos(File[] files , int tipo , boolean main_contain_realm){

        for(int i = 0; i < files.length; i++){
            /// se verifica que la carpeta principal sea "Files", aqui es donde se contienen los archivos de realm
            /// el tipo sirve para identificar que los directorios encontrados en la variables "files" son sub-directorios de la carpeta "files"

            if((files[i].isDirectory() && files[i].getName().equals("files")) || tipo == 1 ){

                if(files[i].isDirectory()){
                    /// la variable "main_contain_realm" sirve para identificar que el subdirectorio en la variable "files"
                    /// es un carpeta de realm, por lo tanto hay que eliminarla, si no es una carpeta realm el subdirectorio no se elimina.
                    eliminar_archivos(files[i].listFiles() , 1 , files[i].getName().contains("realm"));
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

