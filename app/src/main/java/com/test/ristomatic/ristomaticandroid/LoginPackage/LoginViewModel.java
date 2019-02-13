package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class LoginViewModel extends AndroidViewModel {
    LoginRepository loginRepository;
    //file dove sono salvate info su user loggato
    private static File userLoggedFile;
    private static MutableLiveData<LoginCases> loginCase;
    private FileOutputStream outputStream;
    public static String filename = "RistoMatic";

    public enum LoginCases{
        RIGHT_PASSWORD, WRONG_PASSWORD, CONNECTION_PROBLEM
    }
    public LoginViewModel(Application application) {
        super(application);
        loginCase = new MutableLiveData<LoginCases>();
        setUserLoggedFile(new File(ContextApplication.getAppContext().getFilesDir(), filename));
    }


    public static File getUserLoggedFile() {
        return userLoggedFile;
    }

    public static void setUserLoggedFile(File userLoggedFile) {
        LoginViewModel.userLoggedFile = userLoggedFile;
    }

    public static MutableLiveData<LoginCases> getLoginCase(){
        return loginCase;
    }

    public static void setLoginCase(LoginCases newCase){
        loginCase.setValue(newCase);
    }


    public void init(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }


    public void sendCode(int code){
        loginRepository.sendCode(new VolleyCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    //ritorna solo array con un oggetto --> pos0
                    saveOnFile(result.getJSONObject(0));
                    LoginViewModel.setLoginCase(LoginCases.RIGHT_PASSWORD);
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

    public Intent checkAlreadyLogged() {
        Intent moveToActivityIntent = new Intent(getApplication().getApplicationContext(), LoginActivity.class);
        String userInString = getUserFileInString();
        if(userLoggedFile.exists() && userInString.length() != 0) {
            try {
                return moveToActivityIntent = moveToMain(userInString, moveToActivityIntent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Intent moveToMain(String userInString, Intent moveToActivityIntent) throws JSONException {
        JSONObject UserLoggedJSON = new JSONObject(userInString);
        moveToActivityIntent = new Intent(getApplication().getApplicationContext(), MainActivity.class);
        moveToActivityIntent.putExtra("Id", UserLoggedJSON.getString("cameriere_id"));
        moveToActivityIntent.putExtra("Waiter", UserLoggedJSON.getString("nome_cameriere"));
        return moveToActivityIntent;
    }

    public static String getUserFileInString(){
        String jsonString = "";
        try {
            FileReader fr = new FileReader(userLoggedFile);
            int i;
            while ((i=fr.read()) != -1){
                jsonString += (char)i;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
