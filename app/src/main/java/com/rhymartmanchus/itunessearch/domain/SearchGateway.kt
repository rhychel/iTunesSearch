package com.rhymartmanchus.itunessearch.domain

interface SearchGateway {

    suspend fun searchTrackByTerm(term: String, country: String): List<Track>
    suspend fun saveTrack(track: Track)
    suspend fun saveLastSession(searchSession: SearchSession)
    suspend fun getLastSession(): SearchSession
    suspend fun getLastSearchedTracks(): List<Track>
    suspend fun invalidateLastSearchedTracks()

}