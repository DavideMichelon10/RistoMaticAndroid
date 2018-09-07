package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;
    //file dove sono salvate info su user loggato
    public static File userLoggedFile;
    private static MutableLiveData<Boolean> logged;
    public static String filename = "RistoMatic";

    public LoginViewModel(){
        logged = new MutableLiveData<Boolean>();
        userLoggedFile = new File(ContextApplication.getAppContext().getFilesDir(), filename);

    }
    public void init(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

    public static MutableLiveData<Boolean> getLogged(){
        return  logged;
    }
    //forse boolean
    public void sendCode(int code){
        loginRepository.sendCode(code);
    }

    public static void setLogged(boolean bool){
        logged.setValue(bool);
    }


}
