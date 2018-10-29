package com.map_movil.map_movil;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.map_movil.map_movil.Realm.RealmConfig;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;
import com.map_movil.map_movil.view.Quejas.QuejasHomeFragment;
import com.map_movil.map_movil.view.corresponsabilidad.CorresponsabilidadFragment;
import com.map_movil.map_movil.view.downloadData.DownloadDataFragment;
import com.map_movil.map_movil.view.descargar_validacion.DescargarValidacionFragment;
import com.map_movil.map_movil.view.informacionHogares.InformacionHogaresFragment;
import com.map_movil.map_movil.view.excluidos.ExcluidoFragment;
import com.map_movil.map_movil.view.login.LoginActivity;
import com.map_movil.map_movil.view.programados.ProgramadosFragment;
import com.map_movil.map_movil.view.reportes.ReportsFragment;
import com.map_movil.map_movil.view.sincronizar.SincronizarFragment;
import com.map_movil.map_movil.view.solicitudes.SolicitudHomeFragment;
import com.map_movil.map_movil.view.validar_hogares.ListarValidacionesFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textViewNombreUsuario;
    private SharedPreferences sharedPreferences;
    private LinearLayout linearLayoutContentMainHome;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private int intCodItemSelect;
    private IntentFilter interFilter;
    private BroadCastInternet broadCastInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayoutContentMainHome = findViewById(R.id.linearLayoutContentMainHome);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);

        textViewNombreUsuario = navigationView.getHeaderView(0).findViewById(R.id.nav_nom_usu);
        textViewNombreUsuario.setText(sharedPreferences.getString("nombre", "").toString());

        if(sharedPreferences.getInt("Sincronizar",0)==1){
            navigationView.getMenu().getItem(0).getSubMenu().getItem(2).setActionView(R.layout.sincro_notificacion);
        }

        interFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.broadCastInternet = new BroadCastInternet();
        registerReceiver(this.broadCastInternet  , interFilter);

        showToolbar("Inicio");
        MostarPermisos(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.broadCastInternet);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void MostarPermisos(Menu menu){
        try{
            JSONObject permisos  =  new JSONObject( this.sharedPreferences.getString("permisos","") );
            JSONArray jsonArray = permisos.getJSONArray("permisos");

            for (int i = 0; i < jsonArray.length(); i++) {
                     MenuItem item= navigationView.getMenu().findItem(
                             getResources().getIdentifier(jsonArray.get(i).toString(),"id",getPackageName())
                     );

                     item.setVisible(true);
                     item.setEnabled(true);
                }
        }
        catch(Exception e){ }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        ViewCompat.setElevation(findViewById(R.id.appBar), 8);

        if(id != intCodItemSelect) {
            BroadCastInternet.bolSubscribe = false;
            showToolbar("Inicio");
            showContentScreenHome(true);

            if (id == R.id.nav_soli) {
                showToolbar("Solicitudes");
                showContentScreenHome(false);
                if (intCodItemSelect != id) {
                    ViewCompat.setElevation(findViewById(R.id.appBar), 0);
                    SolicitudHomeFragment fragmentHomeSolitud = new SolicitudHomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, fragmentHomeSolitud).commit();
                }
            } else if (id == R.id.nav_reports) {
                showContentScreenHome(false);
                showToolbar("Reportes");
                if (intCodItemSelect != id) {
                    ReportsFragment reportsFragment = new ReportsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, reportsFragment).commit();
                }
            } else if (id == R.id.nav_logout) {
                RealmConfig realmConfig = new RealmConfig(getApplicationContext());
                realmConfig.deleteDataBase();
                sharedPreferences.edit().clear().apply();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            } else if (id == R.id.nav_download) {
                showContentScreenHome(false);
                showToolbar("Descargar Validaciones");
                if (intCodItemSelect != id) {
                    DescargarValidacionFragment fragment_descargar = new DescargarValidacionFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, fragment_descargar).commit();
                }
            } else if (id == R.id.nav_val) {
                showContentScreenHome(false);
                showToolbar("Validar Hogares");
                if (intCodItemSelect != id) {
                    ListarValidacionesFragment fragment_validar = new ListarValidacionesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, fragment_validar).commit();
                }
            } else if (id == R.id.nav_coresponsibility) {
                showContentScreenHome(false);
                showToolbar("Corresponsabilidad");
                if (intCodItemSelect != id) {
                    CorresponsabilidadFragment corresponsabilidadFragment = new CorresponsabilidadFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, corresponsabilidadFragment).commit();
                }
            } else if (id == R.id.nav_complaint_denunciation) {
                showContentScreenHome(false);
                showToolbar("Quejas y Denuncias");
                if (intCodItemSelect != id) {
                    ViewCompat.setElevation(findViewById(R.id.appBar), 0);
                    QuejasHomeFragment fragmentHomQuejas = new QuejasHomeFragment();

                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, fragmentHomQuejas).commit();
                }
            } else if (id == R.id.nav_inf_homes) {
                showContentScreenHome(false);
                showToolbar("Información Hogares");
                if (intCodItemSelect != id) {
                    InformacionHogaresFragment informacionHogaresFragment = new InformacionHogaresFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, informacionHogaresFragment).commit();
                }
            } else if (id == R.id.nav_config_download_data) {
                showContentScreenHome(false);
                showToolbar("Descargar datos");
                if (intCodItemSelect != id) {
                    DownloadDataFragment downloadDataFragment = new DownloadDataFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, downloadDataFragment).commit();
                }
            }else if(id == R.id.nav_config_synchronize_data){
                showContentScreenHome(false);
                showToolbar("Sincronizar datos locales");
                if (intCodItemSelect != id) {
                    SincronizarFragment sincronizarFragment = new SincronizarFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, sincronizarFragment).commit();
                }
            } else if (id == R.id.nav_notification) {
            } else if (id == R.id.nav_programmed) {
                showContentScreenHome(false);
                showToolbar("Programados");
                if (intCodItemSelect != id) {
                    ProgramadosFragment programadosFragment = new ProgramadosFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, programadosFragment).commit();
                }
            } else if (id == R.id.nav_excluded) {
                showContentScreenHome(false);
                showToolbar("Excluido");
                if (intCodItemSelect != id) {
                    ExcluidoFragment excluidoFragment = new ExcluidoFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, excluidoFragment).commit();
                }
            }
            cleanFragmmentViewGroup();
        }

        intCodItemSelect = id;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showContentScreenHome(boolean show){
        if(show == true){
            linearLayoutContentMainHome.setVisibility(View.VISIBLE);
        }else{
            linearLayoutContentMainHome.setVisibility(View.GONE);
        }
    }

    private void showToolbar(String strTitle){
        getSupportActionBar().setTitle(strTitle);
        toolbar.getMenu().clear();
    }

    private void cleanFragmmentViewGroup(){
        List<android.support.v4.app.Fragment> fragmentsList = getSupportFragmentManager().getFragments();
        for(android.support.v4.app.Fragment fragment: fragmentsList){
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

}
