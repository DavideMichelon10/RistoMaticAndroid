package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;

public class VolleyCallApplication extends Application {

    public static String checkLogin(){
        return GlobalVariableApplication.serverAddress() +"CheckLogIn";
    }
}
