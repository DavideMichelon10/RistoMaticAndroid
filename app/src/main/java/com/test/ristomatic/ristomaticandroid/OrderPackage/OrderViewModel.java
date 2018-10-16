package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Category;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Order;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel.Variant;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.DishModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.DishWithVariants;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.VariantModel;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;
    private List<VariantModel> variants;
    private List<DishModel> dishes;
    private List<DishWithVariants> dishesWithVariants;
    private List<Order> orders;
    private List<Category> categories;

    public OrderViewModel(Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        appDatabase.getDishModelDao().addDish(new DishModel(1, "Pizza margherita", (float)2.2));
        appDatabase.getDishModelDao().addDish(new DishModel(2, "Pizza Capricciosa", (float)2.2));
        appDatabase.getDishModelDao().addDish(new DishModel(3, "Pizza Tonno", (float)2.2));
        appDatabase.getDishModelDao().addDish(new DishModel(4, "Pizza 4Formaggi", (float)2.2));

        appDatabase.getVariantModelDao().addVariant(new VariantModel(1, "Prosciutto", 3, 1));
        appDatabase.getVariantModelDao().addVariant(new VariantModel(2, "Uova", 3, 1));
        appDatabase.getVariantModelDao().addVariant(new VariantModel(3, "Cipolla", 3, 1));

        variants = appDatabase.getVariantModelDao().getAllVariants();
        //dishes = appDatabase.getDishModelDao().getAllDishes();
        dishesWithVariants = appDatabase.getDishModelDao().getAllDishesWithVariants();
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
