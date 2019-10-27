package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.LoginPackage.LoginViewModel;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.GraphicComponents;
import com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory.PagerAdapter;
import com.test.ristomatic.ristomaticandroid.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static MainViewModel mainViewModel;
    private static GraphicComponents graphicComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        final ViewPager viewPager = findViewById(R.id.viewPager);
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        graphicComponents = new GraphicComponents(viewPager, tabLayout, pagerAdapter);
        mainViewModel.init(new MainRepository(), graphicComponents.getPagerAdapter(),this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mainViewModel.updateablesDate();
            }
        }).start();

    }


    public static void createView(int numberRooms){
        graphicComponents.createView(numberRooms);
    }


    public static MainViewModel getMainViewModel() {
        return mainViewModel;
    }

    //disabilita tasto goBack
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.logout));
        builder.setPositiveButton(getString(R.string.Si),new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    //svuota file login e torna a pagina login
                    FileOutputStream outputStream ;
                    outputStream = ContextApplication.getAppContext().openFileOutput(LoginViewModel.filename, Context.MODE_PRIVATE);
                    outputStream.write("".getBytes());
                    outputStream.close();
                    MainActivity.super.onBackPressed();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        builder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
