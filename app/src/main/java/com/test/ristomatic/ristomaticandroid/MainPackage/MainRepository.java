package com.test.ristomatic.ristomaticandroid.MainPackage;



import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;

import org.json.JSONArray;

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
                getTablesRooms(volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonArrayTableRoom);
    }


    public void getTablesInRoom(final VolleyCallback volleyCallback, int room) {
        JsonArrayRequest jsonArrayTableRoom = new JsonArrayRequest(Request.Method.GET, VolleyCallApplication.getTablesInRoom() + "/" + room, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getTablesRooms(volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonArrayTableRoom);
    }
}
