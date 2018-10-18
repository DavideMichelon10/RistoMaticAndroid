package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;


import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;

@Entity(
            primaryKeys = { "idDish", "idVariant" },
            foreignKeys = {
                    @ForeignKey(entity = DishModel.class,
                            parentColumns = "idDish",
                            childColumns = "idDish"),
                    @ForeignKey(entity = VariantModel.class,
                            parentColumns = "idVariant",
                            childColumns = "idVariant")
            })
public class DishVariantJoin {
    public final int idDish;
    public final int idVariant;

    public DishVariantJoin(int idDish, int idVariant){
        this.idDish = idDish;
        this.idVariant = idVariant;
    }

}
