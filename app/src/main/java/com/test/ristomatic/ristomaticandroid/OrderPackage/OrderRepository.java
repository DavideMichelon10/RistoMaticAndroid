package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.os.CountDownTimer;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

    public void getRichiama(final VolleyCallbackObject volleyCallbackObject, final JSONObject request) {
        final JsonObjectRequest getRichiama;
        try {
            getRichiama = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.report() + "?idSala="
                    + request.getString("idSala") + "&idTavolo=" + request.getString("idTavolo"), request,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            volleyCallbackObject.onSuccess(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.err.println("ERROR: " + error.getMessage());
                    getRichiama(volleyCallbackObject, request);
                }
            });
            getRichiama.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(getRichiama);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void changeTableState(final VolleyCallbackObject volleyCallback, final JSONObject jsonToSend) {

        JsonObjectRequest changeState = new JsonObjectRequest(Request.Method.PUT, VolleyCallApplication.changeTableState(), jsonToSend,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                changeTableState(volleyCallback, jsonToSend);
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(changeState);
    }
}
