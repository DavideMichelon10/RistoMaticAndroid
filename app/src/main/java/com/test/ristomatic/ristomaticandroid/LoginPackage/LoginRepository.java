package com.test.ristomatic.ristomaticandroid.LoginPackage;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LoginRepository extends OrderRepository {

    public void sendCode(final VolleyCallback volleyCallback, int code)  {
        JsonObjectRequest jsonUserLogged = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.checkLogin() + "/" + code, null,
        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("IN ON  RESPONE");

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
                if (getStatusCode(error) == 404) {
                    LoginViewModel.setLoginCase(LoginViewModel.LoginCases.WRONG_PASSWORD);

                }else{
                    LoginViewModel.setLoginCase(LoginViewModel.LoginCases.CONNECTION_PROBLEM);
                }
            }
        });


        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonUserLogged);
    }
}
