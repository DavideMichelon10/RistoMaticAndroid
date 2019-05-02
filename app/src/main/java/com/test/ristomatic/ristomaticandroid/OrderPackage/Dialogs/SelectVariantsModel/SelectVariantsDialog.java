package com.test.ristomatic.ristomaticandroid.OrderPackage.Dialogs.SelectVariantsModel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities.InsertDishUtilities;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedVariant;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectVariantsDialog extends DialogFragment {

    private static Context orderActivityContext;
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final EditText timeSelectedEditText = new EditText(getContext());
        final String dishName = getArguments().getString("dish");
        final int dishId = getArguments().getInt("dishId");
        final ArrayList<SelectedVariant> variants = getArguments().getParcelableArrayList("variants");
        final List<Boolean> checkedVariants;
        final EditText noteEditText = new EditText(getContext());
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


        final RecyclerView multiChoiceItems = new RecyclerView(getContext());
        multiChoiceItems.setHasFixedSize(true);
        multiChoiceItems.setLayoutManager(new GridLayoutManager(getContext(),1));
        //True se il dialogo è per inserire un piatto, False se è per modificarlo
        if (getArguments().getBooleanArray("selectedVariants") == null) {
            checkedVariants = new ArrayList<>();
            timeSelectedEditText.setText("1");
            multiChoiceItems.setAdapter(new MultiChoiceAdapter(variants, getContext()));
        } else {
            checkedVariants = new ArrayList<>();
            for (Boolean b : getArguments().getBooleanArray("selectedVariants")){
                checkedVariants.add(new Boolean(b));
            }
            noteEditText.setText(getArguments().getString("note"));
            timeSelectedEditText.setText(getArguments().getString("timeSelected"));
            boolean[] selectedVariantsPlusArray = getArguments().getBooleanArray("selectedVariantsPlus");
            List<Boolean> selectedVariantsPlus = new ArrayList<>();
            for (boolean selectedVariantPlus: selectedVariantsPlusArray) {
                selectedVariantsPlus.add(selectedVariantPlus);
            }
            multiChoiceItems.setAdapter(new MultiChoiceAdapter(variants, checkedVariants,selectedVariantsPlus, getContext()));
        }

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
                List<SelectedVariant> selectedVariants = ((MultiChoiceAdapter) multiChoiceItems.getAdapter()).getSelectedVariants();
                try {
                    //se è una modifica
                    if (getArguments().getString("note") != null) {
                        int dishPosition = getArguments().getInt("dishPosition");
                        int timeSelected = Integer.parseInt(timeSelectedEditText.getText().toString());
                        if (noteEditText.getText().toString().compareTo("") != 0){
                            selectedVariants.add(new SelectedVariant(noteEditText.getText().toString(), -1));
                        }
                        InsertDishUtilities.modifyDishInCourse(courseNumber, dishPosition, recyclerViewCourses, timeSelected,(ArrayList<SelectedVariant>) selectedVariants);

                    } else {
                        if (noteEditText.getText().toString().compareTo("") != 0)
                            selectedVariants.add(new SelectedVariant(noteEditText.getText().toString(), -1));
                        SelectedDish insertedDish = new SelectedDish(dishName, dishId, selectedVariants);
                        //Se la portata non esiste ne viene creata una nuova con il numero di portata e viene aggiunta alla lista
                        //successivamente viene chiamato il notifyItemInserted sulla recyclerViewCourses
                        if (!InsertDishUtilities.doesCourseExist(courseNumber)) {
                            InsertDishUtilities.insertDishInNewCourse(courseNumber, insertedDish, Integer.parseInt(timeSelectedEditText.getText().toString()));
                        }

                        //se la portata esiste
                        else {
                            InsertDishUtilities.handleInExistingCourse(courseNumber, insertedDish, Integer.parseInt(timeSelectedEditText.getText().toString()));
                        }
                        //eccezione tirata quando cambi categoria e selezioni un piatto ed esso è nullo
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        });

        builder.setCustomTitle(timeSelectedEditText);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        noteEditText.setHint("NOTE: ");
        layout.addView(noteEditText);
        layout.addView(multiChoiceItems);
        builder.setView(layout);
        Dialog d = builder.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        timeSelectedEditText.selectAll();
        return d;
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow()).clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    //Aggiunge a arguments la lista di varianti da mostrare e ritorna l'istanza del Dialog
    public static SelectVariantsDialog newInsertionInstance(String dishName, int dishID, ArrayList<SelectedVariant> variants, Context orderActivityContext) {
        SelectVariantsDialog.orderActivityContext = orderActivityContext;
        SelectVariantsDialog frag = new SelectVariantsDialog();
        Bundle args = new Bundle();
        args.putString("dish", dishName);
        args.putInt("dishId", dishID);
        args.putParcelableArrayList("variants", variants);
        frag.setArguments(args);
        return frag;
    }

    //Aggiunge a arguments la lista di varianti, varianti selezionata da mostrare e note, ritorna l'istanza del Dialog
    public static SelectVariantsDialog newModificationInstance(int dishPosition, ArrayList<SelectedVariant> variants, String timeSelected, ArrayList<SelectedVariant> selectedVariants, String note, Context orderActivityContext) {
        SelectVariantsDialog.orderActivityContext = orderActivityContext;
        SelectVariantsDialog frag = new SelectVariantsDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList("variants", variants);
        args.putInt("dishPosition", dishPosition);
        //Gli elementi saranno true se la checkbox corrispondente è selezionata false altrimenti
        boolean[] selectedVariantsBoolean = new boolean[variants.size()];
        boolean[] selectedVariantsPlus = new boolean[variants.size()];
        for (int i = 0; i < variants.size(); i++) {
            int selectedVariantIndex = containsVariant(variants.get(i), selectedVariants);
            if (selectedVariantIndex != -1)
            {
                selectedVariantsBoolean[i] = true;
                selectedVariantsPlus[i] = selectedVariants.get(selectedVariantIndex).isPlus();
            }
            else {
                selectedVariantsBoolean[i] = false;
                selectedVariantsPlus[i] = true;
            }

        }
        args.putBooleanArray("selectedVariantsPlus", selectedVariantsPlus);

        args.putBooleanArray("selectedVariants", selectedVariantsBoolean);
        args.putString("note", note);
        args.putString("timeSelected", timeSelected);
        frag.setArguments(args);
        return frag;
    }

    private static int containsVariant(SelectedVariant variants, ArrayList<SelectedVariant> selectedVariantsList){
        for (int i = 0;i<selectedVariantsList.size();i++){
            if(variants.getVariantName().compareTo(selectedVariantsList.get(i).getVariantName()) == 0)
                return i;
        }
        return -1;
    }
}