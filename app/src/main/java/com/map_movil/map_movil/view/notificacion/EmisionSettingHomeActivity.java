package com.map_movil.map_movil.view.notificacion;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.map_movil.map_movil.R;

public class EmisionSettingHomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayoutItems;
    private FloatingActionButton floatingActionAdd;
    private ViewPager viewPagerTab;
    private EmisionSettingHomeActivity.SectionsPagerAdapter sectionsPagerAdapter;

    public EmisionSettingHomeActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_emisiones);
        tabLayoutItems = findViewById(R.id.tabLayoutItems);
        viewPagerTab = findViewById(R.id.viewPagerTab);
        floatingActionAdd = findViewById(R.id.floatingActionAdd);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        floatingActionAdd.setVisibility(View.GONE);

        tabLayoutItems.getTabAt(0).setText("NOTIFICACIÓN");
        tabLayoutItems.getTabAt(1).setText("MENSAJE");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPagerTab.setAdapter(sectionsPagerAdapter);
        viewPagerTab.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutItems));
        tabLayoutItems.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerTab));
    }

    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        public static Fragment newInstance(int sectionNumber){
            switch (sectionNumber){
                case 1:
                    return new NotificacionFragment();
                case 2:
                    return new MensajeFragment();
                default:
                    return new Fragment();
            }
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
            return EmisionSettingHomeActivity.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
