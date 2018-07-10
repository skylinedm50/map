package com.map_movil.map_movil.elemnts;

import android.content.Context;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.widget.TextView;

import com.map_movil.map_movil.R;

import java.util.ArrayList;

public class TabView {
    private TabLayout tabLayout;
    private TabItem tabItem;
    private Context context;

    private TextView textView;
    private TabLayout.Tab tab;


    public TabLayout TabView(Context context, ArrayList<String> arrayListItem){
        this.context = context;
        tabLayout = new TabLayout(this.context.getApplicationContext());

        createItem(arrayListItem);


        return tabLayout;
    }




    private void createItem(ArrayList<String> arrayListItem){
        int intCount = 0;
        for (String item: arrayListItem){
            intCount ++;
            tab = tabLayout.newTab();
            tab.setIcon(R.drawable.ic_playlist_add_check);
            tab.setText(item.toString());

            tabLayout.addTab(tab);





           /* textView = new TextView(context.getApplicationContext());
            textView.setText(item.toString());
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_playlist_add_check, 0, 0);
            tabLayout.add

            tabItem = new TabItem(this.context.getApplicationContext());
            tabItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tabItem.setId(intCount);
            tabItem.*/
        }
    }
}
