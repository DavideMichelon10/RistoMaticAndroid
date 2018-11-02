package com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DishModelDao {
    @Query("select * from DishModel")
    List<DishModel> getAllDishes();

    @Query("select * from DishModel " +
            "join DishCategoryJoin on DishModel.idDish = DishCategoryJoin.idDish " +
            "where idCategory = :idCategory")
    List<DishModel> getSelectedDishes(int idCategory);
    @Insert(onConflict = REPLACE)
    void addDish(DishModel dishModel);

    @Query("DELETE FROM DishModel")
    public void nukeTableDish();
}
