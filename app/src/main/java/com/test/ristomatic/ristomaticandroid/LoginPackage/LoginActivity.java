package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.R;

public class LoginActivity extends AppCompatActivity {
    private  LoginViewModel loginViewModel;
    private EditText editCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(new LoginRepository());

        LoginViewModel.getLoginCase().observe(this, new Observer<LoginViewModel.LoginCases>() {
            @Override
            public void onChanged(@Nullable LoginViewModel.LoginCases loginCases) {
                switch (loginCases){
                    case RIGHT_PASSWORD:
                        Intent intent = new Intent(ContextApplication.getAppContext(), MainActivity.class);
                        startActivity(intent);
                        break;

                    case WRONG_PASSWORD:
                        printToast("La password inserita non è corretta.");
                        break;

                    case CONNECTION_PROBLEM:
                        printToast("Controlla la connessione");
                        break;

                    default:
                        break;
                }
            }
        });
    }


    @Override
    protected void onResume() {
        checkAlreadyLogged();
        editCode = (EditText) findViewById(R.id.editCode);
        editCode.setText("");
        super.onResume();
    }


    @Override
    public void onBackPressed() { }
    

    private void checkAlreadyLogged() {
        if(loginViewModel.checkAlreadyLogged() != null){
            startActivity(loginViewModel.checkAlreadyLogged());
        }
    }


    public void login(View view){
        editCode = (EditText) findViewById(R.id.editCode);
        int code;

        if(editCode.getText().toString().matches("")){
            printToast("Inserisci il codice");
        }else{
            code = Integer.parseInt(editCode.getText().toString());
            if(code > 999 && code < 10000){
                loginViewModel.sendCode(code);
            }else{
                printToast("Inserisci un numero di 4 cifre");
            }
        }
    }


    public void printToast(String message){
        Toast.makeText(ContextApplication.getAppContext(), message, Toast.LENGTH_LONG).show();
    }
}
