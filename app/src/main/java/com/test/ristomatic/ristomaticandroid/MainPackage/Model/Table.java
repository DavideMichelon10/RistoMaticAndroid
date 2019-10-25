package com.test.ristomatic.ristomaticandroid.MainPackage.Model;


import java.util.Objects;

public class Table {
    private int idTable;
    private String tableName;
    private boolean isOccupied;
    private int idRoom;


    public Table(int idTable, String tableName, boolean isOccupied){
        setIdTable(idTable);
        setTableName(tableName);
        setOccupied(isOccupied);
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public boolean getOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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
        return getTableName() == table.tableName &&
                getIdRoom() == table.idRoom &&
                Objects.equals(getOccupied(), table.isOccupied);
    }

    @Override
    public String toString() {
        return "Table{" +
                "idTable=" + idTable +
                ", tableName='" + tableName + '\'' +
                ", isOccupied=" + isOccupied +
                ", idRoom=" + idRoom +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, isOccupied, idRoom);
    }
}
