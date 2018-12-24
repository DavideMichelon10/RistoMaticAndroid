package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;


import com.google.gson.Gson;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.OrderPackage.CategoryAndDishesAdapter.CategoriesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.CategoryAndDishesAdapter.DishesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModelDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private static AppDatabase appDatabase;
    private CategoriesAdapter adapterCategories;
    private static DishesAdapter adapterDishes;
    private CoursesAdapter coursesAdapter;
    private CategoryModelDao categoryModelDao;
    private static DishModelDao dishModelDao;
    private OrderRepository orderRepository;
    private Context context;

    //test
    private List<Course> courses;
    public OrderViewModel(Application application) {
        super(application);
        courses = new ArrayList<>();
        orderRepository = new OrderRepository();
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

    public void sendReport(){
        JSONArray report = new JSONArray();
        /*TODO: aggiungere idWaiter, data, tavolo e salta*/
        for(int i=0; i<CoursesAdapter.getCourses().size(); i++){
            Course course = CoursesAdapter.getCourses().get(i);
            Gson gson = new Gson();
            String json = gson.toJson(course);
            try {
                report.put(new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        orderRepository.sendReport(report, new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) { }
        });
    }
}
