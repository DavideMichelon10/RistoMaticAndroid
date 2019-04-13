package com.test.ristomatic.ristomaticandroid.MainPackage;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainRepository {
    //riceve json con tutte sale e tavoli, ricorsivo
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
                System.out.println("ONERROR in getTablesRooms");
                System.out.println("errore "+error.getMessage());
                getTablesRooms(volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonArrayTableRoom);
    }
    //
    public void getTablesInRoom(final VolleyCallback volleyCallback, int room) {
        JsonArrayRequest jsonArrayTableRoom = new JsonArrayRequest(Request.Method.GET, VolleyCallApplication.getTablesRooms() + "/" + room, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //getTablesRooms(volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonArrayTableRoom);
    }

    public void changeTableState(final VolleyCallbackObject volleyCallback, final JSONObject jsonToSend){

        JsonObjectRequest changeState = new JsonObjectRequest(Request.Method.PUT, VolleyCallApplication.changeTableState(), jsonToSend,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                changeTableState(volleyCallback,jsonToSend);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(changeState);
    }

    //manda 5 date da confrontare e riceve jsonArray con tabelle da aggiornare
    public void updateTablesDate(final JSONArray currentDates, final VolleyCallback volleyCallback){
        JsonArrayRequest jsonArrayDateUpdated = new JsonArrayRequest(Request.Method.GET, VolleyCallApplication.updateTablesDate(), currentDates,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //updateTablesDate(currentDates, volleyCallback);
                System.out.println("IN ONERRROR " +currentDates.toString());
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonArrayDateUpdated);
    }

}