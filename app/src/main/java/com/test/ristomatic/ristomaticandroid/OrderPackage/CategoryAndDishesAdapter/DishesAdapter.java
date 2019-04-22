package com.test.ristomatic.ristomaticandroid.OrderPackage.CategoryAndDishesAdapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities.InsertDishUtilities;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderViewModel;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.OrderPackage.Dialogs.SelectVariantsModel.SelectVariantsDialog;
import com.test.ristomatic.ristomaticandroid.R;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;

import java.util.ArrayList;
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
            this.button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    FragmentManager fm = ((OrderActivity)context).getSupportFragmentManager();
                    SelectVariantsDialog selectVariantsDialog = createAlertDialog();
                    selectVariantsDialog.show(fm, "fragment_alert");
                    return true;
                }
            });

            this.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int radioButtonIdid = ((RadioGroup)((OrderActivity)context).findViewById(R.id.flow_group)).getCheckedRadioButtonId();
                    final int courseNumber = Integer.parseInt((String) ((RadioButton)((OrderActivity)context)
                            .findViewById(radioButtonIdid))
                            .getText());
                    try{
                        insertDish(courseNumber);
                        //eccezione tirata quando cambi categoria e selezioni un piatto ed esso è nullo
                    }catch (ArrayIndexOutOfBoundsException e){ }
                }
            });
        }


        public SelectVariantsDialog createAlertDialog(){
            SelectedDish insertedDish = new SelectedDish(dishes.get(getAdapterPosition()).getDishName());
            List<String> variants = OrderViewModel.getInitDB().getDishModelDao().getVariantsNameOfDish(dishes.get(getAdapterPosition()).getIdDish());
            SelectVariantsDialog selectVariantsDialog = SelectVariantsDialog.newInsertionInstance(insertedDish.getSelectedDishName(), (ArrayList<String>) variants, context);
            return selectVariantsDialog;
        }


        public void insertDish(final int courseNumber) throws ArrayIndexOutOfBoundsException{
            SelectedDish insertedDish = new SelectedDish(dishes.get(getAdapterPosition()).getDishName());
            if(InsertDishUtilities.doesCourseExist(courseNumber)){
                InsertDishUtilities.handleInExistingCourse(courseNumber, insertedDish);
            }
            else{
                InsertDishUtilities.insertDishInNewCourse(courseNumber, insertedDish);
                // scrolla recyclerView portate fino, va fuori da if perchè comune
                new Runnable(){
                    @Override
                    public void run() {
                        RecyclerView recyclerViewCourses = ((OrderActivity)context).findViewById(R.id.recyclerViewCourses);
                        int coursePos = InsertDishUtilities.isCoursePositionOccupied(courseNumber);
                        if(coursePos != -1){
                            recyclerViewCourses.scrollToPosition(coursePos-1);
                        }else{
                            recyclerViewCourses.scrollToPosition(recyclerViewCourses.getAdapter().getItemCount() -1);
                        }
                    }
                }.run();
            }
        }
    }
}