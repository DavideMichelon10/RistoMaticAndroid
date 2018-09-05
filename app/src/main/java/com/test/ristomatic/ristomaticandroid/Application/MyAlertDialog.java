package com.test.ristomatic.ristomaticandroid.Application;

import android.app.AlertDialog;
import android.content.Context;

import com.test.ristomatic.ristomaticandroid.LoginPackage.LoginActivity;

public class MyAlertDialog {
    AlertDialog.Builder builder;

    public MyAlertDialog(String text, Context context){
        builder = new AlertDialog.Builder(context);
        builder.setMessage(text);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public MyAlertDialog(String text, Context context,String title){
        builder = new AlertDialog.Builder(context);
        builder.setMessage(text);
        builder.setTitle(title);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
