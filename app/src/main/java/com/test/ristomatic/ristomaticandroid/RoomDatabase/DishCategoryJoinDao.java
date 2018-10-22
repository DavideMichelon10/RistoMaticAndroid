package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DishCategoryJoinDao {
    @Insert
    void insert(DishCategoryJoin dishCategoryJoin);

    @Query("SELECT * FROM DishCategoryJoin")
    List<DishCategoryJoin> getAllDishCategoryJoin();

    @Query("DELETE FROM DishCategoryJoin")
    public void nukeTableDishCategory();
}
