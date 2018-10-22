package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        primaryKeys = {"idDish", "idCategory"},
        foreignKeys = {
                @ForeignKey(
                        onDelete = CASCADE,
                        entity = DishModel.class,
                    parentColumns = "idDish",
                    childColumns = "idDish"),
                @ForeignKey(
                        onDelete = CASCADE,
                        entity = CategoryModel.class,
                    parentColumns = "idCategory",
                    childColumns = "idCategory")
        }
)
public class DishCategoryJoin {
    public final int idDish;
    public final int idCategory;

    public DishCategoryJoin(int idDish, int idCategory){
        this.idDish = idDish; this.idCategory = idCategory;
    }
}
