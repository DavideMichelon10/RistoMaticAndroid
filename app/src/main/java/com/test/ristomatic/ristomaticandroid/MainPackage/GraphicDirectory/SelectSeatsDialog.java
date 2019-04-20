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
import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.R;

public class SelectSeatsDialog extends DialogFragment {
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Numero coperti: ");
        //Assegna gli argomenti
        final String idTable = getArguments().getString("idTavolo");

        //Crea il numberPicker per i coperti e il tasto "ok"
        final NumberPicker seatsPicker = new NumberPicker(getContext());
        builder.setView(seatsPicker);
        seatsPicker.setMinValue(1);
        seatsPicker.setMaxValue(100);
        seatsPicker.setValue(GlobalVariableApplication.VALUE_NUMBER_PICKER_COPERTI_START);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ContextApplication.getAppContext(), OrderActivity.class);
                MainActivity.getMainViewModel().changeTableState(Integer.parseInt(idTable), "Occupato");
                intent.putExtra(getString(R.string.id_tavolo), idTable);
                intent.putExtra(getString(R.string.coperti), seatsPicker.getValue());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ContextApplication.getAppContext().startActivity(intent);
            }
        });
        return builder.create();
    }


    public static SelectSeatsDialog newInstance(String idTavolo) {
        SelectSeatsDialog frag = new SelectSeatsDialog();
        Bundle args = new Bundle();
        args.putString("idTavolo", idTavolo);
        frag.setArguments(args);
        return frag;
    }
}