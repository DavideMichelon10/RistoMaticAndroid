package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;
import android.content.Context;

//classe per avere context everywhere
public class ContextApplication extends Application{
    private static Context context;

    public void onCreate() {
        super.onCreate();
        ContextApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextApplication.context;
    }

}
