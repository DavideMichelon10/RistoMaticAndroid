package com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface VariantModelDao {

    @Query("select * from VariantModel")
    List<VariantModel> getAllVariants();

    @Insert(onConflict = REPLACE)
    void addVariant(VariantModel variantModel);

    @Query("DELETE FROM VariantModel")
    void nukeTableVariant();

    @Query("select VariantModel.variantName from VariantModel where idVariant = :idVariant")
    String getVariantName(int idVariant);
}
