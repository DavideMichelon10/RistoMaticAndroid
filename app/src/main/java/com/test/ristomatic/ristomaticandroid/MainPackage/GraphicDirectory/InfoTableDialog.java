package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.test.ristomatic.ristomaticandroid.R;

public class InfoTableDialog extends DialogFragment {
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        builder.setView(linearLayout);
        Button richiama = createRichiamaButton();
        linearLayout.addView(richiama);
        return builder.create();
    }

    private Button createRichiamaButton() {
        Button b = new Button(getContext());
        b.setText(R.string.richiamaTavolo);
        return b;
    }
}