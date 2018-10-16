package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class VariantModel {
    @PrimaryKey
    private int idVariant;
    private String variantName;
    private float variantPrice;
    private int idDish;

    public VariantModel(int idVariant, String variantName, float variantPrice, int idDish) {
        this.idVariant = idVariant;
        this.variantName = variantName;
        this.variantPrice = variantPrice;
        this.setIdDish(idDish);
    }

    public int getIdVariant() {
        return idVariant;
    }

    public String getVariantName() {
        return variantName;
    }

    public float getVariantPrice() {
        return variantPrice;
    }

    public int getIdDish() {
        return idDish;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }
}
