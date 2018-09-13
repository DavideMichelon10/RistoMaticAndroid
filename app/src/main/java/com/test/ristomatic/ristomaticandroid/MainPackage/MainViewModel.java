package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.TabLayoutDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.TabLayoutDirectory.TablesFragment;
import com.test.ristomatic.ristomaticandroid.Model.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;

    public void init(MainRepository mainRepository, PagerAdapter pagerAdapter) {
        this.mainRepository = mainRepository;
        //mainRepository.getTablesRooms(new VolleyCallback() {
          //  @Override
            //public void onSuccess(JSONArray result) {
                JSONArray jsonArray;
                String s = "[[{idTable=1, state=Occupato},{idTable=5, state=libero}],[{idTable=6, state=libero}],[{idTable=6, state=libero}],[{idTable=6, state=libero}]]";
                try {
                    jsonArray = new JSONArray(s);
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                        List<Table> tablesRoom = new LinkedList<>();
                        for(int j=0; j<jsonArray1.length(); j++){
                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
                            Table table = new Gson().fromJson(jsonObject.toString(), Table.class);
                            tablesRoom.add(table);
                           // System.out.println(table.toString());
                            //popolare per prima volta lista tables, mettere progressDialog o qualcosa che attenda la fine
                        }
                        TablesFragment tablesFragment = new TablesFragment();
                        pagerAdapter.rooms.add(tablesFragment);
                        tablesFragment.init(tablesRoom, new RecyclerView(ContextApplication.getAppContext()));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            //}
        //});
    }

    public void getTable(){
        //mainRepository.getTables(new VolleyCallback() {
            //@Override
            //public void onSuccess(JSONArray result) {
                String s = "[{id=1, state=Occupato, idRoom=1},{id=1, state=libero, idRoom=1}]";
                JSONArray result = new JSONArray();

                try {
                    JSONArray jsonArray = new JSONArray(s);
                    result = jsonArray;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int i=0; i< result.length(); i++){
                    try {
                        JSONObject jsonObject = result.getJSONObject(i);
                        Table table = new Gson().fromJson(jsonObject.toString(), Table.class);
                       //if (table.hashCode() == getTables().get(0).getValue().hashCode()) {
                            System.out.println("TAVOLI UGUALI");
                        //}else if(table == null){
                            //getTables().get()
                        //}
                        //else{
                          //  getTables().get(i).getValue().setState(table.getState());
                       // }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

}
