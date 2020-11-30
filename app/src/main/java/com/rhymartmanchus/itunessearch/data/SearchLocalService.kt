package com.rhymartmanchus.itunessearch.data

import com.rhymartmanchus.itunessearch.domain.SearchSession
import com.rhymartmanchus.itunessearch.domain.Track

interface SearchLocalService {

    suspend fun saveTrack(track: Track)
    suspend fun saveSearchSession(searchSession: SearchSession)
    suspend fun getLastSearchedTracks(): List<Track>
    suspend fun getLastSession(): SearchSession
    suspend fun invalidateTracks()

}