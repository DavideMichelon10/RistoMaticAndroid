package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.arch.lifecycle.ViewModel;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Category;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel{
    private List<Order> orders;
    private List<Category> categories;
    public void init(){
        orders = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
