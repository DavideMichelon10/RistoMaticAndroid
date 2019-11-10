package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderSummaryDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedVariant;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryDialog extends DialogFragment {
    private static OrderActivity.SendReport sendReport;
    private static OrderActivity orderActivity;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        ArrayList<Course> courses = (ArrayList<Course>) getArguments().getSerializable("courses");
        String summary = "";
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        for (Course course: courses){
            TextView courseTextView = new TextView(getContext());
            courseTextView.setText("  PORTATA : " + course.getCourseNumber());
            courseTextView.setBackgroundColor(Color.BLUE);
            courseTextView.setTextColor(Color.WHITE);
            courseTextView.setLayoutParams(params);
            linearLayout.addView(courseTextView);
            boolean isEven = true;
            for(SelectedDish selectedDish : course.getAllSelectedDishes()){
                TextView dishTextView = new TextView(getContext());
                dishTextView.setText("   " + selectedDish.getTimeSelected() + "x" + selectedDish.getSelectedDishName());
                if(isEven){
                    dishTextView.setBackgroundColor(0xFFa3c2c2);
                    isEven = false;
                }
                else{
                    dishTextView.setBackgroundColor(0xFFb3cccc);
                    isEven = true;
                }

                linearLayout.addView(dishTextView);
                dishTextView.setLayoutParams(params);
                boolean isVariantEven = true;
                for (SelectedVariant selectedVariant : selectedDish.getSelectedVariants()){
                    String sign = "-- ";
                    if(selectedVariant.isPlus())
                        sign = "++ ";
                    TextView variantTextView = new TextView(getContext());
                    variantTextView.setText("     " + sign + selectedVariant.getVariantName());
                    if(isVariantEven){
                        variantTextView.setBackgroundColor(0xFFf0f5f5);
                        isVariantEven = false;
                    } else {
                        variantTextView.setBackgroundColor(0xFFe0ebeb);
                        isVariantEven = true;
                    }
                    variantTextView.setLayoutParams(params);
                    linearLayout.addView(variantTextView);
                }
            }
        }
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.addView(linearLayout);
        builder.setTitle("RIEPILOGO");
        builder.setView(scrollView);
        builder.setPositiveButton("INVIA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendReport.execute();
                orderActivity.goBack();
            }
        });
        Dialog d = builder.create();
        return d;
    }


    public static OrderSummaryDialog getInstance(ArrayList<Course> courses, OrderActivity.SendReport sendReport, OrderActivity orderActivity) {
        OrderSummaryDialog.sendReport = sendReport;
        OrderSummaryDialog.orderActivity = orderActivity;
        OrderSummaryDialog frag = new OrderSummaryDialog();
        Bundle args = new Bundle();
        args.putSerializable("courses", courses);
        frag.setArguments(args);
        return frag;
    }
}
