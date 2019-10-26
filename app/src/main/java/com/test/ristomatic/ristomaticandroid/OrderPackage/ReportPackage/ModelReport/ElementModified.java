package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

public class ElementModified {
    private int idElement; //CORTO
    private String description; //DESCRI
    private int portata; //NUMERO PORTATA
    private int quantity; //QUAN
    private int quantityModified; //quan_modi


    public ElementModified(int idElement, String description, int portata, int quantity, int quantityModified){
        this.idElement = idElement;
        this.description = description;
        this.portata = portata;
        this.quantity = quantity;
        this.quantityModified = quantityModified;
    }
    public int getIdElement() {
        return idElement;
    }

    public void setIdElement(int idElement) {
        this.idElement = idElement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityModified() {
        return quantityModified;
    }

    public void setQuantityModified(int quantityModified) {
        this.quantityModified = quantityModified;
    }

    public int getPortata() {
        return portata;
    }

    public void setPortata(int portata) {
        this.portata = portata;
    }

    @Override
    public String toString() {
        return  "ID: "+idElement+" description: "+description+" quantita: "+quantity+" modificata: "+quantityModified+"\n";
    }
}
