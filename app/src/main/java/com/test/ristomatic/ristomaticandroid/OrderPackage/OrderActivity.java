package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private OrderViewModel orderViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        //orderViewModel.init();


        /*Intent intent = getIntent();
        String getMenu = intent.getStringExtra("getMenu");
        String getVariants = intent.getStringExtra("variants");
        System.out.println(getMenu);
        System.out.println(getVariants);

        System.out.println("IN ONCREATE");*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
}