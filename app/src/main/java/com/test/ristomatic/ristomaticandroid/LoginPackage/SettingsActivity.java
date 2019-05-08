package com.test.ristomatic.ristomaticandroid.LoginPackage;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.R;

public class SettingsActivity extends AppCompatActivity {

    TextView textViewCoursesNumber;
    TextView textViewColumnTables;
    TextView textViewCopertiStart;
    EditText editTextCoursesNumber;
    EditText editTextCopertiStart;
    RadioGroup radioGroupColumnTables;
    public static SharedPreferences userPreferences = ContextApplication.getAppContext().getSharedPreferences("userPreferences", MODE_PRIVATE);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        textViewCoursesNumber = findViewById(R.id.textViewCoursesNumber);
        textViewCoursesNumber.setText("NUMERO PORTATE: ");
        editTextCoursesNumber = findViewById(R.id.editTextCoursesNumber);
        textViewColumnTables = findViewById(R.id.textViewColumnTables);
        textViewColumnTables.setText("NUMERO COLONNE TAVOLI: ");
        radioGroupColumnTables = findViewById(R.id.flow_group);
        textViewCopertiStart = findViewById(R.id.textViewCopertiStart);
        textViewCopertiStart.setText("COPERTI INIZIALI: ");
        editTextCopertiStart = findViewById(R.id.editTextCopertiStart);
        setRadioButton();
        setDefaultSettings();

    }

    private void setRadioButton(){
        RadioGroup.LayoutParams layoutParams;
        for(int i = 0; i< 5; i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i+1);
            radioButton.setText(""+(i+1));
            layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT);
            radioGroupColumnTables.addView(radioButton, -1, layoutParams);
        }
    }

    public void setDefaultSettings(){
        editTextCoursesNumber.setText(Integer.toString(userPreferences.getInt("COURSES_NUMBER", 3)));
        editTextCopertiStart.setText(Integer.toString(userPreferences.getInt("VALUE_COPERTI_START", 2)));
        radioGroupColumnTables.check(userPreferences.getInt("NUMBER_COLUMN_TABLES", 4));
    }

    public void saveSettings(View view){
        SharedPreferences.Editor editor = userPreferences.edit();
        int selectedId = radioGroupColumnTables.getCheckedRadioButtonId();
        int courseNumber = setEditText(editTextCoursesNumber);
        int copertiStart = setEditText(editTextCopertiStart);



        editor.putInt("COURSES_NUMBER", courseNumber);
        editor.putInt("NUMBER_COLUMN_TABLES", selectedId);
        editor.putInt("VALUE_COPERTI_START", copertiStart);
        editor.commit();
        onBackPressed();
    }

    private int setEditText(EditText editText) {
        int value;
        if(editText.getText().toString().equals("") || editText.getText().toString().equals("0")){
            value = 4;
        }else{
            value = Integer.parseInt(editText.getText().toString());
        }
        return value;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
