package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;


import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
            primaryKeys = { "idDish", "idVariant" },
            foreignKeys = {
                    @ForeignKey(onDelete = CASCADE,
                            entity = DishModel.class,
                            parentColumns = "idDish",
                            childColumns = "idDish"),
                    @ForeignKey(onDelete = CASCADE,
                                entity = VariantModel.class,
                            parentColumns = "idVariant",
                            childColumns = "idVariant")
            })
public class DishVariantJoin {
    private int idDish;
    private int idVariant;

    public DishVariantJoin(int idDish, int idVariant){
        this.setIdDish(idDish);
        this.setIdVariant(idVariant);
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public int getIdVariant() {
        return idVariant;
    }

    public void setIdVariant(int idVariant) {
        this.idVariant = idVariant;
    }
}
