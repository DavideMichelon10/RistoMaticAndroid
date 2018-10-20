package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;

import org.json.JSONObject;
//tutte le chiamate eseguite con volley
public class VolleyCallApplication extends Application {

    public static String checkLogin(){
        return GlobalVariableApplication.serverAddress() +"CheckLogIn";
    }
    //tavoli aggiornati per sala
    public static String getTablesInRoom(){
        return GlobalVariableApplication.serverAddress() +"getTablesInRoom";
    }
    //sale con tavoli
    public static String getTablesRooms(){
        return GlobalVariableApplication.serverAddress() +"getTablesRooms";
    }
    //cambiare stato tavolo
    public static String changeTableState(){
        return GlobalVariableApplication.serverAddress() + "changeTableState";
    }
    //categorie con piatti e id varianti
    public static String updateablesDate(){
        return GlobalVariableApplication.serverAddress() + "updateablesDate";
    }
    //tutte le varianti


}
