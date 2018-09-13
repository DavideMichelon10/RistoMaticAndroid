package com.test.ristomatic.ristomaticandroid.Model;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;

import java.util.List;

public class Room {
    RoomAdapter roomAdapter;
    RecyclerView myRecyleView;
    Context context;
    public static int index;
    public Room(List<Table> newTableList, RecyclerView myRecyleView) {
        this.roomAdapter = new RoomAdapter(newTableList, context);
        this.roomAdapter = roomAdapter;
        this.myRecyleView = myRecyleView;
        this.context = ContextApplication.getAppContext();
        this.myRecyleView.setAdapter(this.roomAdapter);
        this.myRecyleView.setLayoutManager(new GridLayoutManager(ContextApplication.getAppContext(), 3));
        this.myRecyleView.setId(index++);
    }

    public RoomAdapter getRoomAdapter() {
        return roomAdapter;
    }

    public RecyclerView getMyRecyleView() {
        return myRecyleView;
    }

    public void setRoomAdapter(RoomAdapter roomAdapter) {
        this.roomAdapter = roomAdapter;
    }

    public void setMyRecyleView(RecyclerView myRecyleView) {
        this.myRecyleView = myRecyleView;
    }

    public void addTable(Table newTable){
        for (int i = 0; i< roomAdapter.tables.size(); i++){

            if(i!= roomAdapter.tables.size()-1 && i!= 0){
                System.out.println("NEL addTable IF: " + i);
                if (newTable.getIdTable() < roomAdapter.tables.get(i+1).getIdTable() && newTable.getIdTable() > roomAdapter.tables.get(i-1).getIdTable()){
                    roomAdapter.tables.add(i, newTable);
                    break;
                }
            }
            else if(i == 0)
            {
                System.out.println("NEL addTable ELSE IF: " + i);
                if (newTable.getIdTable() < roomAdapter.tables.get(i).getIdTable()){
                    roomAdapter.tables.add(i, newTable);
                    break;
                }
            }
            else {
                System.out.println("NEL addTable ELSE: " + i);
                if (newTable.getIdTable() > roomAdapter.tables.get(i).getIdTable())
                    roomAdapter.tables.add(newTable);
            }
        }
    }

    public void setTableList(List<Table> newTableList){
        roomAdapter.tables = newTableList;
    }
}