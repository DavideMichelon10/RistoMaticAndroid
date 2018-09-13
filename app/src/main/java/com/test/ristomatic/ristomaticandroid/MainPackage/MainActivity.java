package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.test.ristomatic.ristomaticandroid.MainPackage.TabLayoutDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.MainPackage.TabLayoutDirectory.TablesFragment;
import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;
    private int numberRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberRooms = 4;
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //mainViewModel.init(new MainRepository());

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //for(int i=0; i<numberRooms; i++){
          //  pagerAdapter.addFragment(new TablesFragment());
        //}
        mainViewModel.init(new MainRepository(), pagerAdapter);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        //titolo tab
        for(int i=0; i<numberRooms; i++){
            tabLayout.getTabAt(i).setText("Sala "+ (1+i));

        }

    }


    public void testJSON(View view){
        //mainViewModel.getTable();
        //mainViewModel.getTables().get(0).setValue(new Table(2, "aa", 4));
    }

    //disabilita tasto goBack
    @Override
    public void onBackPressed() { }
}
