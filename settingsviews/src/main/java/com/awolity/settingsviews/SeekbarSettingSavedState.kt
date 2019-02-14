package com.awolity.settingsviews

import android.os.Parcel
import android.os.Parcelable
import android.view.View

internal class SeekbarSettingSavedState : View.BaseSavedState {

    var disabledColorValue: Int = 0
    var titleColorValue: Int = 0
    var descriptionColorValue: Int = 0
    var max: Int = 0
    var position: Int = 0

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.disabledColorValue)
        dest.writeInt(this.titleColorValue)
        dest.writeInt(this.descriptionColorValue)
        dest.writeInt(this.max)
        dest.writeInt(this.position)
    }

    constructor(superState: Parcelable) : super(superState) {}

    constructor(`in`: Parcel) : super(`in`) {
        this.disabledColorValue = `in`.readInt()
        this.titleColorValue = `in`.readInt()
        this.descriptionColorValue = `in`.readInt()
        this.max = `in`.readInt()
        this.position = `in`.readInt()
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<SeekbarSettingSavedState> =
            object : Parcelable.Creator<SeekbarSettingSavedState> {
                override fun createFromParcel(source: Parcel): SeekbarSettingSavedState {
                    return SeekbarSettingSavedState(source)
                }

                override fun newArray(size: Int): Array<SeekbarSettingSavedState?> {
                    return arrayOfNulls<SeekbarSettingSavedState>(size)
                }
            }
    }
}
