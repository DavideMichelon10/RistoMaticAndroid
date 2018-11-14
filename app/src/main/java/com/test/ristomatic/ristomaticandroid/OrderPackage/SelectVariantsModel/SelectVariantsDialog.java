package com.test.ristomatic.ristomaticandroid.OrderPackage.SelectVariantsModel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities.InsertDishUtilities;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.ArrayList;
import java.util.List;

public class SelectVariantsDialog extends DialogFragment {

    private static Context orderActivityContext;
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String dishName = getArguments().getString("dish");
        final ArrayList<String> variants = getArguments().getStringArrayList("variants");
        System.out.println("ARGUMENTS: " + this.getArguments().size());
        AlertDialog.Builder builder = new AlertDialog.Builder(this.orderActivityContext);
        final String[] variantsArray = new String[variants.size()];
        final boolean[] checkedVariants = new boolean[variants.size()];
        //Mostra le varianti con collegate una checkBox
        builder.setMultiChoiceItems(variants.toArray(variantsArray), checkedVariants, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                //Imposta a true le checkedVariants checkate
                checkedVariants[i] = isChecked;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Id del radioButton clickato
                int radioButtonId = ((RadioGroup)((OrderActivity) orderActivityContext).findViewById(R.id.flow_group)).getCheckedRadioButtonId();
                //Posizione della portata selezionata nella lista di portate
                int courseNumber = Integer.parseInt((String) ((RadioButton)((OrderActivity) orderActivityContext).findViewById(radioButtonId)).getText());
                RecyclerView recyclerViewCourses = ((OrderActivity) orderActivityContext).findViewById(R.id.recyclerViewCourses);
                List<String> selectedVariants = new ArrayList<>();
                for (int i=0; i<variants.size();i++) {
                    if(checkedVariants[i]){
                        selectedVariants.add(variants.get(i));
                    }
                }
                /*TODO: Implementare l'inserimento del piatto utilizzando InsertDishUtilities*/

                //Vero se la portata esiste già
                try{
                    SelectedDish insertedDish = new SelectedDish(dishName);
                    //Se la portata non esiste ne viene creata una nuova con il numero di portata e viene aggiunta alla lista
                    //successivamente viene chiamato il notifyItemInserted sulla recyclerViewCourses
                    if(!InsertDishUtilities.doesCourseExist(courseNumber)){
                        InsertDishUtilities.insertDishInNewCourse(courseNumber, insertedDish);
                    }

                    //se la portata esiste
                    else{
                        InsertDishUtilities.handleInExistingCourse(courseNumber, insertedDish);
                    }
                    //eccezione tirata quando cambi categoria e selezioni un piatto ed esso è nullo
                }catch (ArrayIndexOutOfBoundsException e){ }
            }
        });
        return builder.create();
    }

    //Aggiunge a arguments la lista di varianti da mostrare e ritorna l'istanza del Dialog
    public static SelectVariantsDialog newInstance(String dishName, ArrayList<String> variants, Context orderActivityContext) {
        SelectVariantsDialog.orderActivityContext = orderActivityContext;
        SelectVariantsDialog frag = new SelectVariantsDialog();
        Bundle args = new Bundle();
        args.putString("dish", dishName);
        args.putStringArrayList("variants",  variants);
        frag.setArguments(args);
        return frag;
    }
}