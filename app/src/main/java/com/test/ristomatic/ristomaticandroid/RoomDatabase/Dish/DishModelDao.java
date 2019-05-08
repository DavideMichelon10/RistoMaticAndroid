package com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedVariant;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Dish.DishModel;
import com.test.ristomatic.ristomaticandroid.RoomDatabase.Variant.VariantModel;

import java.util.List;
import java.util.Map;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface DishModelDao {
    @Query("select * from DishModel")
    List<DishModel> getAllDishes();

    @Query("select * from DishModel " +
            "join DishCategoryJoin on DishModel.idDish = DishCategoryJoin.idDish " +
            "where idCategory = :idCategory " +
            "order by DishModel.dish_name")
    List<DishModel> getSelectedDishes(int idCategory);

    @Query("select * from DishModel where idDish = :idDish")
    DishModel getDish(int idDish);

    @Query("select variantName from VariantModel " +
            "JOIN CategoryVariantJoin on VariantModel.idVariant = CategoryVariantJoin.idVariant " +
            "JOIN CategoryModel on CategoryModel.idCategory = CategoryVariantJoin.idCategory " +
            "JOIN DishCategoryJoin on DishCategoryJoin.idCategory = CategoryModel.idCategory " +
            "WHERE DishCategoryJoin.idDish = :idDish ORDER BY variantName")
    List<String> getVariantsNameOfDish(int idDish);

    @Query("select variantName from VariantModel " +
            "JOIN CategoryVariantJoin on VariantModel.idVariant = CategoryVariantJoin.idVariant " +
            "JOIN CategoryModel on CategoryModel.idCategory = CategoryVariantJoin.idCategory " +
            "JOIN DishCategoryJoin on DishCategoryJoin.idCategory = CategoryModel.idCategory " +
            "JOIN DishModel on DishModel.idDish = DishCategoryJoin.idDish "+
            "WHERE DishModel.dish_name = :dishName ORDER BY variantName")
    List<String> getVariantsNameOfDish(String dishName);


    @Query("select VariantModel.idVariant, variantName, 1 as isPlus from VariantModel " +
            "JOIN CategoryVariantJoin on VariantModel.idVariant = CategoryVariantJoin.idVariant " +
            "JOIN CategoryModel on CategoryModel.idCategory = CategoryVariantJoin.idCategory " +
            "JOIN DishCategoryJoin on DishCategoryJoin.idCategory = CategoryModel.idCategory " +
            "JOIN DishModel on DishModel.idDish = DishCategoryJoin.idDish "+
            "WHERE DishModel.dish_name = :dishName ORDER BY variantName")
    List<SelectedVariant> getVariantsNameAndIdOfDish(String dishName);

    @Insert(onConflict = REPLACE)
    void addDish(DishModel dishModel);

    @Query("DELETE FROM DishModel")
    public void nukeTableDish();
}
