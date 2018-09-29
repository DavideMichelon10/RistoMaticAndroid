package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.ViewModel;
import android.graphics.pdf.PdfDocument;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.TablesFragment;
import com.test.ristomatic.ristomaticandroid.Model.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainViewModel extends ViewModel {

    private MainRepository mainRepository;
    private static int numberRooms;
    PagerAdapter pagerAdapter;

    public static Lock lock;
    public void init(MainRepository mainRepository, final PagerAdapter pagerAdapter, final ViewPager viewPager, final TabLayout tabLayout) {
        lock = new ReentrantLock();
        this.mainRepository = mainRepository;
        this.pagerAdapter = pagerAdapter;
        mainRepository.getTablesRooms(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    for(int i=0; i<result.length(); i++){
                        JSONArray jsonArray1 = result.getJSONArray(i);
                        List<Table> tablesRoom = new LinkedList<>();
                        for(int j=0; j<jsonArray1.length(); j++){
                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
                            System.out.println("PRIMA DEL NEW GSON");
                            Table table = new Gson().fromJson(jsonObject.toString(), Table.class);
                            System.out.println(table.toString());
                            System.out.println("--------------------------------");
                            tablesRoom.add(table);
                            //popolare per prima volta lista tables, mettere progressDialog o qualcosa che attenda la fine
                        }
                        TablesFragment tablesFragment = new TablesFragment();
                        tablesFragment.setFragmentId(i);
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
    //Richiede le informazioni di tutti i tavoli
    public void getTablesUpToDate(final int room){
        mainRepository.getTablesInRoom(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    //cicla per ogni tavolo in sala
                    for(int j=0;j<result.length();j++)
                    {
                        Table newTable = new Gson().fromJson(result.getJSONObject(j).toString(), Table.class);

                        Table currentTable = pagerAdapter.getItem(room).getRoom().getRoomAdapter().getTables().get(j);
                        if (newTable.hashCode() != currentTable.hashCode()) {
                            pagerAdapter.getItem(room).getRoom().getRoomAdapter().getTables().get(j).setState(newTable.getState());
                            pagerAdapter.getItem(room).getRoom().getRoomAdapter().notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, room);
    }

    //Cambia lo stato del tavolo indicato nello stato specificato
    public void changeTableState(final int idTavolo, final String stato){
        mainRepository.changeTableState(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                System.out.println("STATO CAMBIATO");
            }
        }, idTavolo, stato);
    }
}
