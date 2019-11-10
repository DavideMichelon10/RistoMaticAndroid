package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.DefaultRetryPolicy;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
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

public class OrderRepository implements Parcelable {

    public OrderRepository() { }

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

    public void sendReport(final JSONObject report, final VolleyCallbackObject volleyCallback) {

        final JsonObjectRequest sendRequest = new JsonObjectRequest(Request.Method.POST, VolleyCallApplication.report(), report,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        volleyCallback.onSuccess(response);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if(error.getMessage() != null && error.getMessage().contains("Failed to connect to")){
                    Toast.makeText(ContextApplication.getAppContext(), "COMANDA IN CODA", Toast.LENGTH_LONG).show();

                    System.out.println("IN MESSAGE CONTAINS");
                    sendReport(report, volleyCallback);
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
                            System.out.println(response.toString());
                            volleyCallbackObject.onSuccess(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (getStatusCode(error) == 500) {
                        OrderViewModel.setStatusCodeCases(OrderViewModel.StatusCodeCases.STATUS_CODE_500);
                        String body = new String(error.networkResponse.data, UTF_8);
                        printMessageFromJSON(body);
                    }
                }
            });

            getRichiama.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(getRichiama);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendModificaRichiamo(final JSONObject modifiche, final VolleyCallbackObject volleyCallbackObject) {

        final JsonObjectRequest sendModificaRichiamo = new JsonObjectRequest(Request.Method.POST, VolleyCallApplication.sendModificaRichiamo(), modifiche,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.getMessage() != null && error.getMessage().contains("Failed to connect to")){
                    sendModificaRichiamo(modifiche, volleyCallbackObject);
                }

                if (getStatusCode(error) == 500) {
                    String body = new String(error.networkResponse.data, UTF_8);
                    printMessageFromJSON(body);
                }
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
                        OrderViewModel.setStatusCodeCases(OrderViewModel.StatusCodeCases.STATUS_CODE_SCONTRINO_200);

                        showToast("Scontrino stampato", true);
                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                OrderViewModel.setStatusCodeCases(OrderViewModel.StatusCodeCases.STATUS_CODE_SCONTRINO_500);

                if (getStatusCode(error) == 500) {
                    String body = new String(error.networkResponse.data, UTF_8);
                    printMessageFromJSON(body);
                }
            }
        });
        stampaScontrino.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(stampaScontrino);
    }



    public void stampaEstratto(final int idTable, final int idSala, final VolleyCallbackObject volleyCallbackObject) {
        final JsonObjectRequest stampaEstratto = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.stampa() + "/estratto?tavolo=" + idTable+"&sala="+idSala, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        OrderViewModel.setStatusCodeCases(OrderViewModel.StatusCodeCases.STATUS_CODE_SCONTRINO_200);

                        showToast("Estratto conto stampato", true);

                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                OrderViewModel.setStatusCodeCases(OrderViewModel.StatusCodeCases.STATUS_CODE_SCONTRINO_500);
                if (getStatusCode(error) == 500) {
                    String body = new String(error.networkResponse.data, UTF_8);
                    printMessageFromJSON(body);

                    System.out.println("BODY: "+body);
                }
            }
        });
        stampaEstratto.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(stampaEstratto);
    }

    public void stampaEstrattoSenzaCancellare(final int idTable, final int idSala, final VolleyCallbackObject volleyCallbackObject) {
        final JsonObjectRequest stampaEstrattoSenzaCancellare = new JsonObjectRequest(Request.Method.GET, VolleyCallApplication.stampa() + "/estrattoSenzaCancellare?tavolo=" + idTable+"&sala="+idSala, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        OrderViewModel.setStatusCodeCases(OrderViewModel.StatusCodeCases.STATUS_CODE_SCONTRINO_200);

                        showToast("Estratto conto stampato, conto non cancellato", true);

                        volleyCallbackObject.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                OrderViewModel.setStatusCodeCases(OrderViewModel.StatusCodeCases.STATUS_CODE_SCONTRINO_500);
                if (getStatusCode(error) == 500) {
                    String body = new String(error.networkResponse.data, UTF_8);
                    printMessageFromJSON(body);

                    System.out.println("BODY: "+body);
                }
            }
        });
        stampaEstrattoSenzaCancellare.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingeltonVolley.getInstance(ContextApplication.getAppContext()).addToRequestQueue(stampaEstrattoSenzaCancellare);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public int getStatusCode(VolleyError error){
        try{
            NetworkResponse networkResponse = error.networkResponse;
            return networkResponse.statusCode;
        }catch(NullPointerException ex){
            System.out.println("IN CATCH");
            return 0;
        }

    }

    protected void printMessageFromJSON(String body) {
        try {
            JSONObject msg = new JSONObject(body);
            String msgString = msg.getString("MSG");
            showToast(msgString, false);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void showToast(String msg, boolean b){
        Toast toast = Toast.makeText(ContextApplication.getAppContext(), msg, Toast.LENGTH_LONG);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        int color = ContextCompat.getColor(ContextApplication.getAppContext(), R.color.myRed);
        if(b){
            color = ContextCompat.getColor(ContextApplication.getAppContext(), R.color.myGreen);
        }else{
            color = ContextCompat.getColor(ContextApplication.getAppContext(), R.color.myRed);
        }
        view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        toast.show();
    }


}
