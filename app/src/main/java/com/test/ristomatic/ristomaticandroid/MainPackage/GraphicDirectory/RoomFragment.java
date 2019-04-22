package com.test.ristomatic.ristomaticandroid.MainPackage.GraphicDirectory;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import static com.test.ristomatic.ristomaticandroid.Application.GlobalVariableApplication.DELAY_REQUEST_TIME;


public class RoomFragment extends Fragment {
    //Time in mills
    private Room room;
    private int fragmentId;
    private Handler handler;
    private Runnable runnable;
    private LinearLayout linearLayout;
    private View fragmentView;


    public RoomFragment(){
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


    public void init(List<Table> roomTables, RecyclerView recyclerView, MainActivity mainActivityContext) {
        room = new Room(roomTables, recyclerView, mainActivityContext);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(fragmentView == null){
            fragmentView =  inflater.inflate(R.layout.fragment_room, container, false);
            setLinearLayout();
            room.getRoomAdapter().notifyDataSetChanged();
        }
        return fragmentView;
    }


    private void setLinearLayout(){
        linearLayout = fragmentView.findViewById(R.id.linearLayout);
        linearLayout.addView(room.getRoomRecyclerView());
        int backgroundColor = ContextCompat.getColor(getContext(),R.color.backGroundMainActivity);
        linearLayout.setBackgroundColor(backgroundColor);
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
