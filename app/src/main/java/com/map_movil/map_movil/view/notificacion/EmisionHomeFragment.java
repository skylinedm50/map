package com.map_movil.map_movil.view.notificacion;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.map_movil.map_movil.R;

public class EmisionHomeFragment extends Fragment {
    private View view;
    private LinearLayout linearLayoutTab;
    private ViewPager viewPagerTab;
    private LinearLayout linearLayoutRecyclerView;
        private RecyclerView recyclerViewEmisiones;
    private FloatingActionButton floatingActionAdd;
    public int intCodRol;
    private EmisionHomeFragment.SectionsPagerAdapter sectionsPagerAdapter;


    public EmisionHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_emisiones, container, false);
        linearLayoutTab = view.findViewById(R.id.linearLayoutTab);
        viewPagerTab = view.findViewById(R.id.viewPagerTab);
        linearLayoutRecyclerView = view.findViewById(R.id.linearLayoutRecyclerView);
        recyclerViewEmisiones = view.findViewById(R.id.recyclerViewEmisiones);
        floatingActionAdd = view.findViewById(R.id.floatingActionAdd);

        linearLayoutTab.setVisibility(selectVisibleByRol(true));
        linearLayoutRecyclerView.setVisibility(selectVisibleByRol(false));
        floatingActionAdd.setVisibility(selectVisibleByRol(true));
        sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        viewPagerTab.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutItems);
        viewPagerTab.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerTab));

        floatingActionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), EmisionSettingHomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private int selectVisibleByRol(Boolean bolIsCreate){
        switch (intCodRol){
            case 1://Admin
                return (bolIsCreate)? View.VISIBLE : View.GONE;
            default:
                return (!bolIsCreate)? View.VISIBLE : View.GONE;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        public static Fragment newInstance(int sectionNumber){
            EmisionesFragment emisionesFragment = new EmisionesFragment();
            emisionesFragment.intTypeFind = sectionNumber;

            return  emisionesFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_home_emisiones, container, false);
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
       public SectionsPagerAdapter(FragmentManager fm){
           super(fm);
       }

       @Override
       public Fragment getItem(int position) {
           return EmisionHomeFragment.PlaceholderFragment.newInstance(position + 1);
       }

       @Override
       public int getCount() {
           return 2;
       }
   }
}
