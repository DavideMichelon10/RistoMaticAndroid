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

    public SelectedDish(int selectedDishId,String selectedDishName, List<SelectedVariant> selectedVariantName, int timeSelected){
        this.setSelectedDishName(selectedDishName); this.setSelectedVariants(selectedVariantName);
        this.timeSelected = timeSelected; this.selectedDishId = selectedDishId;
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
    //N.B Non viene considerato il timeSelecte --> per fare modifica
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        SelectedDish selectedDish = (SelectedDish) o;

        return this.getSelectedDishId() == selectedDish.getSelectedDishId() &&
                this.getSelectedVariants().equals(selectedDish.getSelectedVariants());
    }

    @Override
    public String toString() {
        StringBuilder var = new StringBuilder();
        var.append(" name: ").append(selectedDishName).append("  Volte selezionato: ").append(timeSelected).append("\n");
        for(SelectedVariant selectedVariant : selectedVariants){
            var.append(selectedVariant.toString());
        }
        return var.toString();
    }
}
