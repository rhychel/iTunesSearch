package com.rhymartmanchus.itunessearch.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track (
    val id: String,
    val name: String,
    val artworkUrl: String,
    val price: Double,
    val genre: String,
    val longDescription: String,
    val currency: String
) : Parcelable