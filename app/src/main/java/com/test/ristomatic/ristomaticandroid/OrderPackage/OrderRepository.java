package com.test.ristomatic.ristomaticandroid.OrderPackage;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;

import org.json.JSONObject;

public class OrderRepository {
    public void sendReport(final JSONObject report, final VolleyCallbackObject volleyCallback) {
        JsonObjectRequest sendRequest = new JsonObjectRequest(Request.Method.POST, VolleyCallApplication.report(), report,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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

    public void getRichiama(final VolleyCallbackObject volleyCallbackObject, int idTable){
        JsonObjectRequest getRichiama = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.report()+"/"+idTable, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("IN ONERROR");
            }
        });

        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(getRichiama);
    }
}
