package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;

//tutte le chiamate eseguite con volley
public class VolleyCallApplication extends Application {

    public static String checkLogin(){
        return GlobalVariableApplication.serverAddress() +"v1/login";
    }
    //sale con tavoli
    public static String getTablesRooms(){
        return GlobalVariableApplication.serverAddress() +"v1/rooms";
    }
    //cambiare stato tavolo
    public static String changeTableState(){
        return GlobalVariableApplication.serverAddress() + "v1/tables";
    }
    //categorie con piatti e id varianti
    public static String updateTablesDate(){
        return GlobalVariableApplication.serverAddress() + "v1/dates";
    }
    public static String report(){
        return GlobalVariableApplication.serverAddress() +"report";
    }
}
