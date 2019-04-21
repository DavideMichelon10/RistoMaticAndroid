package com.test.ristomatic.ristomaticandroid.OrderPackage.Dialogs.SelectVariantsModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedVariant;
import com.test.ristomatic.ristomaticandroid.R;

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

    public MultiChoiceAdapter(List<SelectedVariant> variants, List<Boolean> checkedVariants, List<Boolean> selectedVariantsPlus, Context fragmentDialogContext){
        setVariants(variants);
        this.fragmentDialogContext = fragmentDialogContext;
        setCheckedVariants(checkedVariants);
        setRadioButtonPlusMinus(selectedVariantsPlus);
    }

    private void setRadioButtonPlusMinus(List<Boolean> selectedVariantsPlus){
        for (int i = 0;i < selectedVariantsPlus.size(); i++) {
            variants.get(i).setPlus(selectedVariantsPlus.get(i));
        }
    }

    @NonNull
    @Override
    public CheckItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(fragmentDialogContext).inflate(R.layout.select_variant, parent, false);
        return new CheckItemViewHolder(cardView);
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
        holder.setRadioButton(variants.get(position).isPlus());
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
        RadioGroup plusMinusRadioGroup;
        RadioButton plusRadioButton;
        RadioButton minusRadioButton;
        public CheckItemViewHolder(View itemView) {
            super(itemView);
            variant = itemView.findViewById(R.id.checkBox);
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
            plusMinusRadioGroup = itemView.findViewById(R.id.plus_minus_radiogroup);
            plusRadioButton = plusMinusRadioGroup.findViewById(R.id.plus);
            plusRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    variants.get(getAdapterPosition()).setPlus(true);
                }
            });
            minusRadioButton = plusMinusRadioGroup.findViewById(R.id.minus);
            minusRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    variants.get(getAdapterPosition()).setPlus(false);
                }
            });

        }

        public void setRadioButton(boolean isPlus){
            if(isPlus)
                plusMinusRadioGroup.check(R.id.plus);
            else
                plusMinusRadioGroup.check(R.id.minus);
        }

        public void check(boolean b){
            variant.setChecked(b);
        }

        public void setText(String text){
            variant.setText(text);
        }
    }
}
