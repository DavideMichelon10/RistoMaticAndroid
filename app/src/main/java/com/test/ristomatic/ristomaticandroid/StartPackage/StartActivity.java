package com.test.ristomatic.ristomaticandroid.StartPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.LoginPackage.LoginActivity;
import com.test.ristomatic.ristomaticandroid.LoginPackage.LoginViewModel;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent nextActivityIntent = new Intent(this, LoginActivity.class);

        File userLoggedFile = new File(ContextApplication.getAppContext().getFilesDir(), LoginViewModel.filename);
        if(userLoggedFile.exists())
        {
            String jsonString = "";
            try {
                BufferedReader reader = new BufferedReader(new FileReader(userLoggedFile));
                jsonString = reader.readLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("IL FILE ESISTE: " + jsonString);
            try {
                JSONObject UserLoggedJSON = new JSONObject(jsonString);
                nextActivityIntent = new Intent(this, MainActivity.class);
                nextActivityIntent.putExtra("Id", UserLoggedJSON.getString("cameriere_id"));
                nextActivityIntent.putExtra("Waiter", UserLoggedJSON.getString("nome_cameriere"));
                startActivity(nextActivityIntent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            nextActivityIntent = new Intent(this, LoginActivity.class);
            nextActivityIntent.putExtra("LOGGATO", "NO");
            startActivity(nextActivityIntent);
        }
    }
}
