package com.test.ristomatic.ristomaticandroid.MainPackage.Model;


import java.util.Objects;

public class Table {
    private int idTable;
    private boolean isOccupied;
    private int idRoom;


    public Table(int idTable, boolean isOccupied){
        setIdTable(idTable);
        setOccupied(isOccupied);
    }


    public boolean getOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
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
                Objects.equals(getOccupied(), table.isOccupied);
    }


    @Override
    public int hashCode() {
        return Objects.hash(idTable, isOccupied, idRoom);
    }
}
