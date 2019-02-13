package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public class GraphicComponents {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;

    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public  GraphicComponents(){}

    public GraphicComponents(ViewPager viewPager, TabLayout tabLayout, PagerAdapter pagerAdapter){
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        this.pagerAdapter = pagerAdapter;
    }


    public void createView(int numberRooms){
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0; i<numberRooms; i++){
            tabLayout.getTabAt(i).setText("sala "+ (1+i));
        }
    }
}
