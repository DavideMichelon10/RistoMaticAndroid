package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.List;

@Entity
public class DishModel {
    @PrimaryKey
    private int idDish;
    @ColumnInfo(name = "dish_name")
    private String dishName;
    @ColumnInfo(name = "dish_price")
    private float dishPrice;
    /*@Relation(parentColumn = "idDish", entityColumn = "idVariant", entity = DishModel.class)
    private List<VariantModel> variants;+*/

    public DishModel(int idDish, String dishName, float dishPrice) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
       // this.variants = new ArrayList<>();
    }

    public DishModel(int idDish, String dishName, float dishPrice, List<VariantModel> variants) {
        this.idDish = idDish;
        this.dishName = dishName;
        //this.variants = variants;
        this.dishPrice = dishPrice;
    }

    public int getIdDish() {
        return idDish;
    }

    public String getDishName() {
        return dishName;
    }

    /*public List<VariantModel> getVariants() {
        return variants;
    }*/

    public float getDishPrice() {
        return dishPrice;
    }
}
