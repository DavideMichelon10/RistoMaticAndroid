package com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;

import java.util.List;

@Entity
public class DishModel {
    @PrimaryKey
    private int idDish;
    @ColumnInfo(name = "dish_name")
    private String dishName;
    @ColumnInfo(name = "dish_price")
    private float dishPrice;

    public DishModel(int idDish, String dishName, float dishPrice) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
    }

    public DishModel(int idDish, String dishName, float dishPrice, List<VariantModel> variants) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
    }

    public int getIdDish() {
        return idDish;
    }

    public String getDishName() {
        return dishName;
    }

    public float getDishPrice() {
        return dishPrice;
    }
}
