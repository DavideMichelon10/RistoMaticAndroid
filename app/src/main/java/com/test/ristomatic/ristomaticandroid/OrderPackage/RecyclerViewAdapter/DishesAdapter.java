package com.test.ristomatic.ristomaticandroid.OrderPackage.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderViewModel;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.SelectedDishesAdapter;
import com.test.ristomatic.ristomaticandroid.R;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;

import java.util.List;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.DishViewHolder> {
    private List<DishModel> dishes;
    private Context context;
    public DishesAdapter(List<DishModel> dishes, Context context) {
        this.dishes = dishes; this.context = context;
    }

    public void setDishes(List<DishModel> dishes){
        this.dishes = dishes;
    }
    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_layout, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(v);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        holder.button.setText(dishes.get(position).getDishName()+"\n"+dishes.get(position).getDishPrice());
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {
        private Button button;

        public DishViewHolder(View v) {
            super(v);
            this.button = (Button) v.findViewById(R.id.button);
            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int radioButtonIdid = ((RadioGroup)((OrderActivity)context).findViewById(R.id.flow_group)).getCheckedRadioButtonId();
                    int position = Integer.parseInt((String) ((RadioButton)((OrderActivity)context)
                            .findViewById(radioButtonIdid))
                            .getText());

                    if(CoursesAdapter.getCourses()[position-1] == null){
                        CoursesAdapter.getCourses()[position-1] = new Course();
                        ((RecyclerView)((OrderActivity)context).findViewById(R.id.recyclerViewCourses))
                                .getAdapter().notifyDataSetChanged();
                    }
                    //CoursesAdapter.getCourses()[position-1] = new Course();
                    SelectedDish insertedDish = new SelectedDish(dishes.get(getAdapterPosition()).getDishName());

                    CoursesAdapter.getCourses()[position-1].addSelectedDish(insertedDish);
                    System.out.println("SIZE: " + CoursesAdapter.getCourses()[position-1].getAllSelectedDishes().size());
                    //Assegna a courseViewHolder il ViewHolder legato alla portata del piatto appena inserito
                    // nella lista SelectedDishes
                    CoursesAdapter.CourseViewHolder courseViewHolder = (CoursesAdapter.CourseViewHolder) ((RecyclerView)((OrderActivity)context)
                            .findViewById(R.id.recyclerViewCourses))
                            .findViewHolderForAdapterPosition();
                    System.out.println(Integer.toString(courseViewHolder.getAdapterPosition()));
                    courseViewHolder.getRecyclerViewCourse().getAdapter().notifyDataSetChanged();
                }
            });
        }
    }
}
