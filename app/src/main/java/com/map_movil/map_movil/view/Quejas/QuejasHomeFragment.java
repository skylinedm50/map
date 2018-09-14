package com.map_movil.map_movil.view.Quejas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.map_movil.map_movil.R;

public class QuejasHomeFragment extends Fragment
{

    private ViewPager viewPager;
    private Pager_Adapter pager_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_quejas , container , false);
        this.pager_adapter = new Pager_Adapter(getFragmentManager());
        this.viewPager = (ViewPager) view.findViewById(R.id.quejas_container);
        this.viewPager.setAdapter(this.pager_adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabsQuejas);
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewPager));

        FloatingActionButton FloatActButt = (FloatingActionButton) view.findViewById(R.id.floating_action);
        FloatActButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(view.getContext() , NuevaQuejaActivity.class);
                     view.getContext().startActivity(intent);
            }
        });

        return view;
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
