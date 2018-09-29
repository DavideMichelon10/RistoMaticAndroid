package com.test.ristomatic.ristomaticandroid.OrderPackage.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class UserDish {
    private Dish slectedDish;
    private List<Variant> selectedVariants;

    public UserDish(Dish slectedDish, List<Variant> selectedVariants) {
        this.slectedDish = slectedDish;
        this.selectedVariants = selectedVariants;
    }

    public UserDish(Dish slectedDish) {
        this.slectedDish = slectedDish;
        this.selectedVariants = new ArrayList<>();
    }

    public Dish getSlectedDish() {
        return slectedDish;
    }

    public List<Variant> getSelectedVariants() {
        return selectedVariants;
    }

    public void setSlectedDish(Dish slectedDish) {
        this.slectedDish = slectedDish;
    }

    public void setSelectedVariants(List<Variant> selectedVariants) {
        this.selectedVariants = selectedVariants;
    }
}
