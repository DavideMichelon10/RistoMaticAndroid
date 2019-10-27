package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.R;

public class InfoTableDialog extends DialogFragment {

    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        int idTable = getArguments().getInt("idTavolo");
        String tableName = getArguments().getString("tableName");
        int idRoom = getArguments().getInt("idRoom");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        builder.setView(linearLayout);
        Button richiama = createRichiamaButton(idTable, tableName, idRoom);
        linearLayout.addView(richiama);
        return builder.create();
    }

    private Button createRichiamaButton(final int idTable, final String tableName, final int idRom) {
        Button b = new Button(getContext());
        b.setText(R.string.richiama_tavolo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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