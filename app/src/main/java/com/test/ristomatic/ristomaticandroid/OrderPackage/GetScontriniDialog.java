package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;
import com.test.ristomatic.ristomaticandroid.R;

import org.json.JSONObject;

public class GetScontriniDialog extends DialogFragment {

    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        //Assegna gli argomenti
        final int idTable = getArguments().getInt("idTavolo");
        final int idRoom = getArguments().getInt("idRoom");
        final OrderRepository orderRepository = getArguments().getParcelable("orderRepository");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //builder.setTitle("Scegli l'operazione da fare");
        LinearLayout.LayoutParams params_ = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params_.setMargins(10, 10, 10, 30);
        final TextView title = new TextView(getContext());

        title.setText("Scegli l'operazione da fare:");
        title.setAllCaps(true);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);

        title.setLayoutParams(params_);
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(10, 20, 10, 20);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        builder.setView(linearLayout);


        Button [] buttons = new Button[3];
        for(int i = 0; i < 3; i++){
            buttons[i] = new Button(getContext());
            buttons[i].setLayoutParams(params);
            buttons[i].setTextSize(15);
            buttons[i].setBackgroundColor(ContextCompat.getColor(ContextApplication.getAppContext(),R.color.colorPrimary));
            buttons[i].setTextColor(Color.WHITE);
        }

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderRepository.stampaScontrino(idTable, idRoom, new VolleyCallbackObject() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        
                    }
                });
            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderRepository.stampaEstratto(idTable, idRoom, new VolleyCallbackObject() {
                    @Override
                    public void onSuccess(JSONObject result) {

                    }
                });
            }
        });

        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderRepository.stampaEstrattoSenzaCancellare(idTable, idRoom, new VolleyCallbackObject() {
                    @Override
                    public void onSuccess(JSONObject result) {

                    }
                });
            }
        });
        buttons[0].setText("STAMPA SCONTRINO");
        buttons[1].setText("STAMPA ESTRATTO");
        buttons[2].setText("STAMPA ESTRATTO SENZA CANCELLARE IL CONTO");

        linearLayout.addView(title);
        for(int i = 0; i < 3; i++){
            linearLayout.addView(buttons[i]);
        }





        Dialog d = builder.create();

        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return d;
    }

    public static GetScontriniDialog newInstance(int idTavolo, int idRoom, OrderRepository orderRepository) {
        GetScontriniDialog frag = new GetScontriniDialog();
        Bundle args = new Bundle();
        args.putInt("idTavolo", idTavolo);
        args.putInt("idRoom", idRoom);
        args.putParcelable("orderRepository",orderRepository);
        frag.setArguments(args);
        return frag;
    }
}
