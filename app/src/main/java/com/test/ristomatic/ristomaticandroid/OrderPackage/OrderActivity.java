package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities.InsertDishUtilities;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.R;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;


public class OrderActivity extends AppCompatActivity {
    private OrderViewModel orderViewModel;

    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewDishes;
    private  RecyclerView recyclerViewCourses;

    private RadioGroup rgp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Assegna il context alla utilities per inserire il piatto
        InsertDishUtilities.setContext(this);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        orderViewModel.init(this);
        createCourseSelection();
        //RecyclerView categories
        recyclerViewCategories = (RecyclerView) findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setHasFixedSize(true);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(this,1));
        recyclerViewCategories.setAdapter(orderViewModel.getAdapterCategories());
        //RecyclerView dishes
        recyclerViewDishes = (RecyclerView) findViewById(R.id.recyclerViewDishes);
        recyclerViewDishes.setHasFixedSize(true);
        recyclerViewDishes.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewDishes.setAdapter(orderViewModel.getAdapterDishes());

        //test
        recyclerViewCourses = (RecyclerView) findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setHasFixedSize(true);
        recyclerViewCourses.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerViewCourses.setAdapter(orderViewModel.getCoursesAdapter());
    }

    private void createCourseSelection()
    {
        //assegna a rgp il RadioGroup dell'activity_order.xml
        rgp = (RadioGroup) findViewById(R.id.flow_group);
        //Parametri grafici del RadioGroup
        RadioGroup.LayoutParams rprms;
        //Aggiunge un RadioButton ogni volta che cicla
        for(int i = 0; i< GlobalVariableApplication.getCoursesNumber(); i++){
            //Settando i valori del RadioButon
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(""+(i+1));
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT);
            //Aggiugnge il radioButton al RadioGroup
            rgp.addView(radioButton, -1, rprms);
            //Il primo button è già clickato di default
            if(i==0) {
                radioButton.performClick();
            }
        }
    }


    public void sendReport(View view){
        orderViewModel.sendReport();
    }
    //disabilita buttone backPressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}