package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DishCategoryJoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDishCategory(DishCategoryJoin dishCategoryJoin);

    @Query("SELECT * FROM DishCategoryJoin")
    List<DishCategoryJoin> getAllDishCategoryJoin();

    @Query("DELETE FROM DishCategoryJoin")
    public void nukeTableDishCategory();
}
