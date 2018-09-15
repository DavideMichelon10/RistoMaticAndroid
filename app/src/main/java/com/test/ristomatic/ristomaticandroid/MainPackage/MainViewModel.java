package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.ViewModel;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.RoomAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.TablesFragment;
import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;
    private static int numberRooms;
    public void init(MainRepository mainRepository, final PagerAdapter pagerAdapter, final ViewPager viewPager, final TabLayout tabLayout) {
        this.mainRepository = mainRepository;
        mainRepository.getTablesRooms(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    for(int i=0; i<result.length(); i++){
                        JSONArray jsonArray1 = result.getJSONArray(i);
                        List<Table> tablesRoom = new LinkedList<>();
                        for(int j=0; j<jsonArray1.length(); j++){
                            JSONObject jsonObject = jsonArray1.getJSONObject(j);

                            Table table = new Gson().fromJson(jsonObject.toString(), Table.class);
                            System.out.println(table.toString());
                            System.out.println("--------------------------------");
                            tablesRoom.add(table);
                            //popolare per prima volta lista tables, mettere progressDialog o qualcosa che attenda la fine
                        }
                        TablesFragment tablesFragment = new TablesFragment();
                        pagerAdapter.getRooms().add(tablesFragment);
                        tablesFragment.init(tablesRoom, new RecyclerView(ContextApplication.getAppContext()));

                    }
                    numberRooms = result.length();
                    viewPager.setAdapter(pagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                    for(int a=0; a<numberRooms; a++){
                        tabLayout.getTabAt(a).setText("sala "+ (1+a));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
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
