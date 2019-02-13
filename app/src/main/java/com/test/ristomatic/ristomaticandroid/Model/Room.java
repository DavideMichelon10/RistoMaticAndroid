package com.test.ristomatic.ristomaticandroid.Model;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.RoomAdapter;

import java.util.List;

public class Room {
    private final static int COLUMN_NUMBER = 3;
    private RoomAdapter roomAdapter;
    private RecyclerView myRecyleView;
    //statico per rendere l'id univoco?
    public static int index;
    public Room(List<Table> newTableList, RecyclerView myRecyleView, Context mainActivityContext) {
        this.roomAdapter = new RoomAdapter(newTableList, mainActivityContext);
        this.roomAdapter = roomAdapter;
        this.myRecyleView = myRecyleView;
        this.myRecyleView.setAdapter(this.roomAdapter);
        this.myRecyleView.setLayoutManager(new GridLayoutManager(ContextApplication.getAppContext(), COLUMN_NUMBER));
        this.myRecyleView.setId(index++);
    }

    public RoomAdapter getRoomAdapter() {
        return roomAdapter;
    }

    public void setRoomAdapter(RoomAdapter roomAdapter) {
        this.roomAdapter = roomAdapter;
    }


    public RecyclerView getMyRecyleView() {
        return myRecyleView;
    }

    public void setMyRecyleView(RecyclerView myRecyleView) {
        this.myRecyleView = myRecyleView;
    }
}
