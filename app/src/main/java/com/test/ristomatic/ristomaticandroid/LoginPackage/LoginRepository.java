package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.arch.lifecycle.LiveData;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class LoginRepository{

    public void sendCode(final int code)  {
        JsonObjectRequest jsonUserLogged = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.checkLogin() + "/" + code, null,
        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //LoginViewModel.checkLogin(response);
                        if(response.toString().compareTo("{}")==0){
                            LoginViewModel.setLogged(false);
                        }else{
                            //scrivi su File
                            System.out.println(response.toString());
                            LoginViewModel.setLogged(true);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sendCode(code);
                Toast.makeText(ContextApplication.getAppContext(), "ERROR "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonUserLogged);
        //Toast.makeText(ContextApplication.getAppContext(), "sdg" + contatore, Toast.LENGTH_SHORT).show();
    }



}
