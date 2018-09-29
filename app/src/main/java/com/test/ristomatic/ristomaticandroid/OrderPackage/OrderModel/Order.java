package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Course> courses;
    private int idTable;
    public Order(int idTable) {
        this.idTable = idTable;
        courses = new ArrayList<>();
    }

    public Order(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }
}
