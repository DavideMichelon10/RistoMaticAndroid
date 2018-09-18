package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;

import org.json.JSONObject;

public class VolleyCallApplication extends Application {

    public static String checkLogin(){
        return GlobalVariableApplication.serverAddress() +"CheckLogIn";
    }

    public static String getTablesInRoom(){
        return GlobalVariableApplication.serverAddress() +"getTablesInRoom";
    }
    public static String getTablesRooms(){
        return GlobalVariableApplication.serverAddress() +"getTablesRooms";
    }

}
