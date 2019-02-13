package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.persistence.room.Dao;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.RoomAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.RoomFragment;
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
    private MainRepository mainRepository;
    private static int numberRooms;
    private PagerAdapter pagerAdapter;


    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }


    public  MainViewModel(Application application){
        super(application);
        allDataUpdated = ContextApplication.getAppContext().getSharedPreferences("DateUpdated", MODE_PRIVATE);
        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }


    //metodo chiamato una sola volta, inizilizza tutte le sale con i tavoli
    public void init(final MainRepository mainRepository, final PagerAdapter pagerAdapter, final MainActivity mainActivity) {
        this.mainRepository = mainRepository;
        setPagerAdapter(pagerAdapter);

        mainRepository.getTablesRooms(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    initializeTableFragments(result, pagerAdapter, mainActivity);
                    numberRooms = result.length();
                    MainActivity.createView(numberRooms);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initializeTableFragments(JSONArray result, PagerAdapter pagerAdapter, MainActivity mainActivity) throws JSONException {
        //chiamata sale e tavoli iniziali
        for(int i=0; i<result.length(); i++){
            JSONArray tablesRoomFromCallback = result.getJSONArray(i);
            List<Table> tablesRoom = new LinkedList<>();

            convertJsonToTableList(tablesRoomFromCallback, tablesRoom);

            RoomFragment roomFragment = new RoomFragment();
            roomFragment.setFragmentId(i);
            pagerAdapter.getRooms().add(roomFragment);
            roomFragment.init(tablesRoom, new RecyclerView(ContextApplication.getAppContext()), mainActivity);
        }
    }

    public void convertJsonToTableList(JSONArray tablesRoomFromCallback, List<Table> tablesRoom) throws JSONException {
        for(int j=0; j<tablesRoomFromCallback.length(); j++){
            JSONObject jsonObject = tablesRoomFromCallback.getJSONObject(j);
            Table table = new Gson().fromJson(jsonObject.toString(), Table.class);
            tablesRoom.add(table);
            //popolare per prima volta lista tables, mettere progressDialog o qualcosa che attenda la fine
        }
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
        System.out.println(currentDates.toString());
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
                    changeTableStateOnGraphic(result, room);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, room);
    }

    private void changeTableStateOnGraphic(JSONArray result, int room) throws JSONException {
        //cicla per ogni sala
        for(int j=0;j<result.length();j++) {
            Table newTable = new Gson().fromJson(result.getJSONObject(j).toString(), Table.class);
            RoomAdapter roomAdapter = pagerAdapter.getItem(room).getRoom().getRoomAdapter();
            Table currentTable = roomAdapter.getTables().get(j);
            if (newTable.hashCode() != currentTable.hashCode()) {
                roomAdapter.getTables().get(j).setState(newTable.getState());
                roomAdapter.notifyItemChanged(j);
            }
        }
    }

    //Cambia lo stato del tavolo indicato nello stato specificato
    public void changeTableState(final int idTable, final String state){
        JSONObject objectToSend = new JSONObject();
        try {
            objectToSend = createJsonToSend(idTable, state);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mainRepository.changeTableState(new VolleyCallbackObject() {
            @Override
            public void onSuccess(JSONObject result) { }
        },objectToSend);
    }

    public JSONObject createJsonToSend(int idTable, String state) throws JSONException {
        JSONObject valuesToChange = new JSONObject();
        valuesToChange.put("idTavolo", idTable);
        valuesToChange.put("stato", state);
        return valuesToChange;

    }
}
