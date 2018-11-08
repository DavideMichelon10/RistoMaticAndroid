package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import java.util.ArrayList;
import java.util.List;
//portata, contiene i piatti di quella portata
public class Course {
    private List<SelectedDish> selectedDishes;
    private int courseNumber;

    public Course(){
        selectedDishes =new ArrayList<>();
    }
    public Course(List<SelectedDish> selectedDishes){
        this.selectedDishes = selectedDishes;
    }

    public Course(int courseNumber) {
        this.courseNumber = courseNumber;
        selectedDishes =new ArrayList<>();
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
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