package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import java.util.List;

//piatto specifico selezionato da utente con eventuali varianti
public class SelectedDish {
    private String selectedDishName;
    private List<String> selectedVariantName;
    //numero volte piatto selezionato, è il numero che verrà visualizzato a fianco del nome del piatto
    private int timeSelected;

    public SelectedDish(String selectedDishName){
        this.setSelectedDishName(selectedDishName);
    }
    public SelectedDish(String selectedDishName, List<String> selectedVariantName){
        this.setSelectedDishName(selectedDishName); this.setSelectedVariantName(selectedVariantName);
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

    public List<String> getSelectedVariantName() {
        return selectedVariantName;
    }

    public void setSelectedVariantName(List<String> selectedVariantName) {
        this.selectedVariantName = selectedVariantName;
    }
}
