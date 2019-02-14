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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //intent contenete idTavolo e seatsNumber
        //Assegna il context alla utilities per inserire il piatto
        InsertDishUtilities.setContext(this);

        initializeViewModel();
        createCourseSelection();
        initializeRecyclerViewCategories();
        initializeRecyclerViewDishes();
        initializeRecyclerViewCourses();
    }


    public void initializeViewModel(){
        Intent intent = getIntent();
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        idTable = Integer.parseInt(intent.getStringExtra(getString(R.string.id_tavolo)));
        seatsNumber=intent.getIntExtra(getString(R.string.coperti),0);
        orderViewModel.init(this,idTable, seatsNumber);
    }


    private void createCourseSelection()
    {
        //assegna a rgp il RadioGroup dell'activity_order.xml
        rgp = findViewById(R.id.flow_group);
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


    public void initializeRecyclerViewCategories(){
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setHasFixedSize(true);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(this,1));
        recyclerViewCategories.setAdapter(orderViewModel.getAdapterCategories());
    }


    public void initializeRecyclerViewDishes(){
        recyclerViewDishes = findViewById(R.id.recyclerViewDishes);
        recyclerViewDishes.setHasFixedSize(true);
        recyclerViewDishes.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewDishes.setAdapter(orderViewModel.getDishedAdapter());
    }


    public void initializeRecyclerViewCourses(){
        recyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setHasFixedSize(true);
        recyclerViewCourses.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerViewCourses.setAdapter(orderViewModel.getCoursesAdapter());
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
