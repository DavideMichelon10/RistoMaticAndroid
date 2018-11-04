package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;

import java.util.List;

//piatto specifico selezionato da utente con eventuali varianti
public class SelectedDish {
    private DishModel selectedDish;
    private List<VariantModel> variantsSelected;
    //numero volte piatto selezionato, è il numero che verrà visualizzato a fianco del nome del piatto
    private int timeSelected;

    public SelectedDish(DishModel selectedDish, List<VariantModel> variantsSelected){
        this.selectedDish = selectedDish; this.variantsSelected = variantsSelected;
    }

    public DishModel getSelectedDish() {
        return selectedDish;
    }

    public void setSelectedDish(DishModel selectedDish) {
        this.selectedDish = selectedDish;
    }

    public List<VariantModel> getVariantsSelected() {
        return variantsSelected;
    }

    public void setVariantsSelected(List<VariantModel> variantsSelected) {
        this.variantsSelected = variantsSelected;
    }

    public int getTimeSelected() {
        return timeSelected;
    }

    public void setTimeSelected(int timeSelected) {
        this.timeSelected = timeSelected;
    }
}
