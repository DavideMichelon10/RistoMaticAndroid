package com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class VariantModel {
    @PrimaryKey
    private int idVariant;
    private String variantName;
    private float variantPrice;

    public VariantModel(int idVariant, String variantName, float variantPrice) {
        this.idVariant = idVariant;
        this.variantName = variantName;
        this.variantPrice = variantPrice;
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

}
