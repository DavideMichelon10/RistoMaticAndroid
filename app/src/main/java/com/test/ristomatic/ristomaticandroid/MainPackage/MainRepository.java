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
        JsonArrayRequest jsonArrayTableRoom = new JsonArrayRequest(Request.Method.GET, VolleyCallApplication.getTablesInRoom() + "/" + room, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("OnError getTablesInRoom");
                //getTablesRooms(volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(jsonArrayTableRoom);
    }

    public void changeTableState(final VolleyCallback volleyCallback, final int idTable, final String state){
        String urlAndParameters = String.format(VolleyCallApplication.changeTableState() + "/{\"idTavolo\":\"%1$s\",\"stato\":\"%2$s\"}", idTable, state);
        JsonArrayRequest changeState = new JsonArrayRequest(Request.Method.POST, urlAndParameters, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR: "+ error.getMessage());
                changeTableState(volleyCallback, idTable, state);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(changeState);
    }

    //manda 5 date da confrontare e riceve jsonArray con tabelle da aggiornare
    public void updateTablesDate(final JSONArray currentDates, final VolleyCallback volleyCallback){
        JsonArrayRequest jsonArrayDateUpdated = new JsonArrayRequest(Request.Method.POST, VolleyCallApplication.updateTablesDate(), currentDates,
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
    /*
    public void getMenu(final VolleyCallback volleyCallback){
        final JsonArrayRequest getMenu = new JsonArrayRequest(Request.Method.GET, VolleyCallApplication.getMenu(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("OnError getMenu");
                getMenu(volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(getMenu);
    }

    public  void getVariants(final  VolleyCallback volleyCallback){
        final JsonArrayRequest getVariants = new JsonArrayRequest(Request.Method.GET, VolleyCallApplication.getVariants(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getVariants(volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(getVariants);
    }*/
}