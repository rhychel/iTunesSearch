package com.rhymartmanchus.itunessearch.data.models

import com.google.gson.annotations.SerializedName

data class TrackRaw (

    @SerializedName("trackId")
    val id: String,

    @SerializedName("trackName")
    val trackName: String,

    @SerializedName("artworkUrl100")
    val artworkUrl: String,

    @SerializedName("trackPrice")
    val price: Double,

    @SerializedName("primaryGenreName")
    val genre: String,

    @SerializedName("longDescription")
    val longDescription: String,

    @SerializedName("currency")
    val currency: String

)