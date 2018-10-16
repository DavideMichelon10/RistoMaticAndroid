package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;



@Database(entities = {VariantModel.class, DishModel.class, DishVariantJoin.class} , version = 1)
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
    public abstract DishVariantJoinDao getdishVariantJoinDao();

}
