package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.MyAlertDialog;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.R;

public class LoginActivity extends AppCompatActivity {
    private  LoginViewModel loginViewModel;
    private EditText editCode;
    private Button buttonLogin;
    private ProgressDialog dlg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(new LoginRepository());

        LoginViewModel.getLogged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean == false){
                    MyAlertDialog alertWrongPassword = new MyAlertDialog("La password inserita non è corretta. Riprova.", LoginActivity.this, "Passord non corretta");
                }else{
                    //vai a pagina Login
                    System.out.println("PASSWORD CORRETTA");
                    Toast.makeText(ContextApplication.getAppContext(), "Password corretta!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ContextApplication.getAppContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        //Toast.makeText(this, LoginViewModel, Toast.LENGTH_SHORT).show();
    }

    public void login(View view){
        editCode = (EditText) findViewById(R.id.editCode);
        int code;

        if(editCode.getText().toString().matches("")){
            Toast.makeText(this, "Inserisci il codice!", Toast.LENGTH_LONG).show();
        }else{
            code = Integer.parseInt(editCode.getText().toString());
            if(code > 999 && code < 10000){
                loginViewModel.sendCode(code);

            }else{
                Toast.makeText(this, "Inserisci un numero di 4 cifre", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void testSave(View view){
        if(LoginViewModel.userLoggedFile.length() == 0){
            Toast.makeText(this, "FILE ESISTE MA VUOTO",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "FILE PIENO",Toast.LENGTH_LONG).show();
        }
    }
}
