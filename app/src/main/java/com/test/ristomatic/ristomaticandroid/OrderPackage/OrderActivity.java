package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities.InsertDishUtilities;
import com.test.ristomatic.ristomaticandroid.R;

import org.json.JSONException;


public class OrderActivity extends AppCompatActivity {
    private OrderViewModel orderViewModel;

    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewDishes;
    private  RecyclerView recyclerViewCourses;

    private RadioGroup rgp;
    private int seatsNumber, idTable;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        InsertDishUtilities.setContext(this);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        progress = findViewById(R.id.progressBar);

        initializeVM();
        createCourseSelection();
        initializeRecyclerViewCategories();
        initializeRecyclerViewDishes();
        initializeRecyclerViewCourses();

    }

    public void initializeVM(){
        Intent intent = getIntent();
        idTable = intent.getIntExtra("idTable", 0);
        boolean richiama = intent.getBooleanExtra("richiama",false);

        if(richiama){
            orderViewModel.init(idTable,this);
        }else{
            seatsNumber = intent.getIntExtra("coperti",0);
            System.out.println("coperti: "+seatsNumber);
            orderViewModel.init(idTable, seatsNumber,this);
            //TODO: far partire magari un altro thread qua
        }
    }


    private void createCourseSelection()
    {
        rgp = findViewById(R.id.flow_group);
        RadioGroup.LayoutParams rprms;
        for(int i = 0; i< GlobalVariableApplication.getCoursesNumber(); i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(""+(i+1));
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT);
            rgp.addView(radioButton, -1, rprms);
            if(i==0) {
                radioButton.performClick();
            }
        }
    }


    public void initializeRecyclerViewCategories(){
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setHasFixedSize(true);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(this,1));
        recyclerViewCategories.setAdapter(orderViewModel.getAdaptersContainer().getCategoriesAdapter());
    }


    public void initializeRecyclerViewDishes(){
        recyclerViewDishes = findViewById(R.id.recyclerViewDishes);
        recyclerViewDishes.setHasFixedSize(true);
        recyclerViewDishes.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewDishes.setAdapter(orderViewModel.getAdaptersContainer().getDishesAdapter());
    }


    public void initializeRecyclerViewCourses(){
        recyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setHasFixedSize(true);
        recyclerViewCourses.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerViewCourses.setAdapter(orderViewModel.getAdaptersContainer().getCoursesAdapter());
    }


    public void sendReport(View view) throws JSONException {
        orderViewModel.sendReport();
        super.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        AlertDialog backPressedDialog = buildAlertDialog();
        backPressedDialog.show();
    }

    public AlertDialog buildAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.back_to_table));
        builder.setPositiveButton(getString(R.string.Si),new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                OrderActivity.super.onBackPressed();
            }

        });
        builder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
