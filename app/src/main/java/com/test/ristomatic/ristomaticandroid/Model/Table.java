package com.test.ristomatic.ristomaticandroid.Model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Table {
    private int idTable;
    private String state;
    private List<Date> reservations;
    private int idRoom;
    //Numero di sottoconti presenti su questo tavolo
    private int subs;


    public Table(int idTable, String state,List<Date> reservations){
        setIdTable(idTable);
        setState(state);
        setReservations(reservations);
        setSubs(1);
    }


    public Table(int idTable, String state,List<Date> reservations, int subs){
        setIdTable(idTable);
        setState(state);
        setReservations(reservations);
        setSubs(subs);
    }


    public int getSubs() {
        return subs;
    }

    public void setSubs(int subs) {
        this.subs = subs;
    }


    public void increseSubs() {
        this.subs++;
    }

    public void decreseSubs() {
        this.subs--;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }


    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }


    public List<Date> getReservations() {
        return reservations;
    }

    public void setReservations(List<Date> reservations) {
        this.reservations = reservations;
    }


    public void addReservations(Date reservation) {
        this.reservations.add(reservation);
    }
    @Override
    public String toString() {
        String s = "IN TOSTRING " + this.getIdTable() + " " + this.getState() + " ";
        //for (Date date : reservations) {
            //s+= date.toString();
        //}
        return s;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return getIdTable() == table.idTable &&
                getIdRoom() == table.idRoom &&
                Objects.equals(getState(), table.state);
    }


    @Override
    public int hashCode() {
        return Objects.hash(idTable, state, idRoom);
    }
}
