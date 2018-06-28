package com.map_movil.map_movil;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.map_movil.map_movil.presenter.ubicaciones.UbicacionPresenterImpl;
import com.map_movil.map_movil.presenter.ubicaciones.UbicacionesPresenter;
import com.map_movil.map_movil.view.descargar_validacion.DescargarValidacionFragment;
import com.map_movil.map_movil.view.login.LoginActivity;
import com.map_movil.map_movil.view.solicitudes.SolicitudHomeFragment;
import com.map_movil.map_movil.view.ubicacion.UbicacionView;
import com.map_movil.map_movil.view.validar_hogares.ValidarHogaresFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Intent intent;
    private TextView textViewNombreUsuario;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        sharedPreferences = getApplicationContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        textViewNombreUsuario = navigationView.getHeaderView(0).findViewById(R.id.nav_nom_usu);
        textViewNombreUsuario.setText(sharedPreferences.getString("nombre", "").toString());

        showToolbar("Inicio");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        boolean boolSelectFragment = false;

        if (id == R.id.nav_soli) {
            showToolbar("Solicitudes");

            SolicitudHomeFragment fragmentHomeSolitud = new SolicitudHomeFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, fragmentHomeSolitud).commit();
            boolSelectFragment = true;
            /*intent = new Intent(this, SolicituActivity.class);
            startActivity(intent);*/

        } else if (id == R.id.nav_segui_soli) {
            Toast.makeText(getApplicationContext(), "En desarrollo...", Toast.LENGTH_SHORT).show();
            showToolbar("Seguimiento de solicitudes");
        }else if (id == R.id.nav_logout) {
            sharedPreferences.edit().clear().commit();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_download){
            DescargarValidacionFragment descargar_hogares = new DescargarValidacionFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, descargar_hogares)
                    .addToBackStack(null).commit();
            showToolbar("Descargar Validaciones");
        }else if(id == R.id.nav_val){
            ValidarHogaresFragment validar_hogares = new ValidarHogaresFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main_home, validar_hogares)
                    .addToBackStack(null).commit();
            showToolbar("Validar Hogares");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showToolbar(String strTitle){
        getSupportActionBar().setTitle(strTitle);
    }

}
