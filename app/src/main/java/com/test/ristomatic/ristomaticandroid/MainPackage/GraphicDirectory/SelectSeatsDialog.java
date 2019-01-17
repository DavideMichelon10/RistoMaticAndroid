package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.R;

public class SelectSeatsDialog extends DialogFragment {
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Numero coperti: ");
        //Assegna gli argomenti
        final String idTable = getArguments().getString("idTavolo");
        final int currentSubs = getArguments().getInt("currentSubs");

        //Crea il radiogroup per scegliere il sottoconto
        final RadioGroup subSelector = new RadioGroup(getContext());
        if (currentSubs > 0){
            for (int i=0;i<=currentSubs;i++){
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(i+1);
                subSelector.addView(radioButton, i);
            }
            builder.setCustomTitle(subSelector);
        }

        //Crea il numberPicker per i coperti e il tasto "ok"
        final NumberPicker seatsPicker = new NumberPicker(getContext());
        builder.setView(seatsPicker);
        seatsPicker.setMinValue(1);
        seatsPicker.setMaxValue(100);
        seatsPicker.setValue(2);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ContextApplication.getAppContext(), OrderActivity.class);
                /*TODO: retrive the checked radioButton and add the selected sub to the json sended after pressing "SEND"*/
                if(currentSubs > 0){
                    RadioButton selectedSub = subSelector.findViewById(subSelector.getCheckedRadioButtonId());
                    //vero se bisogna aggiungere un sottoconto
                    if(currentSubs == Integer.parseInt(selectedSub.getText().toString())-1)
                        intent.putExtra("selectedSub", selectedSub.getText().toString());
                }
                else{
                    intent.putExtra("selectedSub", 1);
                    MainActivity.getMainViewModel().changeTableState(Integer.parseInt(idTable), "Occupato");
                }
                intent.putExtra(getString(R.string.id_tavolo), idTable);
                intent.putExtra(getString(R.string.coperti), seatsPicker.getValue());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ContextApplication.getAppContext().startActivity(intent);
            }
        });
        return builder.create();
    }


    public static SelectSeatsDialog newInstance(String idTavolo, int currentSubs) {
        SelectSeatsDialog frag = new SelectSeatsDialog();
        Bundle args = new Bundle();
        args.putString("idTavolo", idTavolo);
        args.putInt("currentSubs", currentSubs);
        frag.setArguments(args);
        return frag;
    }
}