package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;
import java.util.List;

//contiene lista fragment presenti nella viewPager
public class PagerAdapter extends FragmentStatePagerAdapter {
    private List<RoomFragment> rooms = new LinkedList<>();
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(RoomFragment roomFragment){
        getRooms().add(roomFragment);
    }

    @Override
    public RoomFragment getItem(int position) {
        return getRooms().get(position);
    }

    @Override
    public int getCount() {
        return getRooms().size();
    }

    public List<RoomFragment> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomFragment> rooms) {
        this.rooms = rooms;
    }

}
