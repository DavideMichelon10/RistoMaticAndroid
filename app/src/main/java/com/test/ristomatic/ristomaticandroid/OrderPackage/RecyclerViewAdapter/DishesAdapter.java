package com.test.ristomatic.ristomaticandroid.OrderPackage.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderViewModel;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
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
                    SelectedDish insertedDish = new SelectedDish(dishes.get(getAdapterPosition()).getDishName());
                    CoursesAdapter.getCourses()[1].addSelectedDish(insertedDish);
                    System.out.println(dishes.get(getAdapterPosition()).getDishName());
                    //Assegna a courseViewHolder il ViewHolder legato alla portata del piatto appena inserito
                    // nella lista SelectedDishes
                    CoursesAdapter.CourseViewHolder courseViewHolder = (CoursesAdapter.CourseViewHolder) ((RecyclerView)((OrderActivity)context)
                            .findViewById(R.id.recyclerViewCourses))
                            .findViewHolderForAdapterPosition(1);
                    int position = courseViewHolder.getRecyclerViewCourse().getAdapter().getItemCount();
                    System.out.println("POSITION: " + position);
                    courseViewHolder.getRecyclerViewCourse().getAdapter().notifyDataSetChanged();
                }
            });
        }
    }
}
