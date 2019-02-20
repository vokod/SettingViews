package com.awolity.settingviews

import android.os.Parcel
import android.os.Parcelable
import android.view.View

internal class SettingSavedState : View.BaseSavedState {

    var disabledColorValue: Int = 0
    var titleColorValue: Int = 0
    var descriptionColorValue: Int = 0
    var titleText: String =""
    var descriptionText: String =""
    var radiobutton1Text: String =""
    var radiobutton2Text: String =""
    var icon: Int = 0
    var checkable: Boolean = false
    var checked: Boolean = false

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.disabledColorValue)
        dest.writeInt(this.titleColorValue)
        dest.writeInt(this.descriptionColorValue)
        dest.writeString(this.titleText)
        dest.writeString(this.descriptionText)
        dest.writeString(this.radiobutton1Text)
        dest.writeString(this.radiobutton2Text)
        dest.writeInt(this.icon)
        dest.writeInt(if (checkable) 1 else 0)
        dest.writeInt(if (checked) 1 else 0)
    }

    constructor(superState: Parcelable) : super(superState) {}

    constructor(`in`: Parcel) : super(`in`) {
        this.disabledColorValue = `in`.readInt()
        this.titleColorValue = `in`.readInt()
        this.descriptionColorValue = `in`.readInt()
        this.titleText = `in`.readString()!!
        this.descriptionText = `in`.readString()!!
        this.radiobutton1Text = `in`.readString()!!
        this.radiobutton2Text = `in`.readString()!!
        this.icon = `in`.readInt()
        this.checkable = `in`.readInt() != 0
        this.checked = `in`.readInt() != 0
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<SettingSavedState> =
            object : Parcelable.Creator<SettingSavedState> {
                override fun createFromParcel(source: Parcel): SettingSavedState {
                    return SettingSavedState(source)
                }

                override fun newArray(size: Int): Array<SettingSavedState?> {
                    return arrayOfNulls<SettingSavedState>(size)
                }
            }
    }
}
