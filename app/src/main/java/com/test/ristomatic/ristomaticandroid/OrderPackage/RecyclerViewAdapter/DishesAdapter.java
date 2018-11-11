package com.test.ristomatic.ristomaticandroid.OrderPackage.RecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;

import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.CoursesAdapter;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedDish;
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
                    //Posizione nella lista di portate della portata corrente
                    int coursePosition = 0;
                    int courseNumber = Integer.parseInt((String) ((RadioButton)((OrderActivity)context)
                            .findViewById(radioButtonIdid))
                            .getText());
                    //Vero se la portata esiste già
                    try{
                        SelectedDish insertedDish = new SelectedDish(dishes.get(getAdapterPosition()).getDishName());
                    //Se la portata non esiste ne viene creata una nuova con il numero di portata e viene aggiunta alla lista
                    //successivamente viene chiamato il notifyItemInserted sulla recyclerViewCourses
                    if(!doesCourseExist(courseNumber)){
                        insertDishInNewCourse(courseNumber, insertedDish);
                    }

                    //se la portata esiste
                    else{
                        handleInExistingCourse(courseNumber, insertedDish);
                    }
                    //eccezione tirata quando cambi categoria e selezioni un piatto ed esso è nullo
                    }catch (ArrayIndexOutOfBoundsException e){ }
                }
            });
        }

        //Ritorna la posizione del piatto se il piatto è già presente nella lista, -1 altrimenti
        private int findDishInCourse(SelectedDish insertedDish, int coursePosition){
            for (int i=0;i< CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().size();i++){
                if(CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().get(i).getSelectedDishName() == insertedDish.getSelectedDishName()){
                    return i;
                }
            }
            return -1;
        }
        //Cambia timeSelectedDish
        private void changeTimeSelectedDish(int coursePosition, int dishPosition, RecyclerView recyclerViewCourses)
        {
            int timeSelected = CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().get(dishPosition).getTimeSelected();
            CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().get(dishPosition).setTimeSelected(++timeSelected);
            ((CoursesAdapter.CourseViewHolder) recyclerViewCourses
                    .findViewHolderForAdapterPosition(coursePosition))
                    .getRecyclerViewCourse()
                    .getAdapter()
                    .notifyItemChanged(dishPosition);
        }
        //Inserisce il piatto all'interno della portata
        private  void insertDishInCourse(int coursePosition, SelectedDish insertedDish, RecyclerView recyclerViewCourses)
        {
            //Aggiungo insertedDish nella lista di piatti selezionati all'interno della portata
            CoursesAdapter.getCourses().get(coursePosition).addSelectedDish(insertedDish);
            //Ricavo la recyclerViewCourses

            //Chiamo il notifyItemInserted sull'adapter della portata e come position passo la grandezza della lista
            //di piatti selezionati meno 1
            ((CoursesAdapter.CourseViewHolder) recyclerViewCourses
                    .findViewHolderForAdapterPosition(coursePosition))
                    .getRecyclerViewCourse()
                    .getAdapter()
                    .notifyItemInserted(CoursesAdapter.getCourses().get(coursePosition).getAllSelectedDishes().size() - 1);
        }
        //Inserisce il piatto nel caso la portata esista già
        private void handleInExistingCourse(int courseNumber, final SelectedDish insertedDish)
        {
            int coursePosition = 0;
            final RecyclerView recyclerViewCourses = ((OrderActivity)context)
                    .findViewById(R.id.recyclerViewCourses);

                //ricava la posizione della portata esistente nella lista
                for (int i=0;i< CoursesAdapter.getCourses().size();i++){
                    if(CoursesAdapter.getCourses().get(i).getCourseNumber() == (courseNumber))
                    {
                        coursePosition = i;
                        break;
                    }
                }
            final int dishPosition = findDishInCourse(insertedDish, coursePosition);
            try {
                if(dishPosition != -1) {
                    changeTimeSelectedDish(coursePosition, dishPosition, recyclerViewCourses);
                }
                else {
                    insertDishInCourse(coursePosition, insertedDish, recyclerViewCourses);
                }
            }catch (NullPointerException exeption){
                final int finalCoursePosition = coursePosition;
                final int finalCoursePosition1 = coursePosition;
                recyclerViewCourses.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if((CoursesAdapter.CourseViewHolder)recyclerViewCourses
                                .findViewHolderForAdapterPosition(finalCoursePosition) != null)
                        {
                            if(dishPosition != -1) {
                                changeTimeSelectedDish(finalCoursePosition1, dishPosition, recyclerViewCourses);
                            }
                            else {
                                insertDishInCourse(finalCoursePosition1, insertedDish, recyclerViewCourses);
                            }
                        }
                    }
                },30);
            }
        }

        //Inserisce il piatto nel caso la portata non esiste
        private void insertDishInNewCourse(int courseNumber, SelectedDish insertedDish)
        {
            //La portata che verrà inserita
            Course courseInserted = new Course(courseNumber);
            //Aggiungi il piatto da inserire alla portata
            courseInserted.addSelectedDish(insertedDish);
            //Se indexCoursePosition è uguale a -1 allora basta fare l'add
            int indexCoursePosition = isCoursePositionOccupied(courseNumber);
            //Se la portata da inserire non va inserita in una posizione già occupata da un altra portata
            if( indexCoursePosition == -1){
                CoursesAdapter.getCourses().add(courseInserted);
                ((RecyclerView)((OrderActivity)context).findViewById(R.id.recyclerViewCourses)).getAdapter().notifyItemInserted(CoursesAdapter.getCourses().size());
            }
            else {
                CoursesAdapter.getCourses().add(indexCoursePosition, courseInserted);
                ((RecyclerView)((OrderActivity)context).findViewById(R.id.recyclerViewCourses)).getAdapter().notifyItemInserted(indexCoursePosition);
            }
        }

        //Controlla se la portata da inserire esiste già
        private boolean doesCourseExist(int courseNumber)
        {
            for (int i=0;i< CoursesAdapter.getCourses().size();i++){
                if(CoursesAdapter.getCourses().get(i).getCourseNumber() == (courseNumber))
                {
                    return true;
                }
            }
            return false;
        }
        //Ritorna -1 se la portata va aggiunta in fondo e l'indice di dova va inserito se non va in fondo
        private int isCoursePositionOccupied(int courseNumber){
            System.out.println("isCoursePositionOccupied");
            for (int i=0;i< CoursesAdapter.getCourses().size();i++){
                if(courseNumber < CoursesAdapter.getCourses().get(i).getCourseNumber()){
                    return i;
                }
            }
            return -1;
        }
    }
}