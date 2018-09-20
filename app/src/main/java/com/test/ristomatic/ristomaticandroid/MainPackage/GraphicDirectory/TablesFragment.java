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
import com.test.ristomatic.ristomaticandroid.MainPackage.MainViewModel;
import com.test.ristomatic.ristomaticandroid.Model.Room;
import com.test.ristomatic.ristomaticandroid.Model.Table;
import com.test.ristomatic.ristomaticandroid.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TablesFragment extends Fragment {
    private Room room;
    int fragmentId;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private int idFragment;
    private View v;
    private Timer timer;
    int i = 1333;
    TimerTask hourlyTask;
    public TablesFragment(){
        System.out.println("IN FRAGMENT");
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
        System.out.println("ON CREATE");
        timer = new Timer();
        /*hourlyTask = new TimerTask () {
            @Override
            public void run () {
                room.getRoomAdapter().getTables().add(new Table(++i,"a"));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        room.getRoomAdapter().notifyDataSetChanged();
                    }
                });
            }
        };
        timer.schedule (hourlyTask, 0l, 1000);   // 1000*10*60 every 10 minut
*/
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
            linearLayout.setBackgroundColor(0xFF000000);
        }
        room.getRoomAdapter().notifyDataSetChanged();
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        //hourlyTask.cancel();
        System.out.println("in pausa");
        timer.cancel();
        timer.purge();
    }

    @Override
    public void onResume() {
        super.onResume();
        //room.getRoomAdapter().getTables().get(0).setIdTable(++i);
        //room.getRoomAdapter().notifyDataSetChanged();
        System.out.println("in onResume");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.getMainViewModel().getTablesUpToDate(getFragmentId());
                System.out.println("CONTATORE: " + counter++);
            }
        },0,3000);
    }

    public int getFragmentId() {
        return fragmentId;
    }

    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }
}
