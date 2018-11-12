package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.ArrayList;
import java.util.List;

//adapter per singola portata e gestisce tutti i piatti al suo interno
public class SelectedDishesAdapter extends RecyclerView.Adapter<SelectedDishesAdapter.SelectedDishViewHolder> {
    Context context;
    public List<SelectedDish> selectedDishes;


    public SelectedDishesAdapter(Context context, List<SelectedDish> selectedDishes){
        this.context = context;
        this.selectedDishes = selectedDishes;
    }

    @Override
    public SelectedDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_selected_dish,parent, false);
        SelectedDishViewHolder selectedDishViewHolder = new SelectedDishViewHolder(v);
        return selectedDishViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedDishViewHolder holder, int position) {
        holder.timeSelected.setText(" "+(selectedDishes.get(position).getTimeSelected()));
        holder.dishName.setText(selectedDishes.get(position).getSelectedDishName());
    }

    @Override
    public int getItemCount() {
        return selectedDishes.size();
    }

    public class SelectedDishViewHolder extends RecyclerView.ViewHolder {
        TextView timeSelected;
        TextView dishName;
        ImageView deleteImage;
        RecyclerView selectedVariants;
        public SelectedDishViewHolder(final View itemView) {
            super(itemView);
            dishName = (TextView) itemView.findViewById(R.id.dishName);
            timeSelected = (TextView) itemView.findViewById(R.id.timeSelected);
            selectedVariants = (RecyclerView) itemView.findViewById(R.id.recyclerViewVariants);
            deleteImage = (ImageView) itemView.findViewById(R.id.deleteImage);

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //((RecyclerView)((OrderActivity)context).findViewById(R.id.recyclerViewCourses))
                      //      .getAdapter();
                            //.notifyItemRemoved(courseNumber-1);
                    int position = getAdapterPosition();
                    try{
                        selectedDishes.remove(position);
                        notifyItemRemoved(position);
                        //((RecyclerView)((OrderActivity)context).findViewById(R.id.recyclerViewSelectedDishes))
                          //      .getAdapter()
                            //    .notifyItemRemoved(position);
                    }catch (ArrayIndexOutOfBoundsException exception){ }

                    if(selectedDishes.size() == 0){
                        
                    }


                   //CoursesAdapter.getCourses().get();

                }
            });
        }

    }
}
