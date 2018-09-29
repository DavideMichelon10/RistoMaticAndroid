package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryDish {
    List<Dish> dishesInCategory;
    String categoryName;

    public CategoryDish(List<Dish> dishesInCategory, String categoryName) {
        this.dishesInCategory = dishesInCategory;
        this.categoryName = categoryName;
    }

    public CategoryDish(String categoryName) {
        this.categoryName = categoryName;
        this.dishesInCategory = new ArrayList<>();
    }

    public List<Dish> getDishesInCategory() {
        return dishesInCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setDishesInCategory(List<Dish> dishesInCategory) {
        this.dishesInCategory = dishesInCategory;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
