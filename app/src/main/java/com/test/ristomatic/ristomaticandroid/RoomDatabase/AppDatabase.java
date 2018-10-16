package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Dish;


@Database(entities = {VariantModel.class, DishModel.class} , version = 2)
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

    public abstract VariantModelDao getVariantModelDao();
    public abstract DishModelDao getDishModelDao();
}
