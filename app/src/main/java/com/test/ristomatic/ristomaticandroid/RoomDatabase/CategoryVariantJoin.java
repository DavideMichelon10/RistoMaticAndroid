package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;


import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
            primaryKeys = { "idCategory", "idVariant" },
            foreignKeys = {
                    @ForeignKey(onDelete = CASCADE,
                            entity = CategoryModel.class,
                            parentColumns = "idCategory",
                            childColumns = "idCategory"),
                    @ForeignKey(onDelete = CASCADE,
                                entity = VariantModel.class,
                            parentColumns = "idVariant",
                            childColumns = "idVariant")
            })
public class CategoryVariantJoin {
    private int idCategory;
    private int idVariant;

    public CategoryVariantJoin(int idCategory, int idVariant){
        this.setIdCategory(idCategory);
        this.setIdVariant(idVariant);
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idDish) {
        this.idCategory = idCategory;
    }

    public int getIdVariant() {
        return idVariant;
    }

    public void setIdVariant(int idVariant) {
        this.idVariant = idVariant;
    }
}
