package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DishVariantJoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDishVariant(DishVariantJoin dishVariantJoin);

    @Query("SELECT * FROM DishVariantJoin")
    List<DishVariantJoin> getAllDishVariantJoin();

    @Query("DELETE FROM DishVariantJoin")
    public void nukeTableDishVariant();
}