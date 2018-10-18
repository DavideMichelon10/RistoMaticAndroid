package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;

@Entity(
        primaryKeys = {"idDish", "idVariant"},
        foreignKeys = {
                @ForeignKey(entity = DishModel.class,
                    parentColumns = "idDish",
                    childColumns = "idDish"),
                @ForeignKey(entity = CategoryModel.class,
                    parentColumns = "idVariant",
                    childColumns = "idVariant")
        }
)
public class DishCategoryJoin {
    public final int idDish;
    public final int idCategory;

    public DishCategoryJoin(int idDish, int idCategory){
        this.idDish = idDish; this.idCategory = idCategory;
    }
}
