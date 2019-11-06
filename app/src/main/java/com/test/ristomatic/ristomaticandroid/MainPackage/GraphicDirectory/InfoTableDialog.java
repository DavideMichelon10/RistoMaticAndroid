package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.R;

public class InfoTableDialog extends DialogFragment {

    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        int idTable = getArguments().getInt("idTavolo");
        String tableName = getArguments().getString("tableName");
        int idRoom = getArguments().getInt("idRoom");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        builder.setView(linearLayout);
        Button richiama = createRichiamaButton(idTable, tableName, idRoom);
        linearLayout.addView(richiama);
        return builder.create();
    }

    private Button createRichiamaButton(final int idTable, final String tableName, final int idRom) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(10, 20, 10, 20);

        Button b = new Button(getContext());
        b.setLayoutParams(params);
        b.setTextSize(15);
        b.setBackgroundColor(ContextCompat.getColor(ContextApplication.getAppContext(),R.color.colorPrimary));
        b.setTextColor(Color.WHITE);
        b.setText(R.string.richiama_tavolo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("idTable",idTable);
                intent.putExtra("tableName", tableName);
                intent.putExtra("idRoom", idRom);
                intent.putExtra("richiama",true);
                startActivity(intent);
            }
        });
        return b;
    }
    public static InfoTableDialog newIstance(int idTable, String tableName, int idRoom) {
        InfoTableDialog frag = new InfoTableDialog();
        Bundle args = new Bundle();
        args.putString("tableName", tableName);
        args.putInt("idTavolo", idTable);
        args.putInt("idRoom", idRoom);
        frag.setArguments(args);
        return frag;
    }

}