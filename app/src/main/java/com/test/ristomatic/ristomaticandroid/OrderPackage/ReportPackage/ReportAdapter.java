package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.R;

import static com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication.COURSES_NUMBER;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.CourseViewHolder> {
    private Context context;
    private Course[] courses;

    public ReportAdapter(Context context){
        this.context = context;
        courses = new Course[COURSES_NUMBER];
    }

    public ReportAdapter(Context context, Course[] courses){
        this.context = context;
        this.courses = courses;
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_course,parent, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(v);
        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.courseNumber.setText("AFADF");
    }

    @Override
    public int getItemCount() {
        int counter = 0;
        for (int i = 0; i < courses.length; i ++)
            if (courses[i] != null)
                counter ++;
        return  counter;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseNumber;
        RecyclerView courses;
        public CourseViewHolder(View itemView) {
            super(itemView);
            courseNumber = (TextView) itemView.findViewById(R.id.courseNumber);
            courses= (RecyclerView) itemView.findViewById(R.id.recyclerViewSelectedDishes);
        }
    }
}
