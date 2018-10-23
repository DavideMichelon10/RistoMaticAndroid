package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.R;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.AppDatabase;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;

import java.util.LinkedList;
import java.util.List;

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
       mainViewModel.updateablesDate();

    }

    public static MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    //disabilita tasto goBack
    @Override
    public void onBackPressed() { }
}
