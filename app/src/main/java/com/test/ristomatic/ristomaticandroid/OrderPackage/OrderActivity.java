package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

        RadioGroup rgp = (RadioGroup) findViewById(R.id.flow_group);
        RadioGroup.LayoutParams rprms;

        for(int i=0;i<5;i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(""+(i+1));
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton thisButton = (RadioButton) v;
                    System.out.println(thisButton.getText());
                }
            });
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT);
            rgp.addView(radioButton, -1, rprms);
            if(i==0) {
                radioButton.performClick();
            }
        }
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