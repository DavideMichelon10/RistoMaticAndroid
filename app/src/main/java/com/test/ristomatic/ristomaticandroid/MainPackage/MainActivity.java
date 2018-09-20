package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static MainViewModel mainViewModel;
    public ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mainViewModel.init(new MainRepository(), pagerAdapter, viewPager, tabLayout);
    }

    public static MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    //disabilita tasto goBack
    @Override
    public void onBackPressed() { }
}
