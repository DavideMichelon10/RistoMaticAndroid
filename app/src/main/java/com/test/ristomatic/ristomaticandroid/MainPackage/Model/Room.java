package com.test.ristomatic.ristomaticandroid.MainPackage.Model;


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
    public static int index;
    private int idRoom;

    public Room(List<Table> newTableList, RecyclerView roomRecyclerView, int idRoom, MainActivity mainActivityContext) {
        this.roomAdapter = new RoomAdapter(newTableList, mainActivityContext);
        this.roomRecyclerView = roomRecyclerView;
        this.roomRecyclerView.setAdapter(this.roomAdapter);
        this.roomRecyclerView.setLayoutManager(new GridLayoutManager(ContextApplication.getAppContext(), GlobalVariableApplication.getNumberColumnTables()));
        this.roomRecyclerView.setId(index++);
        this.idRoom = idRoom;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public RoomAdapter getRoomAdapter() {
        return roomAdapter;
    }

    public RecyclerView getRoomRecyclerView() {
        return roomRecyclerView;
    }

}
