package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectedVariant implements Parcelable{
    private String variantName;
    private int idVariant;
    private boolean isPlus;

    public SelectedVariant(String variantName, int idVariant){
        setVariantName(variantName);
        setIdVariant(idVariant);
        isPlus = true;
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
}
