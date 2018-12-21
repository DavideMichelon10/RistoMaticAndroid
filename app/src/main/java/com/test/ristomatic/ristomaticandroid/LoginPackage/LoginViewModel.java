package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LoginViewModel extends ViewModel {
    LoginRepository loginRepository;
    //file dove sono salvate info su user loggato
    private static File userLoggedFile;
    private static MutableLiveData<Boolean> logged;
    private FileOutputStream outputStream;
    public static String filename = "RistoMatic";

    public LoginViewModel(){
        logged = new MutableLiveData<Boolean>();
        setUserLoggedFile(new File(ContextApplication.getAppContext().getFilesDir(), filename));
    }

    public static File getUserLoggedFile() {
        return userLoggedFile;
    }

    public static void setUserLoggedFile(File userLoggedFile) {
        LoginViewModel.userLoggedFile = userLoggedFile;
    }

    public void init(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

    public static MutableLiveData<Boolean> getLogged(){
        return  logged;
    }
    public static void setLogged(boolean bool){
        logged.setValue(bool);
    }
    //forse boolean
    public void sendCode(int code){
        loginRepository.sendCode(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    //ritorna solo array con un oggetto --> pos0
                    saveOnFile(result.getJSONObject(0));
                    LoginViewModel.setLogged(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },code);
    }


    private void saveOnFile(JSONObject response){
        try {
            outputStream = ContextApplication.getAppContext().openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(response.toString().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
