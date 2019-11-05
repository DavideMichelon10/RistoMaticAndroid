package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;

public class SelectSeatsDialog extends DialogFragment {
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Numero coperti: ");
        //Assegna gli argomenti
        final int idTable = getArguments().getInt("idTavolo");
        final String tableName = getArguments().getString("tableName");
        final int idRoom = getArguments().getInt("idRoom");

        //Crea il numberPicker per i coperti e il tasto "ok"
        final EditText seatsEditText = new EditText(getContext());
        seatsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        seatsEditText.setText(Integer.toString(GlobalVariableApplication.getValueCopertiStart()));
        seatsEditText.setTextSize(50);
        builder.setView(seatsEditText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ContextApplication.getAppContext(), OrderActivity.class);
                intent.putExtra("idTable", idTable);
                intent.putExtra("idRoom", idRoom);
                intent.putExtra("tableName", tableName);
                intent.putExtra("coperti", Integer.parseInt(seatsEditText.getText().toString()));
                intent.putExtra("richiama",false);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ContextApplication.getAppContext().startActivity(intent);
            }
        });
        Dialog d = builder.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        seatsEditText.selectAll();
        return d;
    }


    public static SelectSeatsDialog newInstance(int idTavolo, String tableName, int idRoom) {
        SelectSeatsDialog frag = new SelectSeatsDialog();
        Bundle args = new Bundle();
        args.putInt("idTavolo", idTavolo);
        args.putString("tableName", tableName);
        args.putInt("idRoom", idRoom);
        frag.setArguments(args);
        return frag;
    }
}