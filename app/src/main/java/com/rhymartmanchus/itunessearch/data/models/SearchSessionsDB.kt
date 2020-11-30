package com.rhymartmanchus.itunessearch.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (
    tableName = "search_sessions"
)
data class SearchSessionsDB (

    @PrimaryKey
    @ColumnInfo (
        name = "id"
    )
    val id: Int,

    @ColumnInfo (
        name = "last_visit"
    )
    val lastVisit: String

)