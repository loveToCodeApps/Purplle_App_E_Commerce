package com.example.purpleapp

import android.os.Parcel
import android.os.Parcelable

data class OfferProductData(
    val id :String,
    var heading: String,
    var sale: String,
    var disc: String,
    var mrp: String,
    var image1: String,
    var img_name:String
)