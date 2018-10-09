package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Category;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Order;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.VariantModel;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;
    private List<VariantModel> variants;

    private List<Order> orders;
    private List<Category> categories;

    public OrderViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        appDatabase.getVariantModelDao().addVariant(new VariantModel(1, "Prosciutto", 3));
        variants = appDatabase.getVariantModelDao().getAllVariants();
    }

    public void init(){
        orders = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public List<VariantModel> getVariants(){
        return variants;
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
