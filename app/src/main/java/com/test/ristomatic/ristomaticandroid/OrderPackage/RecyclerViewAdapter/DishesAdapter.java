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

import com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderViewModel;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.SelectedDishesAdapter;
import com.test.ristomatic.ristomaticandroid.R;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
                    //Posizione nella lista di portate della portata corrente
                    int coursePosition = 0;
                    int courseNumber = Integer.parseInt((String) ((RadioButton)((OrderActivity)context)
                            .findViewById(radioButtonIdid))
                            .getText());
                    //Vero se la portata esiste già
                    boolean courseExistance = false;
                    SelectedDish insertedDish = new SelectedDish(dishes.get(getAdapterPosition()).getDishName());
                    //Scorre la lista e controlla se esiste già la portata selezionata
                    for (int i=0;i< CoursesAdapter.getCourses().size();i++)
                    {
                        if(CoursesAdapter.getCourses().get(i).getCourseNumber() == (courseNumber))
                        {
                            courseExistance = true;
                            break;
                        }
                    }
                    //Se la portata non esiste ne viene creata una nuova con il numero di portata e viene aggiunta alla lista
                    //successivamente viene chiamato il notifyItemInserted sulla recyclerViewCourses
                    if(!courseExistance){
                        //La portata che verrà inserita
                        Course courseInserted = new Course(courseNumber);
                        //Aggiungi il piatto da inserire alla portata
                        courseInserted.addSelectedDish(insertedDish);
                        //Se la portata da inserire non va inserita in una posizione già occupata da un altra portata
                        if( (CoursesAdapter.getCourses().size()) < courseNumber)
                            CoursesAdapter.getCourses().add(courseInserted);
                        else
                            CoursesAdapter.getCourses().add(courseNumber-1, courseInserted);/**/
                        ((RecyclerView)((OrderActivity)context).findViewById(R.id.recyclerViewCourses)).getAdapter().notifyItemInserted(courseNumber-1);
                    }

                    //se la portata esiste
                    else
                    {
                        //ricava la posizione della portata esistente nella lista
                        for (int i=0;i< CoursesAdapter.getCourses().size();i++)
                        {
                            if(CoursesAdapter.getCourses().get(i).getCourseNumber() == (courseNumber))
                            {
                                coursePosition = i;
                                break;
                            }
                        }
                        //Aggiungo insertedDish nella lista di piatti selezionati all'interno della portata
                        CoursesAdapter.getCourses().get(coursePosition).addSelectedDish(insertedDish);
                        //Ricavo la recyclerViewCourses
                        final RecyclerView recyclerViewCourses = ((OrderActivity)context)
                                .findViewById(R.id.recyclerViewCourses);
                        //Chiamo il notifyItemInserted sull'adapter della portata e come position passo la grandezza della lista
                        //di piatti selezionati meno 1
                        ((CoursesAdapter.CourseViewHolder)recyclerViewCourses
                                .findViewHolderForAdapterPosition(coursePosition))
                                .getRecyclerViewCourse()
                                .getAdapter()
                                .notifyItemInserted(CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().size()-1);
                    }

                    /*
                    CoursesAdapter.getCourses().get(courseNumber).addSelectedDish(insertedDish);
                    //Assegna a courseViewHolder il ViewHolder legato alla portata del piatto appena inserito
                    // nella lista SelectedDishes
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final RecyclerView recyclerViewCourses = ((OrderActivity)context)
                            .findViewById(R.id.recyclerViewCourses);
                    ((CoursesAdapter.CourseViewHolder)recyclerViewCourses
                            .findViewHolderForAdapterPosition(courseNumber))
                            .getRecyclerViewCourse()
                            .getAdapter()
                            .notifyItemInserted(CoursesAdapter.getCourses().get(courseNumber).getAllSelectedDishes().size()-1);*/
                }
            });
        }
    }
}