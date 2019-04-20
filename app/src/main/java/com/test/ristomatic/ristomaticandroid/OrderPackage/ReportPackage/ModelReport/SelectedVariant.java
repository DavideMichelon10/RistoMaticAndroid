package com.test.ristomatic.ristomaticandroid.OrderPackage.ReportPackage.ModelReport;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectedVariant implements Parcelable{
    private String selectedVariantName;
    private int selectedVariantId;


    public SelectedVariant(String selectedVariantName, int selectedVariantId){
        setSelectedVariantName(selectedVariantName);
        setSelectedVariantId(selectedVariantId);
    }

    protected SelectedVariant(Parcel in) {
        selectedVariantName = in.readString();
        selectedVariantId = in.readInt();
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

    public String getSelectedVariantName() {
        return selectedVariantName;
    }

    public void setSelectedVariantName(String selectedVariantName) {
        this.selectedVariantName = selectedVariantName;
    }

    public int getSelectedVariantId() {
        return selectedVariantId;
    }

    public void setSelectedVariantId(int selectedVariantId) {
        this.selectedVariantId = selectedVariantId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(selectedVariantName);
        dest.writeInt(selectedVariantId);
    }
}
