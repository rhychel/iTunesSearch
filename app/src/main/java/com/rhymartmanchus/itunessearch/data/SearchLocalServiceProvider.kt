package com.rhymartmanchus.itunessearch.data

import com.rhymartmanchus.itunessearch.data.db.ITunesSearchDAO
import com.rhymartmanchus.itunessearch.domain.SearchSession
import com.rhymartmanchus.itunessearch.domain.Track
import com.rhymartmanchus.itunessearch.domain.exceptions.NoDataException

class SearchLocalServiceProvider (
    private val dao: ITunesSearchDAO
) : SearchLocalService {

    override suspend fun saveTrack(track: Track) {
        dao.saveTrack(
            track.toDB()
        )
    }

    override suspend fun saveSearchSession(searchSession: SearchSession) {
        dao.saveSearchSession(
            searchSession.toDB()
        )
    }

    override suspend fun getLastSearchedTracks(): List<Track> {
        val tracks = dao.getLastSearchedTracks()

        return tracks.map {
            it.toDomain()
        }
    }

    override suspend fun getLastSession(): SearchSession {
        val session = dao.getSearchSession()

        return session?.toDomain() ?: throw NoDataException("No last session")
    }

    override suspend fun invalidateTracks() {
        dao.deleteTracks()
    }
}