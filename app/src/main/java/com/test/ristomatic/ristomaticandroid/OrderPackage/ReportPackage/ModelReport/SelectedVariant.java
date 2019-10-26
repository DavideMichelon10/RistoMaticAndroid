package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

public class SelectedVariant implements Parcelable{
    private String variantName;
    private int idVariant;
    private boolean isPlus;


    public SelectedVariant(int idVariant, String variantName){
        setVariantName(variantName);
        setIdVariant(idVariant);
        isPlus = true;
    }

    @Ignore
    public  SelectedVariant(int idVariant, boolean isPlus){
        setIdVariant(idVariant); setPlus(isPlus);
    }
    protected SelectedVariant(Parcel in) {
        variantName = in.readString();
        idVariant = in.readInt();
        isPlus = in.readByte() != 0;
    }

    public static final Creator<SelectedVariant> CREATOR = new Creator<SelectedVariant>() {
        @Override
        public SelectedVariant createFromParcel(Parcel in) {
            return new SelectedVariant(in);
        }

        @Override
        public SelectedVariant[] newArray(int size) {
            return new SelectedVariant[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        SelectedVariant newVariant = (SelectedVariant) obj;

        return this.isPlus() == newVariant.isPlus && this.getIdVariant() == newVariant.getIdVariant();
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public int getIdVariant() {
        return idVariant;
    }

    public void setIdVariant(int idVariant) {
        this.idVariant = idVariant;
    }

    public boolean isPlus() {
        return isPlus;
    }

    public void setPlus(boolean plus) {
        isPlus = plus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(variantName);
        dest.writeInt(idVariant);
        dest.writeByte((byte) (isPlus ? 1 : 0));
    }

    @Override
    public String toString() {
        return "ID variante"+idVariant+ "Varianta name: "+variantName+"is plus: "+isPlus+"\n";
    }
}
