package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private List<Dish> dishes;
    private String categoryName;
    

    public Category(List<Dish> category, String categoryName) {
        this.dishes = category;
        this.categoryName = categoryName;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.dishes = new ArrayList<>();
    }

    public List<Dish> getCategory() {
        return dishes;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategory(List<Dish> category) {
        this.dishes = category;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
