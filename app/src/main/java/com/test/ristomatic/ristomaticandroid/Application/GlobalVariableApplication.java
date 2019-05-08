package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;

//variabli globali
public class GlobalVariableApplication extends Application {
    public static final int DELAY_REQUEST_TIME = 3000;
    public static int COURSES_NUMBER = 5;
    public static final int VALUE_NUMBER_PICKER_COPERTI_START = 2;
    public static final int NUMBER_COLUMN_REPORT = 1;
    public static int COLUMN_NUMBER = 3;
    public static boolean firstTime = false;
    private static String ipServer;
    private static String port;
    public static String getIpServer() {
        return ipServer;
    }




    public static void setIpServer(String ipServer) {
        GlobalVariableApplication.ipServer = ipServer;
    }

    public static int getCoursesNumber() {
        return COURSES_NUMBER;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        GlobalVariableApplication.port = port;
    }

    public static String serverAddress(){
        //return "http://"+ getIpServer() +":"+getPort()+"/";
        //return "https://ristoserver.herokuapp.com/";
        return "https://ristoserver.herokuapp.com/";
    }
}