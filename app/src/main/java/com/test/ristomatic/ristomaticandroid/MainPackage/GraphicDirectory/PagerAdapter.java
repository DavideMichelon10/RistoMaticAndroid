package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;
import java.util.List;

//contiene lista fragment presenti nella viewPager
public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<TablesFragment> rooms = new LinkedList<>();
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(TablesFragment tablesFragment){
        getRooms().add(tablesFragment);
    }
    @Override
    public TablesFragment getItem(int position) {
        return getRooms().get(position);
    }

    @Override
    public int getCount() {
        return getRooms().size();
    }

    public List<TablesFragment> getRooms() {
        return rooms;
    }

    public void setRooms(List<TablesFragment> rooms) {
        this.rooms = rooms;
    }

}
