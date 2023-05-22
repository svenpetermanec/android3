package hr.tvz.android.listapetermanec

import android.os.Parcel
import android.os.Parcelable

class Exercise() : Parcelable {
    var id: Int = 0

    constructor(id: Int) : this() {
        this.id = id
    }

    constructor(parcel: Parcel) : this() {
        this.id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return id.hashCode()
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }
}