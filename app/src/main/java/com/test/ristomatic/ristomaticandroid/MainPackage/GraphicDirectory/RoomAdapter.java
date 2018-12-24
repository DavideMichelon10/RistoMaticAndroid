package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.ristomatic.ristomaticandroid.Application.ContextApplication;
import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.OrderPackage.OrderActivity;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<Table> tables;
    private Context context;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        if (viewHolder.getAdapterPosition() >= 0)
            viewHolder.setState(tables.get(viewHolder.getAdapterPosition()).getState());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Table table = getTables().get(position);
        holder.textViewId.setText(Integer.toString(table.getIdTable()));
        holder.setState(table.getState());
    }

    @Override
    public int getItemCount() {
        return getTables().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewId;
        public String State;
        View itemView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.itemView = itemView;
            //all'interno di list_item
            textViewId = (TextView) itemView.findViewById(R.id.textViewIdTavolo);
            /*TODO: rendere il seguente codice D.R.Y.*/
            textViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getState().compareTo("Occupato") != 0) {
                        FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                        SelectSeatsDialog selectSeatsDialog = SelectSeatsDialog.newInstance((String) textViewId.getText(), tables.get(getAdapterPosition()).getSubs());
                        selectSeatsDialog.show(fm, "selected_seats_fragment");
                    } else {
                        MainActivity.getMainViewModel().changeTableState(Integer.parseInt((String) textViewId.getText()), "Occupato");
                        Intent intent = new Intent(ContextApplication.getAppContext(), OrderActivity.class);
                        String idTable = (String) textViewId.getText();
                        intent.putExtra("idTavolo", idTable);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ContextApplication.getAppContext().startActivity(intent);
                    }
                }
            });
            textViewId.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                    SelectSeatsDialog selectSeatsDialog = SelectSeatsDialog.newInstance((String) textViewId.getText(), tables.get(getAdapterPosition()).getSubs());
                    selectSeatsDialog.show(fm, "selected_seats_fragment");
                    return false;
                }
            });
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
            if (State.compareTo("Occupato") == 0)
                textViewId.setBackgroundColor(0xFFAFAFAF);
            else
                this.textViewId.setBackgroundColor(0xFF32E567);
        }
    }
}
