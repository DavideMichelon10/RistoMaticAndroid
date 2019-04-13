package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;

//variabli globali
public class GlobalVariableApplication extends Application {
    public static final int DELAY_REQUEST_TIME = 3000;
    public static final int COURSES_NUMBER = 4;
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
        return "http://192.168.1.86:8080/";
    }
}