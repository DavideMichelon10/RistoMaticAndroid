package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginRepository{

    public void sendCode(final VolleyCallback volleyCallback, int code)  {
        JsonObjectRequest jsonUserLogged = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.checkLogin() + "/" + code, null,
        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //nessun utente con questa password
                        if(response.toString().compareTo("{}")==0){
                            LoginViewModel.setLoginCase(LoginViewModel.LoginCases.WRONG_PASSWORD);
                        }else{
                            JSONArray jsonArray = new JSONArray();
                            jsonArray.put(response);
                            volleyCallback.onSuccess(jsonArray);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LoginViewModel.setLoginCase(LoginViewModel.LoginCases.CONNECTION_PROBLEM);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonUserLogged);
    }
}
