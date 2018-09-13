package com.test.ristomatic.ristomaticandroid.MainPackage.TabLayoutDirectory;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Model.Room;
import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;


public class TablesFragment extends Fragment {
    private Room room;
    private LinearLayout linearLayout;
    public TablesFragment(){
        System.out.println("IN FRAGMENT");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("ON CR VIEW");
        View v =  inflater.inflate(R.layout.fragment_blank, container, false);
        linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);
        if(linearLayout.getChildCount()<1){
            linearLayout.addView(room.getMyRecyleView());
        }
        return v;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void init(List<Table> roomTables, RecyclerView recyclerView){
        System.out.println("PRIMA");
        room = new Room(roomTables, recyclerView);
        System.out.println("DENTRO");
        //linearLayout = (LinearLayout) getView().findViewById(R.id.linearLayout);
        //linearLayout.addView(room.getMyRecyleView());


    }
}
