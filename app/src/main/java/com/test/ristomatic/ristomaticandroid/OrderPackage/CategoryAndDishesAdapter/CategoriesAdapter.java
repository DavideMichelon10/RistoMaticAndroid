package com.test.ristomatic.ristomaticandroid.OrderPackage.CategoryAndDishesAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderViewModel;
import com.test.ristomatic.ristomaticandroid.R;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Category.CategoryModel;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>  {
    private List<CategoryModel> categories;


    public CategoriesAdapter(List<CategoryModel> categories){
        this.categories = categories;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_layout, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(v);
        return  viewHolder;
    }


    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.button.setText(categories.get(position).getCategoryName());
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private Button button;


        public CategoryViewHolder(View v) {
            super(v);
            this.button = (Button) v.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                    OrderViewModel.getDishedAdapter().setDishes(OrderViewModel.getDishModelDao().getSelectedDishes(getAdapterPosition()+1));
                    OrderViewModel.getDishedAdapter().notifyDataSetChanged();
                }
            });
        }
    }
}
