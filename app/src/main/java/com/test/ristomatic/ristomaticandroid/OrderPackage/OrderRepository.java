package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.os.Parcel;
import android.os.Parcelable;

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

public class OrderRepository implements Parcelable {

    public OrderRepository(){}
    protected OrderRepository(Parcel in) {
    }

    public static final Creator<OrderRepository> CREATOR = new Creator<OrderRepository>() {
        @Override
        public OrderRepository createFromParcel(Parcel in) {
            return new OrderRepository(in);
        }

        @Override
        public OrderRepository[] newArray(int size) {
            return new OrderRepository[size];
        }
    };

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

    public void sendModificaRichiamo(final JSONObject modifiche, final VolleyCallbackObject volleyCallbackObject) {
        System.out.println("IN SEND MODIFICA RICHIAMO");
        final JsonObjectRequest sendModificaRichiamo = new JsonObjectRequest(Request.Method.POST, VolleyCallApplication.sendModificaRichiamo(), modifiche,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("IN ONRESPONSE");
                        System.out.println(response.toString());
                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("IN ONERROR SEND RICHIAMO: "+error.getMessage());
                //sendModificaRichiamo(modifiche, volleyCallbackObject);
            }
        });
        sendModificaRichiamo.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(sendModificaRichiamo);
    }


    public void stampaScontrino(final int idTable, final int idSala, final VolleyCallbackObject volleyCallbackObject) {
        final JsonObjectRequest stampaScontrino = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.stampa() + "/scontrino?tavolo=" + idTable+"&sala="+idSala, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.getMessage());
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(stampaScontrino);
    }
    public void stampaEstratto(final int idTable, final int idSala, final VolleyCallbackObject volleyCallbackObject) {
        final JsonObjectRequest stampaEstratto = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.stampa() + "/estratto?tavolo=" + idTable+"&sala="+idSala, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.getMessage());
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(stampaEstratto);
    }

    public void stampaEstrattoSenzaCancellare(final int idTable, final int idSala, final VolleyCallbackObject volleyCallbackObject) {
        final JsonObjectRequest stampaEstrattoSenzaCancellare = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.stampa() + "/estrattoSenzaCancellare?tavolo=" + idTable+"&sala="+idSala, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.getMessage());
            }
        });
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(stampaEstrattoSenzaCancellare);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
