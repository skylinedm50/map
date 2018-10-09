package com.map_movil.map_movil.view.solicitudes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.map_movil.map_movil.R;
import com.map_movil.map_movil.broadcasts.BroadCastInternet;

public class SolicitudHomeFragment extends Fragment {

    private View view;
    private SolicitudHomeFragment.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharePrederencesEditor;

    public SolicitudHomeFragment(){ }

    @Override
    public void  onResume(){
        super.onResume();
        if(sharedPreferences.getInt("Sincronizar",0)==1){
            navigationView = (NavigationView)this.getActivity().findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(0).getSubMenu().getItem(2).setActionView(R.layout.sincro_notificacion);
        }
        BroadCastInternet.subscribeForMessageInternet(view.getContext(), view);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_solicitud, container, false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        this.sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        this.sharePrederencesEditor = this.sharedPreferences.edit();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(view.getContext(), ShowAddSolicitudActivity.class);
                intent.putExtra("intTipoOperacion", 1);
                startActivity(intent);
            }
        });

        BroadCastInternet.subscribeForMessageInternet(view.getContext(), view);
        return view;
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        public static Fragment newInstance(int sectionNumber) {
            SolicitudesFragment fragment = new SolicitudesFragment();
            fragment.intCodUser = 1;

            switch (sectionNumber){
                case 1:
                    fragment.strSimbolo ="resueltas";
                    break;
                case 2:
                    fragment.strSimbolo ="noResueltas";
                    break;
            }
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_home_solicitud, container, false);
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return SolicitudHomeFragment.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
