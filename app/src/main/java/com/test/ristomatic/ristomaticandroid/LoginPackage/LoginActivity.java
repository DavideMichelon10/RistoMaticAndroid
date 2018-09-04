package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.R;

public class LoginActivity extends AppCompatActivity {
    private  LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(new LoginRepository());
        System.out.println("AAAAA");
        LoginViewModel.logged.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean == false){
                    Toast.makeText(ContextApplication.getAppContext(), "OBSERVE FALSE", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ContextApplication.getAppContext(), "OBSERVE TRUE", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void testVolley(View view){
        loginViewModel.sendCode(1234);
    }
}
