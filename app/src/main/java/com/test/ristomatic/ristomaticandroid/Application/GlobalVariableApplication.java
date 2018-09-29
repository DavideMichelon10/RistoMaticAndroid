package com.test.ristomatic.ristomaticandroid.Application;

import android.app.Application;

public class GlobalVariableApplication extends Application {
    private static String ipServer;
    private static String port;
    public static String getIpServer() {
        return ipServer;
    }

    public static void setIpServer(String ipServer) {
        GlobalVariableApplication.ipServer = ipServer;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        GlobalVariableApplication.port = port;
    }

    public static String serverAddress(){
        //return "http://"+ getIpServer() +":"+getPort()+"/";
        return "http://192.168.43.54:8080/";
    }
}