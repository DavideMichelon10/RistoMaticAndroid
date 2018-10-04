package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.ristomatic.ristomaticandroid.MainPackage.MainActivity;
import com.test.ristomatic.ristomaticandroid.Model.Room;
import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;


public class TablesFragment extends Fragment {
    //Time in mills
    final int DELAY_REQUEST_TIME = 3000;
    private Room room;
    private int fragmentId;
    private Handler handler;
    private Runnable runnable;
    private LinearLayout linearLayout;
    private View v;
    public TablesFragment(){
    }
    public int getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public void init(List<Table> roomTables, RecyclerView recyclerView){

        room = new Room(roomTables, recyclerView);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                MainActivity.getMainViewModel().getTablesUpToDate(getFragmentId());
                handler.postDelayed(runnable, DELAY_REQUEST_TIME);
            }
        };
    }
    static int counter = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(v == null){
            v =  inflater.inflate(R.layout.fragment_blank, container, false);
            linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout);
            linearLayout.addView(room.getMyRecyleView());
            linearLayout.setBackgroundColor(0xFFFFFF);
        }
        room.getRoomAdapter().notifyDataSetChanged();
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 0);
    }
}
