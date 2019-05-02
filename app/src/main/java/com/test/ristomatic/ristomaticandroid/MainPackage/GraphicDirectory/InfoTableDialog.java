package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.R;

public class InfoTableDialog extends DialogFragment {

    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        int idTable = getArguments().getInt("idTavolo");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        builder.setView(linearLayout);
        Button richiama = createRichiamaButton(idTable);
        linearLayout.addView(richiama);
        return builder.create();
    }

    private Button createRichiamaButton(final int idTable) {
        Button b = new Button(getContext());
        b.setText(R.string.richiamaTavolo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("idTable",idTable);
                System.out.println("ID TABLE: "+idTable);
                intent.putExtra("richiama",true);
                startActivity(intent);
            }
        });
        return b;
    }
    public static InfoTableDialog newIstance(int idTable) {
        InfoTableDialog frag = new InfoTableDialog();
        Bundle args = new Bundle();
        args.putInt("idTavolo", idTable);
        frag.setArguments(args);
        return frag;
    }

}