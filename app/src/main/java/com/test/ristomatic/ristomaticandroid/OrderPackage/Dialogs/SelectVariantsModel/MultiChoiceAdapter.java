package com.test.ristomatic.ristomaticandroid.OrderPackage.Dialogs.SelectVariantsModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedVariant;

import java.util.ArrayList;
import java.util.List;

public class MultiChoiceAdapter extends RecyclerView.Adapter<MultiChoiceAdapter.CheckItemViewHolder> {

    Context fragmentDialogContext;

    private List<SelectedVariant> variants;

    public List<SelectedVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<SelectedVariant> variants) {
        this.variants = variants;
    }


    List<Boolean> checkedVariants;

    public List<Boolean> getCheckedVariants() {
        return checkedVariants;
    }

    public void setCheckedVariants(List<Boolean> checkedVariants) {
        this.checkedVariants = checkedVariants;
    }

    public MultiChoiceAdapter(List<SelectedVariant> variants, Context fragmentDialogContext){
        setVariants(variants);
        this.fragmentDialogContext = fragmentDialogContext;
        setCheckedVariants(new ArrayList<Boolean>());
        for (int i=0;i<variants.size();i++){
            getCheckedVariants().add(false);
        }
    }

    public MultiChoiceAdapter(List<SelectedVariant> variants, List<Boolean> checkedVariants, Context fragmentDialogContext){
        setVariants(variants);
        this.fragmentDialogContext = fragmentDialogContext;
        setCheckedVariants(checkedVariants);
    }

    @NonNull
    @Override
    public CheckItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckItemViewHolder(new CheckBox(fragmentDialogContext));
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckItemViewHolder holder, int position) {
        holder.setText(variants.get(position).getVariantName());
        holder.check(checkedVariants.get(position));
    }

    @Override
    public int getItemCount() {
        return variants.size();
    }

    public List<SelectedVariant> getSelectedVariants(){
        List<SelectedVariant> selectedVariants = new ArrayList<>();
        for (int i=0;i<variants.size();i++){
            if(checkedVariants.get(i))
                selectedVariants.add(variants.get(i));
        }
        return selectedVariants;
    }

    public class CheckItemViewHolder extends RecyclerView.ViewHolder{

        CheckBox variant;
        public CheckItemViewHolder(View itemView) {
            super(itemView);
            variant = (CheckBox) itemView;
            variant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    if(checkBox.isChecked())
                        checkedVariants.set(getAdapterPosition(), true);
                    else
                        checkedVariants.set(getAdapterPosition(), false);
                }
            });
        }

        public void check(boolean b){
            variant.setChecked(b);
        }

        public void setText(String text){
            variant.setText(text);
        }
    }
}
