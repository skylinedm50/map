package com.map_movil.map_movil.view.solicitudes;

import android.content.Intent;
import android.os.Bundle;
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

public class SolicitudHomeFragment extends Fragment{
    private View view;
    private SolicitudHomeFragment.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    public SolicitudHomeFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_solicitud, container, false);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(view.getContext(), VerSolicitudActivity.class);
                intent.putExtra("intTipoOperacion", 1);


                //tabLayout.getTabAt(0).getCustomView().findViewById(R.id.)
                //int intPosicion = tabLayout.getSelectedTabPosition();
                // TabLayout tabLayoutTest = tab;




                startActivity(intent);
                mSectionsPagerAdapter.notifyDataSetChanged();
            }
        });

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
