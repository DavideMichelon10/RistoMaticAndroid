package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;


import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.DishVariantJoin;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.DishVariantJoinDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModelDao;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;
    private List<VariantModel> variants;
    private List<DishModel> dishes;
    private List<DishVariantJoin> dishVariantJoinList;

    public OrderViewModel(Application application) {
        super(application);
        /*appDatabase = AppDatabase.getDatabase(this.getApplication());
        VariantModelDao variantModelDao = appDatabase.getVariantModelDao();
        DishModelDao dishModelDao = appDatabase.getDishModelDao();
        DishVariantJoinDao dishVariantJoinDao = appDatabase.getdishVariantJoinDao();

        variantModelDao.addVariant(new VariantModel(1,"Prosciutto", (float) 3.0));
        variantModelDao.addVariant(new VariantModel(2,"Uova", (float) 4.0));
        variantModelDao.addVariant(new VariantModel(3,"Tonno", (float) 2.0));

        dishModelDao.addDish(new DishModel(1, "Pizza Margherita", 4));
        dishModelDao.addDish(new DishModel(2, "Pizza Capricciosa", 6));
        dishModelDao.addDish(new DishModel(3, "Pizza tonno", 5));

        dishVariantJoinDao.insert(new DishVariantJoin(1,2));
        dishVariantJoinDao.insert(new DishVariantJoin(1,3));
        dishVariantJoinDao.insert(new DishVariantJoin(1,1));

        variants = appDatabase.getVariantModelDao().getAllVariants();
        dishVariantJoinList = appDatabase.getdishVariantJoinDao().getAllDishVariantJoin();*/
    }

    public void init(){
        //orders = new ArrayList<>();
        //categories = new ArrayList<>();
    }

}
