package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel;

import java.util.List;

public class Course {
    private int diCourse;
    private List<UserDish> selectedDishes;

    public Course(int diCourse) {
        this.diCourse = diCourse;
    }

    public Course(List<UserDish> selectedDishes, int diCourse) {
        this.selectedDishes = selectedDishes;
        this.diCourse = diCourse;
    }

    public List<UserDish> getSelectedDishes() {
        return selectedDishes;
    }

    public int getDiCourse() {
        return diCourse;
    }

    public void setSelectedDishes(List<UserDish> selectedDishes) {
        this.selectedDishes = selectedDishes;
    }

    public void setDiCourse(int diCourse) {
        this.diCourse = diCourse;
    }
}
