package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.content.Context;

import com.test.ristomatic.ristomaticandroid.OrderPackage.CategoryAndDishesAdapter.CategoriesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.CategoryAndDishesAdapter.DishesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;

import java.util.List;


public class AdaptersContainer {
    private CategoriesAdapter categoriesAdapter;
    private DishesAdapter dishesAdapter;
    private CoursesAdapter coursesAdapter;

    public AdaptersContainer(){}

    public AdaptersContainer(Context context, List<Course> courses, InitDB initDB){
        setCategoriesAdapter(new CategoriesAdapter(initDB.getCategoryModelDao().getAllCategories()));
        setDishesAdapter(new DishesAdapter(initDB.getDishModelDao().getSelectedDishes(1), context));
        setCoursesAdapter(new CoursesAdapter(context, courses));
    }
    public CoursesAdapter getCoursesAdapter(){
        return coursesAdapter;
    }

    private void setCoursesAdapter(CoursesAdapter coursesAdapter){
        this.coursesAdapter = coursesAdapter;
    }
    public CategoriesAdapter getCategoriesAdapter() {
        return categoriesAdapter;
    }

    public DishesAdapter getDishesAdapter() {
        return dishesAdapter;
    }


    private void setDishesAdapter(DishesAdapter dishesAdapter) {
        this.dishesAdapter = dishesAdapter;
    }

    private void setCategoriesAdapter(CategoriesAdapter categoriesAdapter) {
        this.categoriesAdapter = categoriesAdapter;
    }

}
