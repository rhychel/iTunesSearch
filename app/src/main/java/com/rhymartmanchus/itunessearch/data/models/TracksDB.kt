package com.rhymartmanchus.itunessearch.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (
    tableName = "tracks"
)
data class TracksDB (

    @PrimaryKey
    @ColumnInfo (
        name = "id"
    )
    val id: String,

    @ColumnInfo (
        name = "name"
    )
    val name: String,

    @ColumnInfo (
        name = "artwork_url"
    )
    val artworkUrl: String,

    @ColumnInfo (
        name = "price"
    )
    val price: Double,

    @ColumnInfo (
        name = "genre"
    )
    val genre: String,

    @ColumnInfo (
        name = "long_description"
    )
    val longDescription: String,

    @ColumnInfo (
        name = "currency"
    )
    val currency: String
)