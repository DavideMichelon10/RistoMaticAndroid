package com.test.ristomatic.ristomaticandroid.OrderPackage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.Application.VolleyCallbackObject;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities.InsertDishUtilities;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.R;

import org.json.JSONException;
import org.json.JSONObject;


public class OrderActivity extends AppCompatActivity {
    private OrderViewModel orderViewModel;

    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewDishes;
    private  RecyclerView recyclerViewCourses;
    private RadioGroup rgp;
    private ProgressBar progress, progressAttendiScontrino;
    private TextView tIdTable, tImporto;
    private Button b, bRichiama;
    private int seatsNumber, idTable, idRoom;
    private String tableName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        InsertDishUtilities.setContext(this);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        bRichiama = findViewById(R.id.retryRichiama);
        progress = findViewById(R.id.progressBar);
        progressAttendiScontrino = findViewById(R.id.progressBarAttendiScontrino);
        tIdTable = findViewById(R.id.tableName);
        tImporto = findViewById(R.id.importo);
        Intent intent = getIntent();
        idRoom = intent.getIntExtra("idRoom", -1);

        tableName = intent.getStringExtra("tableName");
        idTable = intent.getIntExtra("idTable", 0);
        seatsNumber = intent.getIntExtra("coperti",0);

        tIdTable.setText(tableName);

        final boolean richiama = intent.getBooleanExtra("richiama",false);

        initializeVM(richiama,seatsNumber,this);
        createCourseSelection();

        addSeatsNumberToReport();

        bRichiama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bRichiama.setVisibility(View.INVISIBLE);
                progress.setVisibility(View.VISIBLE);
                initializeVM(richiama,seatsNumber, ContextApplication.getAppContext());
            }
        });


        OrderViewModel.getStatusCodeCases().observe(this, new Observer<OrderViewModel.StatusCodeCases>() {
            @Override
            public void onChanged(@Nullable OrderViewModel.StatusCodeCases statusCodeCases) {

                switch (statusCodeCases){
                    case STATUS_CODE_500:
                        bRichiama.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.INVISIBLE);
                        break;
                    case STATUS_CODE_SCONTRINO_500:
                        progressAttendiScontrino.setVisibility(View.GONE);

                        break;
                    case REQUEST_SCONTRINO:
                        progressAttendiScontrino.setVisibility(View.VISIBLE);

                        break;

                    case STATUS_CODE_SCONTRINO_200:
                        progressAttendiScontrino.setVisibility(View.GONE);
                        //OrderActivity.super.onBackPressed();


                }
            }


        });

        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("ON LONG CLICK");
                showDialogGetScontrino(idTable, idRoom, orderViewModel.getOrderRepository());
                return true;
            }
        });
    }

    public void showDialogGetScontrino(int idTable, int idRoom, OrderRepository orderRepository){
        FragmentManager fm = this.getSupportFragmentManager();
        GetScontriniDialog scontrinoDialog = GetScontriniDialog.newInstance(idTable, idRoom, orderRepository);
        scontrinoDialog.show(fm, "selected_scontrini_fragment");

    }

    public void initializeVM(boolean richiama, int seatsNumber, final Context context){
        b = findViewById(R.id.sendReport);
        b.setEnabled(false);

        if(richiama){
            orderViewModel.initRichiama(idTable, idRoom,this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    orderViewModel.getRichiama(new VolleyCallbackObject() {
                        @Override
                        public void onSuccess(final JSONObject result) {
                            runOnUiThread( new Runnable(){
                                @Override
                                public void run() {
                                    initializeRecyclerViewCategories();
                                    initializeRecyclerViewDishes();
                                    initializeRecyclerViewCourses(context);
                                    float importo = 0;
                                    try {
                                        importo = result.getInt("importo");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    tImporto.setText(importo+"€");
                                    b.setEnabled(true);
                                    progress.setVisibility(View.INVISIBLE);
                                }
                            });
                        }
                    }, idTable, context);
                }
            }).start();
        }else{
            orderViewModel.init(idTable, seatsNumber, idRoom,this);
            initializeRecyclerViewCourses(context);
            initializeRecyclerViewCategories();
            initializeRecyclerViewDishes();
            progress.setVisibility(View.INVISIBLE);
            b.setEnabled(true);
        }
    }


    private void createCourseSelection() {
        rgp = findViewById(R.id.flow_group);
        RadioGroup.LayoutParams rprms;
        for(int i = 0; i< GlobalVariableApplication.getCoursesNumber(); i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(""+i);
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.MATCH_PARENT);
            rgp.addView(radioButton, -1, rprms);
            if(i==1) {
                radioButton.performClick();
            }
        }
    }


    public void initializeRecyclerViewCategories(){
        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategories.setHasFixedSize(true);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(this,1));
        recyclerViewCategories.setAdapter(OrderViewModel.getAdaptersContainer().getCategoriesAdapter());
    }


    public void initializeRecyclerViewDishes(){
        recyclerViewDishes = findViewById(R.id.recyclerViewDishes);
        recyclerViewDishes.setHasFixedSize(true);
        recyclerViewDishes.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewDishes.setAdapter(OrderViewModel.getAdaptersContainer().getDishesAdapter());
    }


    public void initializeRecyclerViewCourses(Context context){
        recyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setHasFixedSize(true);
        recyclerViewCourses.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerViewCourses.setAdapter(OrderViewModel.getAdaptersContainer().getCoursesAdapter());

    }


    public void sendReport(View view) throws JSONException {
        orderViewModel.sendReport();
        //new SendReport().execute();
        super.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        AlertDialog backPressedDialog = buildAlertDialog();
        backPressedDialog.show();
    }

    public AlertDialog buildAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.back_to_table));
        builder.setPositiveButton(getString(R.string.Si),new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                OrderActivity.super.onBackPressed();
            }

        });
        builder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    private void addSeatsNumberToReport(){
        /*TODO: aggiornare con id articolo riferito al coperto*/
        int seatsNumber = getIntent().getIntExtra("coperti", -1);
        if(seatsNumber != -1){
            SelectedDish testSeats = new SelectedDish("COPERTI", 0);
            InsertDishUtilities.insertDishInNewCourse(0, testSeats, seatsNumber);
        }
    }


    private class SendReport extends AsyncTask<Void, String ,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                orderViewModel.sendReport();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return(null);
        }
    }
}
