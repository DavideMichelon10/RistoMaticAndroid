package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.arch.lifecycle.ViewModel;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel{
    private List<Order> orders;
    public void init(){
        orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
