package com.test.ristomatic.ristomaticandroid.MainPackage;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.LoginPackage.LoginViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainRepository {


    public  void getTablesRooms(final VolleyCallback volleyCallback) {
        JsonArrayRequest jsonArrayTableRoom = new JsonArrayRequest(Request.Method.GET, VolleyCallApplication.getTablesRooms(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonArrayTableRoom);
    }
    public void getTables(final VolleyCallback volleyCallback) {
        JsonArrayRequest jsonArrayTable = new JsonArrayRequest(Request.Method.GET, VolleyCallApplication.getTables(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonArrayTable);
    }
}
