package com.test.ristomatic.ristomaticandroid.Model;


import java.util.Objects;

public class Table {
    private int idTable;
    private String state;
    private int idRoom;


    public Table(int idTable, String state){
        setIdTable(idTable);
        setState(state);
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
