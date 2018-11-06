package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import java.util.ArrayList;
import java.util.List;
//portata, contiene i piatti di quella portata
public class Course {
    private List<SelectedDish> selectedDishes;

    public Course(){
        selectedDishes =new ArrayList<>();
    }
    public Course(List<SelectedDish> selectedDishes){
        this.selectedDishes = selectedDishes;
    }
    public List<SelectedDish> getAllSelectedDishes() {
        return selectedDishes;
    }

    public void setSelectedDishes(List<SelectedDish> selectedDishes) {
        this.selectedDishes = selectedDishes;
    }
    public void addSelectedDish(SelectedDish selectedDish){
        selectedDishes.add(selectedDish);
    }
}
