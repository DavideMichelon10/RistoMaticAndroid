package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.ristomatic.ristomaticandroid.Model.Room;
import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;


public class TablesFragment extends Fragment {
    private Room room;
    private LinearLayout linearLayout;
    private boolean bool = true;
    public TablesFragment(){
        System.out.println("IN FRAGMENT");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_blank, container, false);
        linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);
        linearLayout.addView(room.getMyRecyleView());

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("in pausa");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("in Resume");
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void init(List<Table> roomTables, RecyclerView recyclerView){
        room = new Room(roomTables, recyclerView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
