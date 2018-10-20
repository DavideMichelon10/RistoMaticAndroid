package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.TablesFragment;
import com.test.ristomatic.ristomaticandroid.Model.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainViewModel extends ViewModel {
    //contiene le date degli ultimi updated delle tabelle 5 tab
    private SharedPreferences allDataUpdated;
    //boolean utile quando è prima volta e deve inviare le due stringhe
    private MainRepository mainRepository;
    private static int numberRooms;
    private PagerAdapter pagerAdapter;

    public  MainViewModel(){
        allDataUpdated = ContextApplication.getAppContext().getSharedPreferences("DateUpdated", MODE_PRIVATE);
    }
    //metodo chiamato una sola volta, inizilizza tutte le sale con i tavoli
    public void init(final MainRepository mainRepository, final PagerAdapter pagerAdapter, final ViewPager viewPager, final TabLayout tabLayout) {
        this.mainRepository = mainRepository;
        this.pagerAdapter = pagerAdapter;
        mainRepository.getTablesRooms(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    //chiamata sale e tavoli iniziali
                    for(int i=0; i<result.length(); i++){
                        JSONArray jsonArray1 = result.getJSONArray(i);
                        List<Table> tablesRoom = new LinkedList<>();
                        for(int j=0; j<jsonArray1.length(); j++){
                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
                            Table table = new Gson().fromJson(jsonObject.toString(), Table.class);
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
                    for(int i=0; i<numberRooms; i++){
                        tabLayout.getTabAt(i).setText("sala "+ (1+i));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //eseguire chiamata a server e confrontare data in locale e sul server
            }
        });
    }

    //invia valore delle 5 date con jsonArray(5 elementi, uno per ogni tabella), ricezione jsonArray
    // e dove non è nullo svuota e ripopola tabella
    public void updateablesDate(){
        JSONArray currentDates = new JSONArray();

        currentDates.put(allDataUpdated.getString("VariantDate",null));
        currentDates.put(allDataUpdated.getString("DishDate",null));
        currentDates.put(allDataUpdated.getString("DishVariantDate",null));
        currentDates.put(allDataUpdated.getString("CategoryDate",null));
        currentDates.put(allDataUpdated.getString("DishCategoryDate",null));



    }

    //Richiede le informazioni di tutti i tavoli
    public void getTablesUpToDate(final int room){
        mainRepository.getTablesInRoom(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    //cicla per ogni tavolo in sala
                    for(int j=0;j<result.length();j++) {
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
