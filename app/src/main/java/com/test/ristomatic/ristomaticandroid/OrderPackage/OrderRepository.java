package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.SingeltonVolley;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;
import com.test.ristomatic.ristomaticandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import static java.nio.charset.StandardCharsets.UTF_8;

public class OrderRepository {
    public void sendReport(final JSONObject report, final boolean richiama, final VolleyCallbackObject volleyCallback) {
        int method = Request.Method.POST;
        if (richiama)
            method = Request.Method.PUT;

        final JsonObjectRequest sendRequest = new JsonObjectRequest(method, VolleyCallApplication.report(), report,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(("ON POSITIVE RESPONSE"));
                        volleyCallback.onSuccess(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ON ERROR RESPONSE: " + error.getMessage());
                if(error.getMessage().contains("Failed to connect to")){
                    System.out.println("IN MESSAGE CONTAINS");
                    sendReport(report, richiama, volleyCallback);
                }
            }
        });
        sendRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
                    NetworkResponse networkResponse = error.networkResponse;
                    int statusError = networkResponse.statusCode;
                    if (statusError == 500) {
                        OrderViewModel.setStatusCodeCases(OrderViewModel.StatusCodeCases.STATUS_CODE_500);
                        String body = new String(error.networkResponse.data, UTF_8);
                        System.out.println("BODY: " + body);
                        try {
                            JSONObject msg = new JSONObject(body);
                            String msgString = msg.getString("MSG");
                            Toast toast = Toast.makeText(ContextApplication.getAppContext(), msgString, Toast.LENGTH_LONG);
                            View view = toast.getView();

                            //Gets the actual oval background of the Toast then sets the colour filter
                            int color = ContextCompat.getColor(ContextApplication.getAppContext(), R.color.myRed);
                            view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                            toast.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("IN PARSE ERROR" + networkResponse.statusCode);

                    //getRichiama(volleyCallbackObject, request);
                }
            }) {
                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {


                    return super.parseNetworkError(volleyError);
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    int statusCode = response.statusCode;
                    System.out.println("STATUS CODE: " + statusCode);
                    return super.parseNetworkResponse(response);
                }
            };
            getRichiama.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
