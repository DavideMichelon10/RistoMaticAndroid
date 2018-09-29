package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private int idDish;
    private String dishName;
    private List<Variant> variants;
    private float dishPrice;

    public Dish(int idDish, String dishName, float dishPrice) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.variants = new ArrayList<>();
    }

    public Dish(int idDish, String dishName, List<Variant> variants, float dishPrice) {
        this.idDish = idDish;
        this.dishName = dishName;
        this.variants = variants;
        this.dishPrice = dishPrice;
    }

    public int getIdDish() {
        return idDish;
    }

    public String getDishName() {
        return dishName;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public float getDishPrice() {
        return dishPrice;
    }

    public void setIdDish(int idDish) {
        this.idDish = idDish;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public void setDishPrice(float dishPrice) {
        this.dishPrice = dishPrice;
    }
}
