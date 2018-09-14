package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    public List<Table> tables;
    private Context context;
    private int list_item;

    public RoomAdapter(List<Table> tables, Context context) {
        this.tables = tables;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Bisogna ancora creare la list_item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_layout, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Table table = tables.get(position);
        //holder contiene la view attuale collegata table.get(position)
        if(holder.textViewId.getText() != Integer.toString(table.getIdTable()))
        {
            holder.textViewId.setText(Integer.toString(table.getIdTable()));
        }
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewId;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            //all'interno di list_item
            textViewId = (TextView)itemView.findViewById(R.id.textViewIdTavolo);
        }
    }
}
