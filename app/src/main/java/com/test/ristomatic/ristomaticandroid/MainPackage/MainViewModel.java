package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
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
import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.DishCategoryJoin;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.DishCategoryJoinDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.DishVariantJoin;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.DishVariantJoinDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModelDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainViewModel extends AndroidViewModel {
    //contiene le date degli ultimi updated delle tabelle 5 tab
    private SharedPreferences allDataUpdated;
    private AppDatabase appDatabase;
    //boolean utile quando è prima volta e deve inviare le due stringhe
    private MainRepository mainRepository;
    private static int numberRooms;
    private PagerAdapter pagerAdapter;

    public  MainViewModel(Application application){
        super(application);
        allDataUpdated = ContextApplication.getAppContext().getSharedPreferences("DateUpdated", MODE_PRIVATE);
        appDatabase = AppDatabase.getDatabase(this.getApplication());

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
        final JSONArray currentDates = new JSONArray();

        currentDates.put(allDataUpdated.getString("VariantDate","0"));
        currentDates.put(allDataUpdated.getString("DishDate","0"));
        currentDates.put(allDataUpdated.getString("CategoryDate","0"));
        currentDates.put(allDataUpdated.getString("DishVariantDate","0"));
        currentDates.put(allDataUpdated.getString("DishCategoryDate","0"));

        mainRepository.updateTablesDate(currentDates, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                for(int i=0; i<result.length(); i++){
                    try {
                        //controllare questo if
                        if(result.getJSONObject(i).length() > 0 ){
                            JSONObject jsonTable = result.getJSONObject(i);
                            updateTable(i, jsonTable);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    //viene passato indice e oggetto della tabella, eliminata e ripopolata
    private void updateTable(int index, JSONObject jsonTable) throws JSONException{
        SharedPreferences.Editor editor = allDataUpdated.edit();
        Gson gson = new Gson();
        switch (index){
            case 0:
                VariantModelDao variantModelDao = appDatabase.getVariantModelDao();
                variantModelDao.nukeTableVariant();
                for(int i =0; i<jsonTable.getJSONArray("table").length(); i++){
                    String variantInString = jsonTable.getJSONArray("table").get(i).toString();
                    VariantModel variantModel = gson.fromJson(variantInString, VariantModel.class);
                    variantModelDao.addVariant(variantModel);
                }
                editor.putString("VariantDate", jsonTable.getString("dataUpdated"));
                editor.commit();
                break;
            case 1:
                DishModelDao dishModelDao = appDatabase.getDishModelDao();
                dishModelDao.nukeTableDish();
                for(int i=0; i<jsonTable.getJSONArray("table").length(); i++){
                    String dishInString = jsonTable.getJSONArray("table").get(i).toString();
                    DishModel dishModel = gson.fromJson(dishInString, DishModel.class);
                    dishModelDao.addDish(dishModel);
                }
                editor.putString("DishDate", jsonTable.getString("dataUpdated"));
                editor.commit();
                break;
            case 2:
                CategoryModelDao categoryModelDao = appDatabase.getCategoryModelDao();
                categoryModelDao.nukeTableCategory();
                for(int i = 0; i< jsonTable.getJSONArray("table").length(); i++){
                    String categoryInString = jsonTable.getJSONArray("table").get(i).toString();
                    CategoryModel categoryModel = gson.fromJson(categoryInString, CategoryModel.class);
                    categoryModelDao.addCategory(categoryModel);
                }
                editor.putString("CategoryDate", jsonTable.getString("dataUpdated"));
                editor.commit();
                break;
            case 3:
                DishVariantJoinDao dishVariantJoinDao = appDatabase.getdishVariantJoinDao();
                dishVariantJoinDao.nukeTableDishVariant();
                for(int i=0; i<jsonTable.getJSONArray("table").length(); i++){
                    String dishVariantJoinInString = jsonTable.getJSONArray("table").get(i).toString();
                    DishVariantJoin dishVariantJoin = gson.fromJson(dishVariantJoinInString, DishVariantJoin.class);
                    dishVariantJoinDao.addDishVariant(dishVariantJoin);
                }
                editor.putString("DishVariantDate", jsonTable.getString("dataUpdated"));
                editor.commit();
                break;
            case 4:
                DishCategoryJoinDao dishCategoryJoinDao = appDatabase.getDishCategoryJoinDao();
                dishCategoryJoinDao.nukeTableDishCategory();
                for(int i=0; i<jsonTable.getJSONArray("table").length(); i++){
                    String dishCategoryJoinInString = jsonTable.getJSONArray("table").get(i).toString();
                    DishCategoryJoin dishCategoryJoin = gson.fromJson(dishCategoryJoinInString, DishCategoryJoin.class);
                    dishCategoryJoinDao.addDishCategory(dishCategoryJoin);
                }
                editor.putString("DishCategoryDate", jsonTable.getString("dataUpdated"));
                editor.commit();
                break;
            default:
                break;
        }
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
