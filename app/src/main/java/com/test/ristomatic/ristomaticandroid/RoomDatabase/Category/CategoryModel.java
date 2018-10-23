package com.test.ristomatic.ristomaticandroid.RoomDatabase.Category;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CategoryModel {
    @PrimaryKey
    private int idCategory;
    private String categoryName;

    public CategoryModel(int idCategory, String categoryName){
        this.idCategory = idCategory; this.categoryName = categoryName;
    }
    public int getIdCategory() {
        return idCategory;
    }
    public String getCategoryName() {
        return categoryName;
    }

}
