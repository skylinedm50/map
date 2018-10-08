package com.map_movil.map_movil.view.Quejas;

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

public class QuejasHomeFragment extends Fragment
{

    private ViewPager viewPager;
    private Pager_Adapter pager_adapter;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private View view;
    private SharedPreferences.Editor sharePrederencesEditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_quejas , container , false);
        this.pager_adapter = new Pager_Adapter(getFragmentManager());
        this.viewPager = (ViewPager) view.findViewById(R.id.quejas_container);
        this.viewPager.setAdapter(this.pager_adapter);


   //     navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabsQuejas);
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewPager));
        this.sharedPreferences = view.getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        this.sharePrederencesEditor = this.sharedPreferences.edit();

        FloatingActionButton FloatActButt = (FloatingActionButton) view.findViewById(R.id.floating_action);
        FloatActButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(view.getContext() , NuevaQuejaActivity.class);
                     view.getContext().startActivity(intent);
            }
        });
        BroadCastInternet.subscribeForMessageInternet(view.getContext(), view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedPreferences.getInt("Sincronizar",0)==1){
            navigationView = (NavigationView)this.getActivity().findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(0).getSubMenu().getItem(2).setActionView(R.layout.sincro_notificacion);
        }
    }

    public static class HolderTabs extends Fragment{

        public HolderTabs(){}

        public static Fragment CreateInstance(int number_section){
            QuejasFragment fragment = new QuejasFragment();

            switch (number_section){
                case 1:
                    fragment.codigo_accion = 1;
                    break;
                case 2 :
                    fragment.codigo_accion = 2;
                    break;
            }

            return  fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState)
        {
            return LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_quejas , container , false);
        }
    }


    public class Pager_Adapter extends FragmentPagerAdapter{

        public Pager_Adapter(FragmentManager fm) {  super(fm); }

        @Override
        public Fragment getItem(int position) {
            return QuejasHomeFragment.HolderTabs.CreateInstance(position+1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
