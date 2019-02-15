package com.awolity.settingsviews

import android.os.Parcel
import android.os.Parcelable
import android.view.View

internal class SettingSavedState : View.BaseSavedState {

    var disabledColorValue: Int = 0
    var titleColorValue: Int = 0
    var descriptionColorValue: Int = 0

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.disabledColorValue)
        dest.writeInt(this.titleColorValue)
        dest.writeInt(this.descriptionColorValue)
    }

    constructor(superState: Parcelable) : super(superState) {}

    constructor(`in`: Parcel) : super(`in`) {
        this.disabledColorValue = `in`.readInt()
        this.titleColorValue = `in`.readInt()
        this.descriptionColorValue = `in`.readInt()
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
