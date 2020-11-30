package com.rhymartmanchus.itunessearch.data

import com.rhymartmanchus.itunessearch.data.models.SearchSessionsDB
import com.rhymartmanchus.itunessearch.data.models.TrackRaw
import com.rhymartmanchus.itunessearch.data.models.TracksDB
import com.rhymartmanchus.itunessearch.domain.SearchSession
import com.rhymartmanchus.itunessearch.domain.Track

fun SearchSessionsDB.toDomain(): SearchSession =
    SearchSession(
        lastVisit
    )

fun SearchSession.toDB(): SearchSessionsDB =
    SearchSessionsDB(
        1,
        lastAccess
    )

fun Track.toDB(): TracksDB =
    TracksDB(
        id,
        name,
        artworkUrl,
        price,
        genre,
        longDescription,
        currency
    )

fun TracksDB.toDomain(): Track =
    Track(
        id,
        name,
        artworkUrl,
        price,
        genre,
        longDescription,
        currency
    )

fun TrackRaw.toDomain(): Track =
    Track(
        id,
        trackName,
        artworkUrl,
        price,
        genre,
        longDescription,
        currency
    )