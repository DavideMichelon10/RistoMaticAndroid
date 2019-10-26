package com.test.ristomatic.ristomaticandroid.OrderPackage;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderRepository {

    public void sendReport(final JSONObject report, final boolean richiama, final VolleyCallbackObject volleyCallback) {
        int method;
        if(richiama)
            method = Request.Method.PUT;
        else
            method = Request.Method.POST;

        JsonObjectRequest sendRequest = new JsonObjectRequest(method, VolleyCallApplication.report(), report,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(report.toString(1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        volleyCallback.onSuccess(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sendReport(report, richiama, volleyCallback);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(sendRequest);
    }

    public void getRichiama(final VolleyCallbackObject volleyCallbackObject, final int idTable) {
        final JsonObjectRequest getRichiama = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.report() + "/" + idTable, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.getMessage());
                getRichiama(volleyCallbackObject, idTable);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(getRichiama);
    }

}
