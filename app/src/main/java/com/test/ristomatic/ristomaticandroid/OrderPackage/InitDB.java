package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.content.Context;

import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModelDao;

public class InitDB {

    private static AppDatabase appDatabase;
    private CategoryModelDao categoryModelDao;
    public DishModelDao dishModelDao;
    private VariantModelDao variantModelDao;

    public InitDB(Context context){
        setAppDatabase(AppDatabase.getDatabase(context));
        setCategoryModelDao(getAppDatabase().getCategoryModelDao());
        setDishModelDao(getAppDatabase().getDishModelDao());
        setVariantModelDao(getAppDatabase().getVariantModelDao());

    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static void setAppDatabase(AppDatabase appDatabase) {
        InitDB.appDatabase = appDatabase;
    }

    public DishModelDao getDishModelDao() {
        return dishModelDao;
    }

    public void setDishModelDao(DishModelDao dishModelDao) {
        this.dishModelDao = dishModelDao;
    }

    public CategoryModelDao getCategoryModelDao() {
        return categoryModelDao;
    }

    public void setCategoryModelDao(CategoryModelDao categoryModelDao) {
        this.categoryModelDao = categoryModelDao;
    }

    public VariantModelDao getVariantModelDao() {
        return variantModelDao;
    }

    public void setVariantModelDao(VariantModelDao variantModelDao) {
        this.variantModelDao = variantModelDao;
    }
}
