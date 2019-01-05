package com.test.ristomatic.ristomaticandroid.OrderPackage.Dialogs.SelectVariantsModel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities.InsertDishUtilities;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectVariantsDialog extends DialogFragment {

    private static Context orderActivityContext;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.orderActivityContext);

        final EditText noteEditText = new EditText(getContext());
        final EditText timeSelectedEditText = new EditText(getContext());
        final String dishName = getArguments().getString("dish");
        final ArrayList<String> variants = getArguments().getStringArrayList("variants");
        final String[] variantsArray = new String[variants.size()];
        final boolean[] checkedVariants;
        final int dishPosition;

        timeSelectedEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        //Make the filter that avoid to insert spaces inside editText
        InputFilter spaceFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String[] splits = source.toString().split(" ");
                String reunite = "";
                for (int i = 0; i<splits.length;i++){
                    reunite += splits[i];
                }
                return reunite;
            }
        };
        timeSelectedEditText.setFilters(new InputFilter[]{spaceFilter});

        //True se il dialogo è per inserire un piatto, False se è per modificarlo
        if (getArguments().getBooleanArray("selectedVariants") == null) {
            checkedVariants = new boolean[variants.size()];
            timeSelectedEditText.setText("1");
        } else {
            checkedVariants = getArguments().getBooleanArray("selectedVariants");
            noteEditText.setText(getArguments().getString("note"));
            timeSelectedEditText.setText(getArguments().getString("timeSelected"));
        }

        //Mostra le varianti con collegate una checkBox
        builder.setMultiChoiceItems(variants.toArray(variantsArray), checkedVariants, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                //Imposta a true le checkedVariants checkate
                checkedVariants[i] = isChecked;
            }
        });

        //Tasto "OK"
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(timeSelectedEditText.getText().toString().compareTo("") == 0)
                    timeSelectedEditText.setText("1");
                //Id del radioButton clickato
                int radioButtonId = ((RadioGroup) ((OrderActivity) orderActivityContext).findViewById(R.id.flow_group)).getCheckedRadioButtonId();
                //Posizione della portata selezionata nella lista di portate
                int courseNumber = Integer.parseInt((String) ((RadioButton) ((OrderActivity) orderActivityContext).findViewById(radioButtonId)).getText());
                RecyclerView recyclerViewCourses = ((OrderActivity) orderActivityContext).findViewById(R.id.recyclerViewCourses);
                List<String> selectedVariants = new ArrayList<>();
                for (int i = 0; i < variants.size(); i++) {
                    if (checkedVariants[i]) {
                        selectedVariants.add(variants.get(i));
                    }
                }
                //Vero se la portata esiste già
                try {
                    //se è una modifica
                    if (getArguments().getString("note") != null) {
                        int dishPosition = getArguments().getInt("dishPosition");
                        int timeSelected = Integer.parseInt(timeSelectedEditText.getText().toString());
                        if (noteEditText.getText().toString().compareTo("") != 0){
                            selectedVariants.add(noteEditText.getText().toString());
                        }
                        InsertDishUtilities.modifyDishInCourse(courseNumber, dishPosition, recyclerViewCourses, timeSelected,(ArrayList<String>) selectedVariants);
                    } else {
                        if (noteEditText.getText().toString().compareTo("") != 0)
                            selectedVariants.add(noteEditText.getText().toString());
                        SelectedDish insertedDish = new SelectedDish(dishName, selectedVariants);
                        //Se la portata non esiste ne viene creata una nuova con il numero di portata e viene aggiunta alla lista
                        //successivamente viene chiamato il notifyItemInserted sulla recyclerViewCourses
                        if (!InsertDishUtilities.doesCourseExist(courseNumber)) {
                            if (timeSelectedEditText.getText().toString() == "1")
                                InsertDishUtilities.insertDishInNewCourse(courseNumber, insertedDish);
                            else
                                InsertDishUtilities.insertDishInNewCourse(courseNumber, insertedDish, Integer.parseInt(timeSelectedEditText.getText().toString()));
                        }

                        //se la portata esiste
                        else {
                            if (timeSelectedEditText.getText().toString() == "1")
                                InsertDishUtilities.handleInExistingCourse(courseNumber, insertedDish);
                            else
                                InsertDishUtilities.handleInExistingCourse(courseNumber, insertedDish, Integer.parseInt(timeSelectedEditText.getText().toString()));
                        }
                        //eccezione tirata quando cambi categoria e selezioni un piatto ed esso è nullo
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        });
        builder.setCustomTitle(timeSelectedEditText);
        //noteEditText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        noteEditText.setHint("NOTE");
        builder.setView(noteEditText);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow()).clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    //Aggiunge a arguments la lista di varianti da mostrare e ritorna l'istanza del Dialog
    public static SelectVariantsDialog newInsertionInstance(String dishName, ArrayList<String> variants, Context orderActivityContext) {
        SelectVariantsDialog.orderActivityContext = orderActivityContext;
        SelectVariantsDialog frag = new SelectVariantsDialog();
        Bundle args = new Bundle();
        args.putString("dish", dishName);

        args.putStringArrayList("variants", variants);
        frag.setArguments(args);
        return frag;
    }

    //Aggiunge a arguments la lista di varianti, varianti selezionata da mostrare e note, ritorna l'istanza del Dialog
    public static SelectVariantsDialog newModificationInstance(int dishPosition, ArrayList<String> variants, String timeSelected, ArrayList<String> selectedVariants, String note, Context orderActivityContext) {
        SelectVariantsDialog.orderActivityContext = orderActivityContext;
        SelectVariantsDialog frag = new SelectVariantsDialog();
        Bundle args = new Bundle();
        args.putInt("dishPosition", dishPosition);
        //Gli elementi saranno true se la checkbox corrispondente è selezionata false altrimenti
        boolean[] selectedVariantsBoolean = new boolean[variants.size()];
        for (int i = 0; i < variants.size(); i++) {
            if (selectedVariants.contains(variants.get(i)))
                selectedVariantsBoolean[i] = true;
            else
                selectedVariantsBoolean[i] = false;
        }
        args.putBooleanArray("selectedVariants", selectedVariantsBoolean);
        args.putString("note", note);
        args.putString("timeSelected", timeSelected);
        args.putStringArrayList("variants", variants);
        frag.setArguments(args);
        return frag;
    }
}