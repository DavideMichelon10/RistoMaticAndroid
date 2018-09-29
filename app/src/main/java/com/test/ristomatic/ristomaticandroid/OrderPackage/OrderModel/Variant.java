package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel;

public class Variant {
    private int idVariant;
    private String variantName;
    private float variantPrice;

    public Variant(int idVariant, String variantName, float variantPrice) {
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

    public void setIdVariant(int idVariant) {
        this.idVariant = idVariant;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public void setVariantPrice(float variantPrice) {
        this.variantPrice = variantPrice;
    }
}
