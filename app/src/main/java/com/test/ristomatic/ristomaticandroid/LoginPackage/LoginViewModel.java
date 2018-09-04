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
    public  static MutableLiveData<Boolean> logged;

    public LoginViewModel(){
        logged = new MutableLiveData<Boolean>();
        //this.loginRepository = loginRepository;
        //filename = "UserCredential";
    }
    public void init(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

    //forse boolean
    public void sendCode(int code){
        loginRepository.sendCode(code);
    }

    public static void setLogged(boolean bool){
        logged.setValue(bool);
    }
    //public void getLogged(int code){
      //  logged = loginRepository.getLogged(code);
    //}
    public boolean isLogged(){
        //quando esci dal programma, elimina file
        if(userLoggedFile.exists()){
            return true;
        }
        return false;
    }
}
