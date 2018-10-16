package com.test.ristomatic.ristomaticandroid.RoomDatabase;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

// Note: No annotation required at this class definition.
public class DishWithVariants {
    @Embedded
    public DishModel dishModel;
    @Relation(parentColumn = "idDish", entityColumn = "idDish", entity = VariantModel.class)
    public List<VariantModel> variantsForDish;
}
