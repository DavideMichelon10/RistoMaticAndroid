package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.widget.Toast;


import com.google.gson.Gson;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;
import com.test.ristomatic.ristomaticandroid.LoginPackage.LoginViewModel;
import com.test.ristomatic.ristomaticandroid.OrderPackage.CategoryAndDishesAdapter.CategoriesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.CategoryAndDishesAdapter.DishesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.R;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModelDao;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModelDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private static AppDatabase appDatabase;
    private CategoriesAdapter adapterCategories;
    private static DishesAdapter dishedAdapter;
    private CoursesAdapter coursesAdapter;
    private CategoryModelDao categoryModelDao;
    private static DishModelDao dishModelDao;
    private OrderRepository orderRepository;
    private int tableId, seatsNumber;


    //test
    private List<Course> courses;
    public OrderViewModel(Application application) {
        super(application);
        courses = new ArrayList<>();
        orderRepository = new OrderRepository();
    }


    public void init(Context context, int tableId, int seatsNumber) {
        this.seatsNumber = seatsNumber;
        this.tableId = tableId;
        setAppDatabase(AppDatabase.getDatabase(this.getApplication()));
        setCategoryModelDao(getAppDatabase().getCategoryModelDao());
        setDishModelDao(getAppDatabase().getDishModelDao());
        setAdapterCategories(new CategoriesAdapter(getCategoryModelDao().getAllCategories()));
        setDishedAdapter(new DishesAdapter(getDishModelDao().getSelectedDishes(1), context));
        setCoursesAdapter(new CoursesAdapter(context, courses));

    }


    public CoursesAdapter getCoursesAdapter(){
        return coursesAdapter;
    }

    private void setCoursesAdapter(CoursesAdapter coursesAdapter){
        this.coursesAdapter = coursesAdapter;
    }


    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    private static void setAppDatabase(AppDatabase appDatabase) {
        OrderViewModel.appDatabase = appDatabase;
    }


    public CategoriesAdapter getAdapterCategories() {
        return adapterCategories;
    }

    public static DishesAdapter getDishedAdapter() {
        return dishedAdapter;
    }


    private static void setDishedAdapter(DishesAdapter dishedAdapter) {
        OrderViewModel.dishedAdapter = dishedAdapter;
    }

    private void setAdapterCategories(CategoriesAdapter adapterCategories) {
        this.adapterCategories = adapterCategories;
    }


    private CategoryModelDao getCategoryModelDao() {
        return categoryModelDao;
    }

    private void setCategoryModelDao(CategoryModelDao categoryModelDao) {
        this.categoryModelDao = categoryModelDao;
    }


    public  static DishModelDao getDishModelDao() {
        return dishModelDao;
    }

    private static void setDishModelDao(DishModelDao dishModelDao) {
        OrderViewModel.dishModelDao = dishModelDao;
    }


    protected void sendReport() throws JSONException {
        JSONObject report = getReportInformation();
        JSONArray courses = convertReportToJSON();
        report.put("portate",courses);
        orderRepository.sendReport(report, new VolleyCallbackObject() {
            @Override
            public void onSuccess(JSONObject result) {
                Toast.makeText(getApplication(),getApplication().getString(R.string.comandaInviata), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public JSONObject getReportInformation() throws JSONException {
        JSONObject report = new JSONObject();
        File userLoggedFile = new File(ContextApplication.getAppContext().getFilesDir(), LoginViewModel.filename);
        JSONObject user = new JSONObject(LoginViewModel.getUserFileInString());
        report.put(getApplication().getString(R.string.Waiter),user.get("nome_cameriere"));
        report.put(getApplication().getString(R.string.id_tavolo), tableId);
        if(seatsNumber != 0)
            report.put(getApplication().getString(R.string.coperti), seatsNumber);
        return report;
    }


    private JSONArray convertReportToJSON(){
        JSONArray courses = new JSONArray();
        for(int i=0; i<CoursesAdapter.getCourses().size(); i++){
            Course course = CoursesAdapter.getCourses().get(i);
            Gson gson = new Gson();
            String json = gson.toJson(course);
            try {
                courses.put(new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return courses;
    }
}
