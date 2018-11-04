package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import java.util.ArrayList;
import java.util.List;
//portata, contiene i piatti di quella portata
public class Course {
    private List<SelectedDish> course;

    public Course(){
        course =new ArrayList<>();
    }
    public Course(List<SelectedDish> course){
        this.course = course;
    }
    public List<SelectedDish> getCourse() {
        return course;
    }

    public void setCourse(List<SelectedDish> course) {
        this.course = course;
    }
}
