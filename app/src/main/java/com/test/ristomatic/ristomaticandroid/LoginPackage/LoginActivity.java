package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.R;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private EditText editCode;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(new LoginRepository());

        LoginViewModel.getLoginCase().observe(this, new Observer<LoginViewModel.LoginCases>() {
            @Override
            public void onChanged(@Nullable LoginViewModel.LoginCases loginCases) {
                switch (loginCases) {
                    case RIGHT_PASSWORD:
                        Intent intent = new Intent(ContextApplication.getAppContext(), MainActivity.class);
                        startActivity(intent);
                        break;

                    case CONNECTION_PROBLEM:
                        showSnackBar("CONTROLLA LA CONNESSIONE");
                        break;

                    case WRONG_PASSWORD:
                        showSnackBar("PASSWORD SBAGLIATA");
                        break;

                    default:
                        showSnackBar("ERRORE SCONOSCIUTO");
                        break;
                }
            }
        });

    }

    private void showSnackBar(String text) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.buttonLogin), text, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.RED);
        snackbar.show();
    }


    @Override
    protected void onResume() {
        checkAlreadyLogged();
        editCode = findViewById(R.id.editCode);
        editCode.setText("");
        super.onResume();
    }


    @Override
    public void onBackPressed() {
    }


    private void checkAlreadyLogged() {
        if (loginViewModel.checkAlreadyLogged() != null) {
            startActivity(loginViewModel.checkAlreadyLogged());
        }
    }


    public void login(View view) {
        editCode = findViewById(R.id.editCode);
        int code;

        if (editCode.getText().toString().matches("")) {
            showSnackBar("Inserisci il codice");
        } else {
            code = Integer.parseInt(editCode.getText().toString());
            if (code > 999 && code < 10000) {
                new SendCode().execute(code);
            } else {
                showSnackBar("Inserisci un numero di 4 cifre");
            }
        }
    }

    private class SendCode extends AsyncTask<Integer, String, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            loginViewModel.sendCode(integers[0]);
            return null;
        }
    }
}
