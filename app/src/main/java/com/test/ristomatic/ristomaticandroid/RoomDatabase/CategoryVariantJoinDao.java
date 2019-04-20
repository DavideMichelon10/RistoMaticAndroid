package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface CategoryVariantJoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCategoryVariant(CategoryVariantJoin categoryVariantJoin);

    @Query("SELECT * FROM CategoryVariantJoin")
    List<CategoryVariantJoin> getAllCategoryVariantJoin();

    @Query("DELETE FROM CategoryVariantJoin")
    public void nukeTableDishVariant();
}
