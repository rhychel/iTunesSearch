package com.rhymartmanchus.itunessearch.data

import com.rhymartmanchus.itunessearch.domain.SearchGateway
import com.rhymartmanchus.itunessearch.domain.SearchSession
import com.rhymartmanchus.itunessearch.domain.Track

class SearchGatewayImpl (
    private val local: SearchLocalService,
    private val remote: SearchRemoteService
) : SearchGateway {

    override suspend fun searchTrackByTerm(term: String, country: String): List<Track> =
        remote.searchByTerm(term, country)

    override suspend fun saveTrack(track: Track) =
        local.saveTrack(track)

    override suspend fun saveLastSession(searchSession: SearchSession) =
        local.saveSearchSession(searchSession)

    override suspend fun getLastSession(): SearchSession =
        local.getLastSession()

    override suspend fun getLastSearchedTracks(): List<Track> =
        local.getLastSearchedTracks()

    override suspend fun invalidateLastSearchedTracks() =
        local.invalidateTracks()
}