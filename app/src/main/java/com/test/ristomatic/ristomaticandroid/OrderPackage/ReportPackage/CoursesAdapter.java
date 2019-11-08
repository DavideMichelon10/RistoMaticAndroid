package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    private Context context;
    private static List<Course> courses;

    public CoursesAdapter(Context context, List<Course> courses){
        this.context = context;
        this.setCourses(courses);
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static void setCourses(List<Course> courses) {
        CoursesAdapter.courses = courses;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_course,parent, false);
        CourseViewHolder courseViewHolder = new CourseViewHolder(v);
        return courseViewHolder;
    }

    //creare lista con elementi in position di array course e passarlo a SelectedDishesAdapter
    //Si occuperà SelectedDishesAdapter di creare la lista di piatti selezionati per quella portata
    @Override
    public void onBindViewHolder(final CourseViewHolder holder, final int position) {
        System.out.println("ON BIND VIEW HOLDER COURSE");
        Course course = getCourses().get(position);
        holder.courseNumber.setText("Portata: " + getCourses().get(position).getCourseNumber());
        holder.courses.setHasFixedSize(false);
        holder.courses.setLayoutManager(new GridLayoutManager(context, GlobalVariableApplication.NUMBER_COLUMN_REPORT));
        final SelectedDishesAdapter selectedDishesAdapter = new SelectedDishesAdapter(context, course);
        holder.courses.setAdapter(selectedDishesAdapter);
    }


    @Override
    public int getItemCount() {
        return  getCourses().size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseNumber;
        RecyclerView courses;
        public CourseViewHolder(View itemView) {
            super(itemView);
            courseNumber = itemView.findViewById(R.id.courseNumber);
            courses = itemView.findViewById(R.id.recyclerViewSelectedDishes);
        }
        public RecyclerView getRecyclerViewCourse() {
            return courses;
        }
    }
}
