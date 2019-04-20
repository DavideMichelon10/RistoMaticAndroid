package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModelDao;


@Database(entities = {VariantModel.class, DishModel.class,CategoryModel.class, CategoryVariantJoin.class, DishCategoryJoin.class} , version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context){
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ristoClient_db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public abstract DishModelDao getDishModelDao();
    public abstract VariantModelDao getVariantModelDao();
    public abstract CategoryModelDao getCategoryModelDao();
    public abstract CategoryVariantJoinDao getCategoryVariantJoinDao();
    public abstract DishCategoryJoinDao getDishCategoryJoinDao();

}
