package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport.SelectedVariant;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;

public class SelectedVariantsAdapter extends RecyclerView.Adapter<SelectedVariantsAdapter.SelectedVariantViewHolder> {
    Context context;
    List<SelectedVariant> variantsSelected;

    public SelectedVariantsAdapter(Context context, List<SelectedVariant> variantsSelected){
        this.context = context;
        this.variantsSelected = variantsSelected;
    }

    @NonNull
    @Override
    public SelectedVariantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_selected_variant,parent, false);
        SelectedVariantViewHolder selectedVariantViewHolder = new SelectedVariantViewHolder(v);
        return selectedVariantViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedVariantViewHolder holder, int position) {
        holder.variantSelected.setText(variantsSelected.get(position).getSelectedVariantName());
    }

    @Override
    public int getItemCount() {
        return variantsSelected.size();
    }

    public class SelectedVariantViewHolder extends RecyclerView.ViewHolder{
        TextView variantSelected;
        public SelectedVariantViewHolder(View itemView) {
            super(itemView);
            variantSelected = (TextView) itemView.findViewById(R.id.variantSelected);
        }
    }
}
