package com.awolity.settingsviews;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

class RadiogroupSettingSavedState extends View.BaseSavedState {

    private int disabledColorValue, titleColorValue, descriptionColorValue,  radioButtonLabelColorValue;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.disabledColorValue);
        dest.writeInt(this.titleColorValue);
        dest.writeInt(this.descriptionColorValue);
        dest.writeInt(this.radioButtonLabelColorValue);
    }

    RadiogroupSettingSavedState(Parcelable superState) {
        super(superState);
    }

    RadiogroupSettingSavedState(Parcel in) {
        super(in);
        this.disabledColorValue = in.readInt();
        this.titleColorValue = in.readInt();
        this.descriptionColorValue = in.readInt();
        this.radioButtonLabelColorValue = in.readInt();
    }

    public static final Parcelable.Creator<RadiogroupSettingSavedState> CREATOR = new Parcelable.Creator<RadiogroupSettingSavedState>() {
        @Override
        public RadiogroupSettingSavedState createFromParcel(Parcel source) {
            return new RadiogroupSettingSavedState(source);
        }

        @Override
        public RadiogroupSettingSavedState[] newArray(int size) {
            return new RadiogroupSettingSavedState[size];
        }
    };

    public int getDisabledColorValue() {
        return disabledColorValue;
    }

    public void setDisabledColorValue(int disabledColorValue) {
        this.disabledColorValue = disabledColorValue;
    }

    public int getTitleColorValue() {
        return titleColorValue;
    }

    public void setTitleColorValue(int titleColorValue) {
        this.titleColorValue = titleColorValue;
    }

    public int getDescriptionColorValue() {
        return descriptionColorValue;
    }

    public void setDescriptionColorValue(int descriptionColorValue) {
        this.descriptionColorValue = descriptionColorValue;
    }

    public int getRadioButtonLabelColorValue() {
        return radioButtonLabelColorValue;
    }

    public void setRadioButtonLabelColorValue(int radioButtonLabelColorValue) {
        this.radioButtonLabelColorValue = radioButtonLabelColorValue;
    }
}
