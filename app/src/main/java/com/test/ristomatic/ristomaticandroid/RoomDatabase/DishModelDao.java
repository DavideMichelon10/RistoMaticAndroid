package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Dish;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DishModelDao {
    //solo piatti
    @Query("select * from DishModel")
    List<DishModel> getAllDishes();

    //piatti con varianti
    @Query("select * from DishModel ")
    List<DishWithVariants> getAllDishesWithVariants();

    @Insert(onConflict = REPLACE)
    void addDish(DishModel dishModel);
}
