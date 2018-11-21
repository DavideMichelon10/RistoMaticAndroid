package com.test.ristomatic.ristomaticandroid.OrderPackage;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallback;

import org.json.JSONArray;

public class OrderRepository {
    public void sendReport(final JSONArray report, final VolleyCallback volleyCallback){
        JsonArrayRequest sendRequest = new JsonArrayRequest(Request.Method.POST, VolleyCallApplication.sendReport(), report,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("ON SUCCESS");
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sendReport(report, volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(sendRequest);
    }
}
