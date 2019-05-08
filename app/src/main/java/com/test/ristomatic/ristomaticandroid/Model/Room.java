package com.test.ristomatic.ristomaticandroid.Model;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.RoomAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;

import java.util.List;

public class Room {
    private RoomAdapter roomAdapter;
    private RecyclerView roomRecyclerView;
    //statico per rendere l'id univoco?
    public static int index;


    public Room(List<Table> newTableList, RecyclerView roomRecyclerView, MainActivity mainActivityContext) {
        this.roomAdapter = new RoomAdapter(newTableList, mainActivityContext);
        this.roomRecyclerView = roomRecyclerView;
        this.roomRecyclerView.setAdapter(this.roomAdapter);
        this.roomRecyclerView.setLayoutManager(new GridLayoutManager(ContextApplication.getAppContext(), GlobalVariableApplication.getNumberColumnTables()));
        this.roomRecyclerView.setId(index++);
    }


    public RoomAdapter getRoomAdapter() {
        return roomAdapter;
    }

    public void setRoomAdapter(RoomAdapter roomAdapter) {
        this.roomAdapter = roomAdapter;
    }


    public RecyclerView getRoomRecyclerView() {
        return roomRecyclerView;
    }

    public void setRoomRecyclerView(RecyclerView roomRecyclerView) {
        this.roomRecyclerView = roomRecyclerView;
    }
}
