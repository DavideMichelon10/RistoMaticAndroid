package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class Category {
    List<Dish> category;
    String categoryName;
    

    public Category(List<Dish> category, String categoryName) {
        this.category = category;
        this.categoryName = categoryName;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.category = new ArrayList<>();
    }

    public List<Dish> getCategory() {
        return category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategory(List<Dish> category) {
        this.category = category;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
