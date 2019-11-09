package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import java.util.List;

public class ElementModified {
    private int idElement; //CORTO
    private String description; //DESCRI
    private int portata; //NUMERO PORTATA
    private int quantity; //QUAN
    private int quantityModified; //quan_modi
    private int riga_variazione;
    private List<ElementModified> variantsAdded;

    public ElementModified(int idElement, String description, int portata, int quantity, int quantityModified, int riga_variazione){
        this.idElement = idElement;
        this.description = description;
        this.portata = portata;
        this.quantity = quantity;
        this.quantityModified = quantityModified;
        this.riga_variazione = riga_variazione;
    }

    public ElementModified(int idElement, String description, int portata, int quantity, int quantityModified, int riga_variazione,  List<ElementModified> variantsAdded){
        this.variantsAdded = variantsAdded;
        this.idElement = idElement;
        this.description = description;
        this.portata = portata;
        this.quantity = quantity;
        this.quantityModified = quantityModified;
        this.riga_variazione = riga_variazione;
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
        String string =  "ID: "+idElement+" description: "+description+" quantita: "+quantity+" modificata: "+quantityModified+" rig tavolo: "+riga_variazione;

        /*for(ElementModified e : variantsAdded){
            string += e.toString();
        }*/
        return string;
    }

    public List<ElementModified> getVariantsAdded() {
        return variantsAdded;
    }

    public void setVariantsAdded(List<ElementModified> variantsAdded) {
        this.variantsAdded = variantsAdded;
    }
}
