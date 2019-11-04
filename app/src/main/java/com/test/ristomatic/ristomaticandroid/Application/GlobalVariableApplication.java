package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.test.ristomatic.ristomaticandroid.LoginPackage.SettingsActivity;

//variabli globali
public class GlobalVariableApplication extends Application {
    public static final int DELAY_REQUEST_TIME = 3000;
    private static int COURSES_NUMBER;
    private static int NUMBER_COLUMN_TABLES;
    private static int VALUE_COPERTI_START;
    public static final int NUMBER_COLUMN_REPORT = 1;

    public static int getCoursesNumber() {
        return SettingsActivity.userPreferences.getInt("COURSES_NUMBER",4);
    }

    public static int getNumberColumnTables() {
        return SettingsActivity.userPreferences.getInt("NUMBER_COLUMN_TABLES",3);
    }

    public static int getValueCopertiStart() {
        return SettingsActivity.userPreferences.getInt("VALUE_COPERTI_START",3);
    }

    public static String serverAddress(){
        return "http://45.78.15.113:3000/";
        //return "http://ristoserver.herokuapp.com/";
    }

}