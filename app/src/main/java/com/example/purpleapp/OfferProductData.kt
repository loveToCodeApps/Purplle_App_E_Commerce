package com.example.purpleapp

import android.os.Parcel
import android.os.Parcelable

data class OfferProductData(
    val id :String?,
    var heading: String?,
    var sale: String?,
    var disc: String?,
    var mrp: String?,
    var image1: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(heading)
        parcel.writeString(sale)
        parcel.writeString(disc)
        parcel.writeString(mrp)
        parcel.writeString(image1)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OfferProductData> {
        override fun createFromParcel(parcel: Parcel): OfferProductData {
            return OfferProductData(parcel)
        }

        override fun newArray(size: Int): Array<OfferProductData?> {
            return arrayOfNulls(size)
        }
    }
}
