package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import java.util.ArrayList;
import java.util.List;

//piatto specifico selezionato da utente con eventuali varianti
public class SelectedDish {
    private String selectedDishName;
    private int selectedDishId;
    private List<SelectedVariant> selectedVariants;
    //numero volte piatto selezionato, è il numero che verrà visualizzato a fianco del nome del piatto
    private int timeSelected = 1;

    public SelectedDish(String selectedDishName, int selectedDishId){
        this.setSelectedDishId(selectedDishId);
        this.setSelectedDishName(selectedDishName);
        selectedVariants = new ArrayList<>();
    }
    public SelectedDish(String selectedDishName, int selectedDishId, List<SelectedVariant> selectedVariants){
        this.setSelectedDishId(selectedDishId);
        this.setSelectedDishName(selectedDishName);
        this.setSelectedVariants(selectedVariants);
    }

    public int getSelectedDishId() {
        return selectedDishId;
    }

    public void setSelectedDishId(int selectedDishId) {
        this.selectedDishId = selectedDishId;
    }

    public SelectedDish(String selectedDishName, List<SelectedVariant> selectedVariantName, int timeSelected){
        this.setSelectedDishName(selectedDishName); this.setSelectedVariants(selectedVariantName);
        this.timeSelected = timeSelected;
    }

    public int getTimeSelected() {
        return timeSelected;
    }

    public void setTimeSelected(int timeSelected) {
        this.timeSelected = timeSelected;
    }

    public String getSelectedDishName() {
        return selectedDishName;
    }

    public void setSelectedDishName(String selectedDishName) {
        this.selectedDishName = selectedDishName;
    }

    public List<SelectedVariant> getSelectedVariants() {
        return selectedVariants;
    }

    public void setSelectedVariants(List<SelectedVariant> selectedVariants) {
        this.selectedVariants = selectedVariants;
    }

    //compara due oggetti, vera solo se varianti uguali
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        SelectedDish selectedDish = (SelectedDish) o;
        if( this.getSelectedDishName().compareTo(selectedDish.getSelectedDishName()) == 0 &&
                this.getSelectedVariants().equals(selectedDish.getSelectedVariants())){
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ""+timeSelected+" name: "+selectedDishName;
    }
}
