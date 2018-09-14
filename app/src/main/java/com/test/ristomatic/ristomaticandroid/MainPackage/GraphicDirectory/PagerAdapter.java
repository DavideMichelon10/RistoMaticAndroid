package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public List<TablesFragment> rooms = new LinkedList<>();
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(TablesFragment tablesFragment){
        rooms.add(tablesFragment);
    }
    @Override
    public Fragment getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public int getCount() {
        return rooms.size();
    }
}
