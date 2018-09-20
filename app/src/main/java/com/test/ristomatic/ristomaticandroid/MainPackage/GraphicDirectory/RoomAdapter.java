package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<Table> tables;
    private Context context;
    private int list_item;

    public RoomAdapter(List<Table> tables, Context context) {
        this.setTables(tables);
        this.context = context;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Bisogna ancora creare la list_item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        if(viewHolder.getAdapterPosition() >= 0)
            viewHolder.setState(tables.get(viewHolder.getAdapterPosition()).getState());
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Table table = getTables().get(position);
        //holder contiene la view attuale collegata table.get(position)
        if(holder.textViewId.getText() != Integer.toString(table.getIdTable())) {
            holder.textViewId.setText(Integer.toString(table.getIdTable()));
        }
        if(holder.getState() != table.getState())
            holder.setState(table.getState());
    }

    @Override
    public int getItemCount() {
        return getTables().size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewId;
        public String State;
        View itemView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.itemView = itemView;
            //all'interno di list_item
            textViewId = (TextView)itemView.findViewById(R.id.textViewIdTavolo);
            textViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //gestisci click
                    System.out.println("clicked");
                    System.out.println(textViewId.getText().toString());
                }
            });
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
            if(State.compareTo("Occupato") == 0)
                textViewId.setBackgroundColor(0xFFFF0000);
            else
                this.textViewId.setBackgroundColor(0xFF00FF00);
        }
    }
}
