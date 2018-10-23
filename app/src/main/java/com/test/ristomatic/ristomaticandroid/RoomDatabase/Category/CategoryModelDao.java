package com.test.ristomatic.ristomaticandroid.RoomDatabase.Category;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryModelDao {
    @Query("select * from CategoryModel")
    List<CategoryModel> getAllCategories();

    @Insert(onConflict = REPLACE)
    void addCategory(CategoryModel categoryModel);

    @Query("DELETE FROM CategoryModel")
    public void nukeTableCategory();
}
