package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.OrderPackage.Dialogs.SelectVariantsModel.SelectVariantsDialog;
import com.test.ristomatic.ristomaticandroid.OrderPackage.InsertDishUtilities.InsertDishUtilities;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderViewModel;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.Course;
import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedVariant;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//adapter per singola portata e gestisce tutti i piatti al suo interno
public class SelectedDishesAdapter extends RecyclerView.Adapter<SelectedDishesAdapter.SelectedDishViewHolder> {
    Context context;
    Course course;

    public SelectedDishesAdapter(Context context, Course course){
        this.course = course;
        this.context = context;
    }

    @Override
    public SelectedDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_selected_dish,parent, false);
        SelectedDishViewHolder selectedDishViewHolder = new SelectedDishViewHolder(v);
        return selectedDishViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedDishViewHolder holder, int position) {
        List<SelectedVariant> variantsSelected = course.getAllSelectedDishes().get(position).getSelectedVariants();
        holder.timeSelected.setText(" "+(course.getAllSelectedDishes().get(position).getTimeSelected()));
        holder.dishName.setText(course.getAllSelectedDishes().get(position).getSelectedDishName());
        holder.selectedVariants.setHasFixedSize(false);
        holder.selectedVariants.setLayoutManager(new GridLayoutManager(context, 1));
        holder.selectedVariants.setAdapter(new SelectedVariantsAdapter(context, variantsSelected));
    }

    @Override
    public int getItemCount() {
        return course.getAllSelectedDishes().size();
    }

    public class SelectedDishViewHolder extends RecyclerView.ViewHolder {
        TextView timeSelected;
        TextView dishName;
        ImageView deleteImage;
        RecyclerView selectedVariants;
        ImageButton plusDishButton;
        ImageButton minsuDishButton;

        public SelectedDishViewHolder(final View itemView) {
            super(itemView);
            dishName = (TextView) itemView.findViewById(R.id.dishName);
            timeSelected = (TextView) itemView.findViewById(R.id.timeSelected);
            selectedVariants = (RecyclerView) itemView.findViewById(R.id.recyclerViewVariants);
            deleteImage = (ImageView) itemView.findViewById(R.id.deleteImage);
            minsuDishButton = itemView.findViewById(R.id.plus_dish_button);
            plusDishButton= itemView.findViewById(R.id.minus_dish_button);
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    //evita crash se non arriva a rimuovere elemento prima di notify
                    try{
                        course.getAllSelectedDishes().remove(position);
                        notifyItemRemoved(position);
                    }catch (ArrayIndexOutOfBoundsException exception){ }
                    RecyclerView s = (RecyclerView)((OrderActivity)context).findViewById(R.id.recyclerViewCourses);
                    //elimina portata se vuota
                    if(course.getAllSelectedDishes().size() == 0){
                        int pos = CoursesAdapter.getCourses().indexOf((course));
                        CoursesAdapter.getCourses().remove(pos);
                        s.getAdapter().notifyItemRemoved(pos);
                    }
                }
            });

            minsuDishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int coursePosition = InsertDishUtilities.getCoursePositionFromNumber(course.getCourseNumber());
                    RecyclerView recyclerViewCourses = ((OrderActivity)context).findViewById(R.id.recyclerViewCourses);
                    InsertDishUtilities.changeTimeSelectedDish(coursePosition, getAdapterPosition(), recyclerViewCourses, -1);
                }
            });

            plusDishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int coursePosition = InsertDishUtilities.getCoursePositionFromNumber(course.getCourseNumber());
                    RecyclerView recyclerViewCourses = ((OrderActivity)context).findViewById(R.id.recyclerViewCourses);
                    InsertDishUtilities.changeTimeSelectedDish(coursePosition, getAdapterPosition(), recyclerViewCourses, 1);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<SelectedVariant> variants = (ArrayList<SelectedVariant>) OrderViewModel.getInitDB().getDishModelDao().getVariantsNameAndIdOfDish(dishName
                            .getText().toString());
                    ArrayList<SelectedVariant> selectedVariantsList = (ArrayList<SelectedVariant>) ((SelectedVariantsAdapter)selectedVariants
                            .getAdapter()).variantsSelected;
                    String note = "";
                    selectedVariantsList = new ArrayList<>(selectedVariantsList);
                    if(selectedVariantsList.size() > 0 && !containsNote(variants, selectedVariantsList))
                        note = selectedVariantsList.remove(selectedVariantsList.size()-1).getVariantName();
                    String timeSelectedString = timeSelected.getText().toString();
                    SelectVariantsDialog selectVariantsDialog = SelectVariantsDialog.newModificationInstance(getAdapterPosition(),
                            variants, timeSelectedString, selectedVariantsList, note, course.getCourseNumber(), context);
                    FragmentManager fm = ((OrderActivity)context).getSupportFragmentManager();
                    selectVariantsDialog.show(fm, "fragment_alert");
                }
            });
        }


        private boolean containsNote(ArrayList<SelectedVariant> variants, ArrayList<SelectedVariant> selectedVariantsList){
            List<String> variantsNames = new ArrayList<>();
            for (int i = 0;i<variants.size();i++){
                if(variants.get(i).getVariantName().compareTo(selectedVariantsList.get(selectedVariantsList.size()-1).getVariantName()) == 0){
                    return true;
                }
            }
            return false;
        }


        private List<SelectedVariant> hashMapToSelectedVariants(Map<Integer, String> selectedVariants){
            List<Integer> id = new ArrayList<Integer>(selectedVariants.keySet());
            List<String> name = new ArrayList<String>(selectedVariants.values());
            List<SelectedVariant> selectedVariantsConverted = new ArrayList<SelectedVariant>();
            for (int i = 0; i < id.size(); i++){
                selectedVariantsConverted.add(new SelectedVariant( id.get(i),name.get(i)));
            }
            return selectedVariantsConverted;
        }
    }
}
