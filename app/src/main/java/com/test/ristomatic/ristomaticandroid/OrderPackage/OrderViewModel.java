package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;


import com.test.ristomatic.ristomaticandroid.OrderPackage.RecyclerViewAdapter.CategoriesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.RecyclerViewAdapter.DishesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModelDao;

import java.util.ArrayList;
import java.util.List;

import static com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication.COURSES_NUMBER;

public class OrderViewModel extends AndroidViewModel {

    private static AppDatabase appDatabase;
    private CategoriesAdapter adapterCategories;
    private static DishesAdapter adapterDishes;
    private CoursesAdapter coursesAdapter;
    private CategoryModelDao categoryModelDao;
    private static DishModelDao dishModelDao;
    private Context context;

    //test
    private List<Course> courses;
    public OrderViewModel(Application application) {
        super(application);
        courses = new ArrayList<>();
    }

    public void init(Context context)
    {
        this.context = context;
        setAppDatabase(AppDatabase.getDatabase(this.getApplication()));
        setCategoryModelDao(getAppDatabase().getCategoryModelDao());
        setDishModelDao(getAppDatabase().getDishModelDao());
        setAdapterCategories(new CategoriesAdapter(getCategoryModelDao().getAllCategories()));
        setAdapterDishes(new DishesAdapter(getDishModelDao().getSelectedDishes(1), context));
        setCoursesAdapter(new CoursesAdapter(context, courses));

    }

    public CoursesAdapter getCoursesAdapter(){
        return coursesAdapter;
    }
    public void setCoursesAdapter(CoursesAdapter coursesAdapter){
        this.coursesAdapter = coursesAdapter;
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static void setAppDatabase(AppDatabase appDatabase) {
        OrderViewModel.appDatabase = appDatabase;
    }

    public CategoriesAdapter getAdapterCategories() {
        return adapterCategories;
    }

    public static DishesAdapter getAdapterDishes() {
        return adapterDishes;
    }

    public static void setAdapterDishes(DishesAdapter adapterDishes) {
        OrderViewModel.adapterDishes = adapterDishes;
    }

    public void setAdapterCategories(CategoriesAdapter adapterCategories) {
        this.adapterCategories = adapterCategories;
    }


    public CategoryModelDao getCategoryModelDao() {
        return categoryModelDao;
    }

    public void setCategoryModelDao(CategoryModelDao categoryModelDao) {
        this.categoryModelDao = categoryModelDao;
    }

    public  static DishModelDao getDishModelDao() {
        return dishModelDao;
    }

    public static void setDishModelDao(DishModelDao dishModelDao) {
        OrderViewModel.dishModelDao = dishModelDao;
    }
}
